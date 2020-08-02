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

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

/**
 * This class provides additional general functionality that is not yet available in {@link java.util.Collections}.
 *
 * @see java.util.Collections
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public final class QuitteCollections {

    /**
     * Returns an unmodifiable view of the specified deque. Query operations on the returned deque "read through" to the
     * specified deque, and attempts to modify the returned deque, whether direct or via its iterator, result in an
     * {@code UnsupportedOperationException}.
     *
     * <p>The returned deque will be serializable if the specified deque is serializable.</p>
     *
     * @param <E>   the type of the objects in the deque
     * @param deque the deque for which an unmodifiable view is to be returned
     *
     * @return an unmodifiable view of the specified deque
     *
     * @see java.util.Collections#unmodifiableCollection(Collection)
     *
     * @since   0.1.0
     */
    public static <E> Deque<E> unmodifiableDeque(Deque<E> deque) {
        return new UnmodifiableDeque<>(deque);
    }

    private static class UnmodifiableDeque<E> implements Deque<E> {

        private final Deque<E> impl;

        private UnmodifiableDeque(Deque<E> impl) {
            this.impl = impl;
        }

        @Override public boolean contains(Object o) { return this.impl.contains(o); }
        @Override public boolean containsAll(Collection<?> c) { return this.impl.containsAll(c); }
        @Override public E element() { return this.impl.element(); }
        @Override public E getFirst() { return this.impl.getFirst(); }
        @Override public E getLast() { return this.impl.getLast(); }
        @Override public boolean isEmpty() { return this.impl.isEmpty(); }
        @Override public E peek() { return this.impl.peek(); }
        @Override public E peekFirst() { return this.impl.peekFirst(); }
        @Override public E peekLast() { return this.impl.peekLast(); }
        @Override public int size() { return this.impl.size(); }
        @Override public Object[] toArray() { return this.impl.toArray(); }

        @SuppressWarnings("SuspiciousToArrayCall")
        @Override public <T> T[] toArray(T[] a) { return this.impl.toArray(a); }

        @Override
        public Iterator<E> iterator() {
            return new Itr<>(this.impl.iterator());
        }

        @Override
        public Iterator<E> descendingIterator() {
            return new Itr<>(this.impl.descendingIterator());
        }

        private static final class Itr<E> implements Iterator<E> {
            private final Iterator<E> impl;

            private Itr(Iterator<E> impl) {
                this.impl = impl;
            }

            @Override public boolean hasNext() { return this.impl.hasNext(); }
            @Override public E next() { return this.impl.next(); }

        }

        @Override public boolean add(E e) { throw new UnsupportedOperationException(); }
        @Override public boolean addAll(Collection<? extends E> c) { throw new UnsupportedOperationException(); }
        @Override public void addFirst(E e) { throw new UnsupportedOperationException(); }
        @Override public void addLast(E e) { throw new UnsupportedOperationException(); }
        @Override public void clear() { throw new UnsupportedOperationException(); }
        @Override public boolean offer(E e) { throw new UnsupportedOperationException(); }
        @Override public boolean offerFirst(E e) { throw new UnsupportedOperationException(); }
        @Override public boolean offerLast(E e) { throw new UnsupportedOperationException(); }
        @Override public E poll() { throw new UnsupportedOperationException(); }
        @Override public E pop() { throw new UnsupportedOperationException(); }
        @Override public E pollFirst() { throw new UnsupportedOperationException(); }
        @Override public E pollLast() { throw new UnsupportedOperationException(); }
        @Override public void push(E e) { throw new UnsupportedOperationException(); }
        @Override public E remove() { throw new UnsupportedOperationException(); }
        @Override public boolean remove(Object o) { throw new UnsupportedOperationException(); }
        @Override public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
        @Override public E removeFirst() { throw new UnsupportedOperationException(); }
        @Override public E removeLast() { throw new UnsupportedOperationException(); }
        @Override public boolean removeFirstOccurrence(Object o) { throw new UnsupportedOperationException(); }
        @Override public boolean removeLastOccurrence(Object o) { throw new UnsupportedOperationException(); }
        @Override public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }

    }

}