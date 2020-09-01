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
package com.osmerion.quitte.collections;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import com.osmerion.quitte.InvalidationListener;

/**
 * A basic implementation for {@link ObservableList}.
 *
 * @param <E>   the type of the list's elements
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class AbstractObservableList<E> extends AbstractList<E> implements ObservableList<E> {

    private transient final Set<ListChangeListener<? super E>> changeListeners = new LinkedHashSet<>(1);
    private transient final CopyOnWriteArraySet<InvalidationListener> invalidationListeners = new CopyOnWriteArraySet<>();

    @Nullable
    private transient ChangeBuilder changeBuilder;

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addListener(ListChangeListener<? super E> listener) {
        return this.changeListeners.add(Objects.requireNonNull(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeListener(ListChangeListener<? super E> listener) {
        return this.changeListeners.remove(Objects.requireNonNull(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addListener(InvalidationListener listener) {
        return this.invalidationListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeListener(InvalidationListener listener) {
        return this.invalidationListeners.remove(listener);
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

    protected abstract void addImpl(int index, @Nullable E element);

    @Nullable
    protected abstract E removeImpl(int index);

    protected abstract void sortImpl(Comparator<? super E> c);

    @Nullable
    protected abstract E setImpl(int index, @Nullable E element);

    @Override
    public final void add(int index, @Nullable E element) {
        this.addImpl(index, element);

        try (ChangeBuilder changeBuilder = this.beginChange()) {
            changeBuilder.logAdd(index, index + 1);
            this.modCount++;
        }
    }

    @Override
    @Nullable
    public final E remove(int index) {
        E old = this.removeImpl(index);

        try (ChangeBuilder changeBuilder = this.beginChange()) {
            changeBuilder.logRemove(index, old);
            this.modCount++;
        }

        return old;
    }

    @Override
    public final boolean remove(Object element) {
        int index = this.indexOf(element);

        if (index != -1) {
            this.remove(index);
            return true;
        }

        return false;
    }

    @Override
    @Nullable
    public final E set(int index, @Nullable E element) {
        E old = this.setImpl(index, element);

        try (ChangeBuilder changeBuilder = this.beginChange()) {
            changeBuilder.logSet(index, old);
        }

        return old;
    }

    @Override
    public final boolean setAll(Collection<E> elements) {
        List<E> copyOfElements = this.equalContentsOrNull(elements);
        if (copyOfElements == null) return false;

        try (ChangeBuilder ignored = this.beginChange()) {
            this.clear();
            this.addAll(copyOfElements);
            return true;
        }
    }

    @Nullable
    private List<E> equalContentsOrNull(Collection<E> elements) {
        List<E> copyOfElements = new ArrayList<>(elements.size());
        Iterator<?> aItr = this.iterator();
        Iterator<?> bItr = elements.iterator();
        boolean aHasNext, bHasNext;

        while ((aHasNext = aItr.hasNext()) & (bHasNext = bItr.hasNext())) {
            if (!Objects.equals(aItr.next(), bItr.next())) return null;
        }

        return (aHasNext == bHasNext) ? copyOfElements : null;
    }

    @Override
    public final void sort(Comparator<? super E> comparator) {
        try (ChangeBuilder ignored = this.beginChange()) {
            this.sortImpl(comparator);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final List<E> subList(int fromIndex, int toIndex) {
        List<E> subList = super.subList(fromIndex, toIndex);
        return this instanceof RandomAccess ? new RandomAccessObservableSubList(subList) : new ObservableSubList(subList);
    }

    private class ObservableSubList implements List<E> {

        private final List<E> subList;

        private ObservableSubList(List<E> subList) {
            this.subList = subList;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            try (ChangeBuilder ignored = AbstractObservableList.this.beginChange()) {
                return this.subList.addAll(c);
            }
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            try (ChangeBuilder ignored = AbstractObservableList.this.beginChange()) {
                return this.subList.addAll(index, c);
            }
        }

        @Override
        public void clear() {
            try (ChangeBuilder ignored = AbstractObservableList.this.beginChange()) {
                this.subList.clear();
            }
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            try (ChangeBuilder ignored = AbstractObservableList.this.beginChange()) {
                return this.subList.removeAll(c);
            }
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            try (ChangeBuilder ignored = AbstractObservableList.this.beginChange()) {
                return this.subList.retainAll(c);
            }
        }

        @Override
        public boolean add(E e) {
            return this.subList.add(e);
        }

        @Override
        public void add(int index, E element) {
            this.subList.add(index, element);
        }

        @Override
        public boolean contains(Object o) {
            return this.subList.contains(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return this.subList.containsAll(c);
        }

        @Override
        public E get(int index) {
            return this.subList.get(index);
        }

        @Override
        public boolean isEmpty() {
            return this.subList.isEmpty();
        }

        @Override
        public int indexOf(Object o) {
            return this.subList.indexOf(o);
        }

        @Override
        public Iterator<E> iterator() {
            return this.subList.iterator();
        }

        @Override
        public int lastIndexOf(Object o) {
            return this.subList.lastIndexOf(o);
        }

        @Override
        public ListIterator<E> listIterator() {
            return this.subList.listIterator();
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            return this.subList.listIterator(index);
        }

        @Override
        public E remove(int index) {
            return this.subList.remove(index);
        }

        @Override
        public boolean remove(Object o) {
            return this.subList.remove(o);
        }

        @Override
        public E set(int index, E element) {
            return this.subList.set(index, element);
        }

        @Override
        public int size() {
            return this.subList.size();
        }

        @Override
        public Object[] toArray() {
            return this.subList.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            //noinspection SuspiciousToArrayCall
            return this.subList.toArray(a);
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return new ObservableSubList(this.subList.subList(fromIndex, toIndex));
        }

        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        @Override
        public boolean equals(Object obj) {
            return this.subList.equals(obj);
        }

        @Override
        public int hashCode() {
            return this.subList.hashCode();
        }

        @Override
        public String toString() {
            return this.subList.toString();
        }

    }

    private final class RandomAccessObservableSubList extends ObservableSubList implements RandomAccess {

        private RandomAccessObservableSubList(List<E> subList) {
            super(subList);
        }

    }

    /**
     * A builder to be used to register changes made to this list.
     *
     * @see #beginChange()
     * @see #close()
     *
     * @since   0.1.0
     */
    protected final class ChangeBuilder implements AutoCloseable {

        private final List<WorkingLocalChange<E>> localChanges = new ArrayList<>(1);

        private int depth = 0;

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
                for (var listener : AbstractObservableList.this.invalidationListeners) {
                    if (listener.isInvalid()) {
                        AbstractObservableList.this.invalidationListeners.remove(listener);
                        continue;
                    }

                    listener.onInvalidation(AbstractObservableList.this);
                    if (listener.isInvalid()) AbstractObservableList.this.invalidationListeners.remove(listener);
                }

                AbstractObservableList.this.changeBuilder = null;
                if (this.localChanges.isEmpty()) return;

                ListChangeListener.Change<E> change = new ListChangeListener.Change.Update<>(this.localChanges.stream()
                    .map(WorkingLocalChange::complete)
                    .collect(Collectors.toUnmodifiableList())
                );

                for (var listener : AbstractObservableList.this.changeListeners) {
                    if (listener.isInvalid()) {
                        AbstractObservableList.this.changeListeners.remove(listener);
                        continue;
                    }

                    listener.onChanged(change);
                    if (listener.isInvalid()) AbstractObservableList.this.changeListeners.remove(listener);
                }
            }
        }

        public void logAdd(int from, int to) {
            this.localChanges.add(new WorkingLocalChange.Insertion<>(from, to, new ArrayList<>(AbstractObservableList.this.subList(from, to))));
        }

        public void logRemove(int index, @Nullable E old) {
            this.localChanges.add(new WorkingLocalChange.Removal<>(index, index, old));
        }

        public void logRemove(int index, List<? extends E> old) {
            for (E e : old) this.logRemove(index, e);
        }

        public void logSet(int index, @Nullable E old) {
            this.logRemove(index, old);
            this.logAdd(index, index + 1);
        }

    }

    private static abstract class WorkingLocalChange<E> {

        int from, to;

        WorkingLocalChange(int from, int to) {
            this.from = from;
            this.to = to;
        }

        abstract ListChangeListener.LocalChange<E> complete();

        private static final class Insertion<E> extends WorkingLocalChange<E> {

            private List<E> elements;

            private Insertion(int from, int to, List<E> elements) {
                super(from, to);
                this.elements = elements;
            }

            @Override
            ListChangeListener.LocalChange<E> complete() {
                return new ListChangeListener.LocalChange.Insertion<>(this.from, this.elements);
            }

        }

        private static final class Removal<E> extends WorkingLocalChange<E> {

            private List<E> elements;

            private Removal(int from, int to, @Nullable E element) {
                this(from, to, Stream.ofNullable(element).collect(Collectors.toList()));
            }

            private Removal(int from, int to, List<E> elements) {
                super(from, to);
                this.elements = elements;
            }

            @Override
            ListChangeListener.LocalChange<E> complete() {
                return new ListChangeListener.LocalChange.Removal<>(this.from, this.elements);
            }

        }

    }

}