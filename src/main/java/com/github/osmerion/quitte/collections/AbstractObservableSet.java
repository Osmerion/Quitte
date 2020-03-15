/*
 * Copyright (c) 2018-2020 Leon Linhart,
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.osmerion.quitte.collections;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import com.github.osmerion.quitte.InvalidationListener;

/**
 * A basic implementation for {@link ObservableSet}.
 *
 * @param <E>   the type of the set's elements
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class AbstractObservableSet<E> extends AbstractSet<E> implements ObservableSet<E> {

    private transient final Set<SetChangeListener<? super E>> changeListeners = new LinkedHashSet<>(1);
    private transient final Set<InvalidationListener> invalidationListeners = new LinkedHashSet<>(1);

    @Nullable
    private transient ChangeBuilder changeBuilder;

    @Override
    public void addChangeListener(SetChangeListener<? super E> listener) {
        this.changeListeners.add(Objects.requireNonNull(listener));
    }

    @Override
    public void removeChangeListener(SetChangeListener<? super E> listener) {
        this.changeListeners.remove(Objects.requireNonNull(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addListener(InvalidationListener listener) {
        return this.invalidationListeners.add(Objects.requireNonNull(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public boolean removeListener(InvalidationListener listener) {
        return this.invalidationListeners.remove(Objects.requireNonNull(listener));
    }

    /**
     * Pushes a frame on the current {@link ChangeBuilder} instance or creates a new one.
     *
     * @return  the current {@code ChangeBuilder}
     *
     * @since   0.1.0
     */
    protected final ChangeBuilder beginChange() {
        if (this.changeBuilder == null) this.changeBuilder = new ChangeBuilder();
        this.changeBuilder.depth++;

        return this.changeBuilder;
    }

    /**
     * See {@link Set#add(Object)}.
     *
     * @param element   the element to add
     *
     * @return  whether or not this set was modified as result of this operation
     *
     * @since   0.1.0
     */
    protected abstract boolean addImpl(@Nullable E element);

    /**
     * See {@link Set#remove(Object)}.
     *
     * @param element   the element to remove
     *
     * @return  whether or not this set was modified as result of this operation
     *
     * @since   0.1.0
     */
    protected abstract boolean removeImpl(@Nullable Object element);

    @Override
    public final boolean add(E e) {
        if (this.addImpl(e)) {
            try (ChangeBuilder changeBuilder = this.beginChange()) {
                changeBuilder.logAdd(e);
            }

            return true;
        }

        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final boolean remove(Object o) {
        if (this.removeImpl(o)) {
            try (ChangeBuilder changeBuilder = this.beginChange()) {
                changeBuilder.logRemove((E) o);
            }

            return true;
        }

        return false;
    }

    /**
     * A builder to be used to register changes made to this set.
     *
     * @see #beginChange()
     * @see #close()
     *
     * @since   0.1.0
     */
    protected final class ChangeBuilder implements AutoCloseable {

        @Nullable
        private HashSet<E> added, removed;

        private int depth = 0;

        private ChangeBuilder() {}

        /**
         * Pops a frame from this builder.
         *
         * <p>If the last frame has been popped, the changes are committed (i.e. post-processed and compressed) and sent
         * to this listeners of this observable.</p>
         *
         * @since   0.1.0
         */
        @Override
        public void close() {
            if (this.depth < 1) throw new IllegalStateException();

            this.depth--;

            if (this.depth == 0) {
                AbstractObservableSet.this.changeBuilder = null;
                if (this.added == null && this.removed == null) return;

                var change = new SetChangeListener.Change<>(this.added, this.removed);

                for (var itr = AbstractObservableSet.this.changeListeners.iterator(); itr.hasNext(); ) {
                    var listener = itr.next();

                    listener.onChanged(change);
                    if (listener.isInvalid()) itr.remove();
                }

                for (var itr = AbstractObservableSet.this.invalidationListeners.iterator(); itr.hasNext(); ) {
                    var listener = itr.next();

                    listener.onInvalidation(AbstractObservableSet.this);
                    if (listener.isInvalid()) itr.remove();
                }
            }
        }

        /**
         * Logs the addition of the given element.
         *
         * @param element   the element that was added
         *
         * @since   0.1.0
         */
        public void logAdd(@Nullable E element) {
            if (this.added == null) this.added = new HashSet<>();

            /*
             * Guard against spurious operations that add and remove the same element for whatever reason.
             *
             * TODO: This is rarely useful. Reconsider if this check should be performed.
             */
            if (this.removed == null || !this.removed.remove(element)) this.added.add(element);
        }

        /**
         * Logs the removal of the given element.
         *
         * @param old   the element that was removed
         *
         * @since   0.1.0
         */
        public void logRemove(@Nullable E old) {
            if (this.removed == null) this.removed = new HashSet<>();

            /*
             * Guard against spurious operations that add and remove the same element for whatever reason.
             *
             * TODO: This is rarely useful. Reconsider if this check should be performed.
             */
            if (this.added == null || !this.added.remove(old)) this.removed.add(old);
        }

    }

}