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

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

import com.osmerion.quitte.InvalidationListener;

import static java.lang.Math.*;

/**
 * A basic implementation for a modifiable {@link ObservableList}.
 *
 * @param <E>   the type of the list's elements
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class AbstractObservableList<E> extends AbstractList<E> implements ObservableList<E> {

    private transient final CopyOnWriteArraySet<CollectionChangeListener<? super ObservableList.Change<? extends E>>> changeListeners = new CopyOnWriteArraySet<>();
    private transient final CopyOnWriteArraySet<InvalidationListener> invalidationListeners = new CopyOnWriteArraySet<>();

    @Nullable
    private transient ChangeBuilder changeBuilder;

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addChangeListener(CollectionChangeListener<? super Change<? extends E>> listener) {
        return this.changeListeners.add(Objects.requireNonNull(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeChangeListener(CollectionChangeListener<? super Change<? extends E>> listener) {
        return this.changeListeners.remove(Objects.requireNonNull(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addInvalidationListener(InvalidationListener listener) {
        return this.invalidationListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeInvalidationListener(InvalidationListener listener) {
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
        List<E> copyOfElements = this.nullIfEqual(elements);
        if (copyOfElements == null) return false;

        try (ChangeBuilder ignored = this.beginChange()) {
            this.clear();
            this.addAll(copyOfElements);
            return true;
        }
    }

    @Nullable
    private List<E> nullIfEqual(Collection<E> elements) {
        List<E> copyOfElements = new ArrayList<>(elements.size());
        Iterator<E> aItr = this.iterator();
        Iterator<E> bItr = elements.iterator();
        boolean equal = true;

        boolean aHasNext, bHasNext;
        while ((aHasNext = aItr.hasNext()) & (bHasNext = bItr.hasNext())) {
            E element = bItr.next();
            copyOfElements.add(element);

            if (!Objects.equals(aItr.next(), element)) {
                equal = false;
                break;
            }
        }

        if (equal && (aHasNext == bHasNext)) return null;

        bItr.forEachRemaining(copyOfElements::add);
        return copyOfElements;
    }

    @Override
    public final void sort(Comparator<? super E> comparator) {
        try (ChangeBuilder ignored = this.beginChange()) {
            super.sort(comparator);
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
            //noinspection SlowListContainsAll
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
        private int sizeDelta = 0;

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
                AbstractObservableList.this.changeBuilder = null;
                if (this.localChanges.isEmpty()) return;

                ObservableList.Change<E> change = null;

                /*
                 * Compressing changes is a non-trivial task and to keep the implementation relatively simple, only
                 * operations defined in ObservableList are required to be compressed as much as possible.
                 *
                 * If the size of the list didn't change, attempt to construct a permutation mapping. This is done by
                 * processing working changes change by change in the order they occurred.
                 */
                if (this.sizeDelta == 0) {
                    /*
                     * `permutation` holds the permutation mapping (i.e. it maps from the original indices to the
                     * current indices).
                     *
                     * `currentToOriginalIndexMapping` tracks the original indices and is dynamically in-/decreased
                     * during each computation step.
                     */
                    int[] permutation = new int[AbstractObservableList.this.size()];
                    List<Integer> currentToOriginalIndexMapping = new ArrayList<>(AbstractObservableList.this.size());

                    /* Populate the permutation and the currentToOriginalIndex mappings. */
                    for (int i = 0; i < permutation.length; i++) {
                        permutation[i] = i;
                        currentToOriginalIndexMapping.add(i);
                    }

                    /*
                     * To track the positions of elements after they have been removed, `expectedAdditions` stores all
                     * original indices which do not have a mapping to a current index.
                     *
                     * Similarly, `expectedRemovals` tracks the list of current indices which do not have a mapping to
                     * an original index.
                     */
                    Map<E, List<Integer>> expectedAdditions = new HashMap<>();
                    Map<E, List<Integer>> expectedRemovals = new HashMap<>();

                    for (WorkingLocalChange<E> workingLocalChange : this.localChanges) {
                        if (workingLocalChange instanceof WorkingLocalChange.Insertion<E> insertion) {
                            /*
                             * Shift all current indices (starting from the insertion's offset) by adding the number of
                             * elements inserted.
                             */
                            for (int i = insertion.from; i < permutation.length; i++) {
                                if (permutation[i] >= insertion.from) {
                                    permutation[i] += insertion.elements.size();
                                }

                                expectedRemovals.forEach((k, v) -> v.replaceAll(index -> index >= insertion.from ? index + insertion.elements.size() : index));
                            }

                            for (int i = 0; i < insertion.elements.size(); i++) {
                                E element = insertion.elements.get(i);
                                List<Integer> eAIs = expectedAdditions.get(element);
                                int currentIndex = insertion.from + i;

                                /*
                                 * If the element is expected to be added (i.e. it has previously been removed
                                 * unexpectedly), an original index without a mapping to a current index exists. The
                                 * current index is then mapped to the original index.
                                 *
                                 * Otherwise, if the element is not expected to be added, it is expected that it will be
                                 * removed again.
                                 */
                                if (eAIs != null && !eAIs.isEmpty()) {
                                    int originalIndex = eAIs.remove(0);

                                    currentToOriginalIndexMapping.add(currentIndex, originalIndex);
                                    permutation[originalIndex] = currentIndex;
                                } else {
                                    expectedRemovals.computeIfAbsent(element, e -> new ArrayList<>()).add(currentIndex);
                                    currentToOriginalIndexMapping.add(currentIndex, -1);
                                }
                            }
                        } else if (workingLocalChange instanceof WorkingLocalChange.Removal<E> removal) {
                            for (int i = 0; i < removal.elements.size(); i++) {
                                E element = removal.elements.get(i);
                                List<Integer> eRIs = expectedRemovals.get(element);
                                int originalIndex = currentToOriginalIndexMapping.remove(removal.from + i);

                                /*
                                 * If the element is expected to be removed (i.e. it has previously been added
                                 * unexpectedly), a current index without a mapping to an original index exists. The
                                 * original index is then mapped to the current index.
                                 *
                                 * Otherwise, if the element is not expected to be removed, it is expected that it will
                                 * be added again.
                                 */
                                if (eRIs != null && !eRIs.isEmpty()) {
                                    int currentIndex = eRIs.remove(0);

                                    currentToOriginalIndexMapping.set(currentIndex, originalIndex);
                                    permutation[originalIndex] = currentIndex;
                                } else {
                                    expectedAdditions.computeIfAbsent(element, e -> new ArrayList<>()).add(originalIndex);
                                }
                            }

                            /*
                             * Shift all current indices (starting from the removal's offset) by subtracting the number
                             * of elements removed.
                             */
                            for (int i = 0; i < permutation.length; i++) {
                                if (permutation[i] >= removal.from) {
                                    permutation[i] -= removal.elements.size();
                                }

                                expectedRemovals.forEach((k, v) -> v.replaceAll(index -> index >= removal.from ? index - removal.elements.size() : index));
                            }
                        } else {
                            throw new IllegalStateException();
                        }
                    }

                    /*
                     * If elements are still expected to be added/removed, the change was not actually a permutation but
                     * the resulting list just happened to be equal in size to the original ist.
                     */
                    if (expectedAdditions.values().stream().allMatch(List::isEmpty) && expectedRemovals.values().stream().allMatch(List::isEmpty)) {
                        change = new ObservableList.Change.Permutation<>(IntStream.of(permutation).boxed().toList());
                    }
                }

                /* We couldn't reconstruct a permutation and need to "compress" local changes. */
                if (change == null) {
                    List<LocalChange<E>> localChanges = new ArrayList<>(this.localChanges.size());

                    List<E> updateElements = new ArrayList<>();
                    int updateFrom = -1;

                    List<E> batchElements = new ArrayList<>();
                    int batchFrom = -1;

                    /* Keep track of the type of the current change. (Insertion: 1, Removal: 2) */
                    int batchType = 0;

                    for (WorkingLocalChange<E> wlc : this.localChanges) {
                        if (wlc instanceof WorkingLocalChange.Insertion) {
                            if (batchType != 1) {
                                if (batchType == 2) {
                                    if (batchFrom == wlc.from && batchElements.size() == ((WorkingLocalChange.Insertion<E>) wlc).elements.size()) {
                                        if (!updateElements.isEmpty()) {
                                            if (wlc.from <= updateFrom + updateElements.size() && updateFrom <= wlc.to) {
                                                int offset = abs(wlc.from - updateFrom);

                                                updateFrom = min(wlc.from, updateFrom);
                                                updateElements.addAll(offset, ((WorkingLocalChange.Insertion<E>) wlc).elements);
                                            } else {
                                                localChanges.add(new LocalChange.Update<>(updateFrom, new ArrayList<>(updateElements)));
                                                updateElements.clear();

                                                updateFrom = batchFrom;
                                                updateElements.addAll(((WorkingLocalChange.Insertion<E>) wlc).elements);
                                            }
                                        } else {
                                            updateFrom = batchFrom;
                                            updateElements.addAll(((WorkingLocalChange.Insertion<E>) wlc).elements);
                                        }

                                        batchType = 0;
                                        batchElements.clear();

                                        continue;
                                    } else {
                                        if (!updateElements.isEmpty()) {
                                            localChanges.add(new LocalChange.Update<>(updateFrom, new ArrayList<>(updateElements)));
                                            updateFrom = -1;
                                            updateElements.clear();
                                        }

                                        localChanges.add(new LocalChange.Removal<>(batchFrom, new ArrayList<>(batchElements)));
                                        batchElements.clear();
                                    }
                                }

                                batchFrom = wlc.from;
                                batchType = 1;
                                batchElements.addAll(((WorkingLocalChange.Insertion<E>) wlc).elements);
                            } else if (wlc.from <= batchFrom + batchElements.size() && batchFrom <= wlc.to) {
                                int offset = abs(wlc.from - batchFrom);

                                batchFrom = min(wlc.from, batchFrom);
                                batchElements.addAll(offset, ((WorkingLocalChange.Insertion<E>) wlc).elements);
                            } else {
                                if (!updateElements.isEmpty()) {
                                    localChanges.add(new LocalChange.Update<>(updateFrom, new ArrayList<>(updateElements)));
                                    updateFrom = -1;
                                    updateElements.clear();
                                }

                                localChanges.add(new LocalChange.Insertion<>(batchFrom, new ArrayList<>(batchElements)));
                                batchElements.clear();

                                batchFrom = wlc.from;
                                batchType = 1;
                                batchElements.addAll(((WorkingLocalChange.Insertion<E>) wlc).elements);
                            }
                        } else if (wlc instanceof WorkingLocalChange.Removal) {
                            if (batchType != 2) {
                                if (!updateElements.isEmpty()) {
                                    localChanges.add(new LocalChange.Update<>(updateFrom, new ArrayList<>(updateElements)));
                                    updateFrom = -1;
                                    updateElements.clear();
                                }

                                if (!batchElements.isEmpty()) localChanges.add(new LocalChange.Insertion<>(batchFrom, new ArrayList<>(batchElements)));
                            } else if (wlc.from <= batchFrom + batchElements.size() && batchFrom <= wlc.to) {
                                int offset = abs(wlc.from - batchFrom);

                                batchFrom = min(wlc.from, batchFrom);
                                batchElements.addAll(offset, ((WorkingLocalChange.Removal<E>) wlc).elements);

                                continue;
                            } else {
                                if (!updateElements.isEmpty()) {
                                    localChanges.add(new LocalChange.Update<>(updateFrom, new ArrayList<>(updateElements)));
                                    updateFrom = -1;
                                    updateElements.clear();
                                }

                                localChanges.add(new LocalChange.Removal<>(batchFrom, new ArrayList<>(batchElements)));
                                batchElements.clear();
                            }

                            batchFrom = wlc.from;
                            batchType = 2;
                            batchElements.addAll(((WorkingLocalChange.Removal<E>) wlc).elements);
                        } else {
                            throw new IllegalStateException();
                        }
                    }

                    if (!updateElements.isEmpty()) localChanges.add(new LocalChange.Update<>(updateFrom, updateElements));

                    if (!batchElements.isEmpty()) {
                        localChanges.add(switch (batchType) {
                            case 1 -> new LocalChange.Insertion<>(batchFrom, batchElements);
                            case 2 -> new LocalChange.Removal<>(batchFrom, batchElements);
                            default -> throw new IllegalStateException();
                        });
                    }

                    change = new ObservableList.Change.Update<>(List.copyOf(localChanges));
                }

                for (var listener : AbstractObservableList.this.changeListeners) {
                    if (listener.isInvalid()) {
                        AbstractObservableList.this.changeListeners.remove(listener);
                        continue;
                    }

                    listener.onChanged(change);
                    if (listener.isInvalid()) AbstractObservableList.this.changeListeners.remove(listener);
                }

                for (var listener : AbstractObservableList.this.invalidationListeners) {
                    if (listener.isInvalid()) {
                        AbstractObservableList.this.invalidationListeners.remove(listener);
                        continue;
                    }

                    listener.onInvalidation(AbstractObservableList.this);
                    if (listener.isInvalid()) AbstractObservableList.this.invalidationListeners.remove(listener);
                }
            }
        }

        public void logAdd(int from, int to) {
            this.localChanges.add(new WorkingLocalChange.Insertion<>(from, to, new ArrayList<>(AbstractObservableList.this.subList(from, to))));
            this.sizeDelta += to - from;
        }

        public void logRemove(int index, @Nullable E old) {
            this.localChanges.add(new WorkingLocalChange.Removal<>(index, index, old));
            this.sizeDelta--;
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

        final int from, to;

        WorkingLocalChange(int from, int to) {
            this.from = from;
            this.to = to;
        }

        private static final class Insertion<E> extends WorkingLocalChange<E> {

            private final List<E> elements;

            private Insertion(int from, int to, List<E> elements) {
                super(from, to);
                this.elements = elements;
            }

        }

        private static final class Removal<E> extends WorkingLocalChange<E> {

            private final List<E> elements;

            private Removal(int from, int to, @Nullable E element) {
                this(from, to, Collections.singletonList(element));
            }

            private Removal(int from, int to, List<E> elements) {
                super(from, to);
                this.elements = elements;
            }

        }

    }

}
