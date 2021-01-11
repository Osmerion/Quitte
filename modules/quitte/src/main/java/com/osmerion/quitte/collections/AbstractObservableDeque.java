/*
 * Copyright (c) 2018-2021 Leon Linhart,
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

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nullable;
import com.osmerion.quitte.InvalidationListener;

/**
 * A basic implementation for a modifiable {@link ObservableDeque}.
 *
 * @param <E>   the type of the deque's elements
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class AbstractObservableDeque<E> extends AbstractCollection<E> implements ObservableDeque<E> {

    private transient final CopyOnWriteArraySet<DequeChangeListener<? super E>> changeListeners = new CopyOnWriteArraySet<>();
    private transient final CopyOnWriteArraySet<InvalidationListener> invalidationListeners = new CopyOnWriteArraySet<>();

    @Nullable
    private transient ChangeBuilder changeBuilder;

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addListener(DequeChangeListener<? super E> listener) {
        return this.changeListeners.add(Objects.requireNonNull(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeListener(DequeChangeListener<? super E> listener) {
        return this.changeListeners.remove(Objects.requireNonNull(listener));
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
    public final boolean removeListener(InvalidationListener listener) {
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

    protected abstract void addFirstImpl(@Nullable E element);

    @Override
    public final void addFirst(@Nullable E element) {
        this.addFirstImpl(element);

        try (ChangeBuilder changeBuilder = this.beginChange()) {
            changeBuilder.logAdd(DequeChangeListener.Site.HEAD, element);
        }
    }

    protected abstract void addLastImpl(@Nullable E element);

    @Override
    public final void addLast(E element) {
        this.addLastImpl(element);

        try (ChangeBuilder changeBuilder = this.beginChange()) {
            changeBuilder.logAdd(DequeChangeListener.Site.TAIL, element);
        }
    }

    protected abstract boolean offerFirstImpl(@Nullable E element);

    @Override
    public final boolean offerFirst(E element) {
        if (this.offerFirstImpl(element)) {
            try (ChangeBuilder changeBuilder = this.beginChange()) {
                changeBuilder.logAdd(DequeChangeListener.Site.HEAD, element);
            }

            return true;
        }

        return false;
    }

    protected abstract boolean offerLastImpl(@Nullable E element);

    @Override
    public final boolean offerLast(@Nullable E element) {
        if (this.offerLastImpl(element)) {
            try (ChangeBuilder changeBuilder = this.beginChange()) {
                changeBuilder.logAdd(DequeChangeListener.Site.TAIL, element);
            }

            return true;
        }

        return false;
    }

    @Nullable
    protected abstract E removeFirstImpl();

    @Override
    @Nullable
    public final E removeFirst() {
        E element = this.removeFirstImpl();

        try (ChangeBuilder changeBuilder = this.beginChange()) {
            changeBuilder.logRemove(DequeChangeListener.Site.HEAD, element);
        }

        return element;
    }

    @Nullable
    protected abstract E removeLastImpl();

    @Override
    @Nullable
    public final E removeLast() {
        E element = this.removeLastImpl();

        try (ChangeBuilder changeBuilder = this.beginChange()) {
            changeBuilder.logRemove(DequeChangeListener.Site.TAIL, element);
        }

        return element;
    }

    @Nullable
    protected abstract E pollFirstImpl();

    @Override
    @Nullable
    public final E pollFirst() {
        E element = this.pollFirstImpl();

        try (ChangeBuilder changeBuilder = this.beginChange()) {
            changeBuilder.logRemove(DequeChangeListener.Site.HEAD, element);
        }

        return element;
    }

    @Nullable
    protected abstract E pollLastImpl();

    @Override
    public final E pollLast() {
        E element = this.pollLastImpl();

        try (ChangeBuilder changeBuilder = this.beginChange()) {
            changeBuilder.logRemove(DequeChangeListener.Site.TAIL, element);
        }

        return element;
    }

    @Override
    public final boolean removeFirstOccurrence(@Nullable Object object) {
        Iterator<E> itr = this.iterator();

        while (itr.hasNext()) {
            E element = itr.next();

            if (Objects.equals(object, element)) {
                itr.remove();

                try (ChangeBuilder changeBuilder = this.beginChange()) {
                    changeBuilder.logRemove(DequeChangeListener.Site.OPAQUE, element);
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public final boolean removeLastOccurrence(@Nullable Object object) {
        Iterator<E> itr = this.descendingIterator();

        while (itr.hasNext()) {
            E element = itr.next();

            if (Objects.equals(object, element)) {
                itr.remove();

                try (ChangeBuilder changeBuilder = this.beginChange()) {
                    changeBuilder.logRemove(DequeChangeListener.Site.OPAQUE, element);
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public final boolean add(E e) {
        this.addLast(e);
        return true;
    }

    @Override
    public final boolean addAll(Collection<? extends E> c) {
        try (ChangeBuilder ignored = this.beginChange()) {
            return super.addAll(c);
        }
    }

    @Override
    public final void clear() {
        try (ChangeBuilder ignored = this.beginChange()) {
            super.clear();
        }
    }

    @Override
    public final boolean offer(@Nullable E element) {
        return this.offerLast(element);
    }

    @Override
    @Nullable
    public final E remove() {
        return this.removeFirst();
    }

    @Override
    public final boolean removeAll(Collection<?> c) {
        try (ChangeBuilder ignored = this.beginChange()) {
            return super.removeAll(c);
        }
    }

    @Override
    @Nullable
    public final E poll() {
        return this.pollFirst();
    }

    @Override
    public final void push(@Nullable E element) {
        this.addFirst(element);
    }

    @Override
    @Nullable
    public final E pop() {
        return this.removeFirst();
    }

    @Override
    public abstract Iterator<E> iterator();

    /**
     * A builder to be used to register changes made to this set.
     *
     * @see #beginChange()
     * @see #close()
     *
     * @since   0.1.0
     */
    protected final class ChangeBuilder implements AutoCloseable {

        private final List<DequeChangeListener.LocalChange<E>> localChanges = new ArrayList<>(1);

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
                AbstractObservableDeque.this.changeBuilder = null;
                if (this.localChanges.isEmpty()) return;

                var change = new DequeChangeListener.Change<>(Collections.unmodifiableList(this.localChanges));

                for (var listener : AbstractObservableDeque.this.changeListeners) {
                    if (listener.isInvalid()) {
                        AbstractObservableDeque.this.changeListeners.remove(listener);
                        continue;
                    }

                    listener.onChanged(change);
                    if (listener.isInvalid()) AbstractObservableDeque.this.changeListeners.remove(listener);
                }

                for (var listener : AbstractObservableDeque.this.invalidationListeners) {
                    if (listener.isInvalid()) {
                        AbstractObservableDeque.this.invalidationListeners.remove(listener);
                        continue;
                    }

                    listener.onInvalidation(AbstractObservableDeque.this);
                    if (listener.isInvalid()) AbstractObservableDeque.this.invalidationListeners.remove(listener);
                }
            }
        }

        /**
         * Logs the addition of the given element.
         *
         * @param site      the site of the deque to which the change was made
         * @param element   the element that was added
         *
         * @since   0.1.0
         */
        public void logAdd(DequeChangeListener.Site site, @Nullable E element) {
            if (!this.localChanges.isEmpty()) {
                int lastIndex = this.localChanges.size() - 1;
                DequeChangeListener.LocalChange<E> lastLocalChange = this.localChanges.get(lastIndex);

                if (lastLocalChange instanceof DequeChangeListener.LocalChange.Insertion && site == lastLocalChange.getSite()) {
                    ArrayList<E> elements = new ArrayList<>(lastLocalChange.getElements());
                    elements.add(element);

                    this.localChanges.set(lastIndex, new DequeChangeListener.LocalChange.Insertion<>(site, Collections.unmodifiableList(elements)));
                    return;
                }
            }

            ArrayList<E> elements = new ArrayList<>();
            elements.add(element);

            //noinspection Java9CollectionFactory
            this.localChanges.add(new DequeChangeListener.LocalChange.Insertion<>(site, Collections.unmodifiableList(elements)));
        }

        /**
         * Logs the removal of the given element.
         *
         * @param site      the site of the deque to which the change was made
         * @param element   the element that was removed
         *
         * @since   0.1.0
         */
        public void logRemove(DequeChangeListener.Site site, @Nullable E element) {
            if (!this.localChanges.isEmpty()) {
                int lastIndex = this.localChanges.size() - 1;
                DequeChangeListener.LocalChange<E> lastLocalChange = this.localChanges.get(lastIndex);

                if (lastLocalChange instanceof DequeChangeListener.LocalChange.Removal && site == lastLocalChange.getSite()) {
                    ArrayList<E> elements = new ArrayList<>(lastLocalChange.getElements());
                    elements.add(element);

                    this.localChanges.set(lastIndex, new DequeChangeListener.LocalChange.Removal<>(site, Collections.unmodifiableList(elements)));
                    return;
                }
            }

            ArrayList<E> elements = new ArrayList<>();
            elements.add(element);

            //noinspection Java9CollectionFactory
            this.localChanges.add(new DequeChangeListener.LocalChange.Removal<>(site, Collections.unmodifiableList(elements)));
        }

    }

}