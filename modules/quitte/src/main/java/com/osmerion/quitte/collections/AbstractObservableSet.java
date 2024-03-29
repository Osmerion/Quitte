/*
 * Copyright (c) 2018-2023 Leon Linhart,
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
package com.osmerion.quitte.collections;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nullable;

import com.osmerion.quitte.InvalidationListener;

/**
 * A basic implementation for a modifiable {@link ObservableSet}.
 *
 * @param <E>   the type of the set's elements
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class AbstractObservableSet<E> extends AbstractSet<E> implements ObservableSet<E> {

    private transient final CopyOnWriteArraySet<SetChangeListener<? super E>> changeListeners = new CopyOnWriteArraySet<>();
    private transient final CopyOnWriteArraySet<InvalidationListener> invalidationListeners = new CopyOnWriteArraySet<>();

    @Nullable
    private transient ChangeBuilder changeBuilder;

    /**
     * {@inheritDoc}
     *
     * @since   0.8.0
     */
    @Override
    public final boolean addChangeListener(SetChangeListener<? super E> listener) {
        return this.changeListeners.add(Objects.requireNonNull(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.8.0
     */
    @Override
    public final boolean removeChangeListener(SetChangeListener<? super E> listener) {
        return this.changeListeners.remove(Objects.requireNonNull(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addInvalidationListener(InvalidationListener listener) {
        return this.invalidationListeners.add(Objects.requireNonNull(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeInvalidationListener(InvalidationListener listener) {
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
     * @return  whether this set was modified as result of this operation
     *
     * @since   0.1.0
     */
    protected abstract boolean addImpl(@Nullable E element);

    /**
     * See {@link Set#remove(Object)}.
     *
     * @param element   the element to remove
     *
     * @return  whether this set was modified as result of this operation
     *
     * @since   0.1.0
     */
    protected abstract boolean removeImpl(@Nullable Object element);

    @Override
    public final boolean add(E e) {
        try (ChangeBuilder changeBuilder = this.beginChange()) {
            if (this.addImpl(e)) {
                changeBuilder.logAdd(e);
                return true;
            }
        }

        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final boolean remove(Object o) {
        try (ChangeBuilder changeBuilder = this.beginChange()) {
            if (this.removeImpl(o)) {
                changeBuilder.logRemove((E) o);
                return true;
            }
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
                if ((this.added == null || this.added.isEmpty()) && (this.removed == null || this.removed.isEmpty())) return;

                var change = new SetChangeListener.Change<>(this.added, this.removed);

                for (var listener : AbstractObservableSet.this.changeListeners) {
                    if (listener.isInvalid()) {
                        AbstractObservableSet.this.changeListeners.remove(listener);
                        continue;
                    }

                    listener.onChanged(AbstractObservableSet.this, change);
                    if (listener.isInvalid()) AbstractObservableSet.this.changeListeners.remove(listener);
                }

                for (var listener : AbstractObservableSet.this.invalidationListeners) {
                    if (listener.isInvalid()) {
                        AbstractObservableSet.this.invalidationListeners.remove(listener);
                        continue;
                    }

                    listener.onInvalidation(AbstractObservableSet.this);
                    if (listener.isInvalid()) AbstractObservableSet.this.invalidationListeners.remove(listener);
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

            /* Guard against spurious operations that add and remove the same element for whatever reason. */
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

            /* Guard against spurious operations that add and remove the same element for whatever reason. */
            if (this.added == null || !this.added.remove(old)) this.removed.add(old);
        }

    }

}