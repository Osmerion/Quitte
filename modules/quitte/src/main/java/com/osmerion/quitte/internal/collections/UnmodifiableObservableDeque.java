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
package com.osmerion.quitte.internal.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import javax.annotation.Nullable;

import com.osmerion.quitte.InvalidationListener;
import com.osmerion.quitte.collections.CollectionChangeListener;
import com.osmerion.quitte.collections.ObservableDeque;

/**
 * A wrapper for an {@link ObservableDeque} that blocks mutation.
 *
 * @param <E>   the type of the deque's elements
 *
 * @see ObservableDeque#unmodifiableViewOf(ObservableDeque)
 *
 * @serial include
 *     
 * @author  Leon Linhart
 */
public class UnmodifiableObservableDeque<E> implements ObservableDeque<E> {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = -1042323408562421394L;

    private final ObservableDeque<E> impl;

    public UnmodifiableObservableDeque(ObservableDeque<E> impl) {
        this.impl = Objects.requireNonNull(impl);
    }

    @Override public boolean addInvalidationListener(InvalidationListener listener) { return this.impl.addInvalidationListener(listener); }
    @Override public boolean removeInvalidationListener(InvalidationListener listener) { return this.impl.removeInvalidationListener(listener); }
    @Override public boolean addChangeListener(CollectionChangeListener<? super ObservableDeque.Change<? extends E>> listener) { return this.impl.addChangeListener(listener); }
    @Override public boolean removeChangeListener(CollectionChangeListener<? super ObservableDeque.Change<? extends E>> listener) { return this.impl.removeChangeListener(listener); }

    @Override public boolean contains(Object o) { return this.impl.contains(o); }
    @Override public boolean containsAll(Collection<?> c) { return this.impl.containsAll(c); }

    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<>() {

            private final Iterator<E> impl = UnmodifiableObservableDeque.this.impl.descendingIterator();

            @Override
            public boolean hasNext() {
                return this.impl.hasNext();
            }

            @Override
            public E next() {
                return this.impl.next();
            }

        };
    }

    @Override public E element() { return this.impl.element(); }
    @Override public E getFirst() { return this.impl.getFirst(); }
    @Override public E getLast() { return this.impl.getLast(); }
    @Override public boolean isEmpty() { return this.impl.isEmpty(); }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            private final Iterator<E> impl = UnmodifiableObservableDeque.this.impl.iterator();

            @Override
            public boolean hasNext() {
                return this.impl.hasNext();
            }

            @Override
            public E next() {
                return this.impl.next();
            }

        };
    }

    @Override public E peek() { return this.impl.peek(); }
    @Override public E peekFirst() { return this.impl.peekFirst(); }
    @Override public E peekLast() { return this.impl.peekLast(); }
    @Override public int size() { return this.impl.size(); }
    @Override public Object[] toArray() { return this.impl.toArray(); }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.impl.toArray(a);
    }

    @Override public boolean add(E e) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public void clear() { throw new UnsupportedOperationException(); }
    @Override public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public void addFirst(@Nullable E element) { throw new UnsupportedOperationException(); }
    @Override public void addLast(@Nullable E element) { throw new UnsupportedOperationException(); }
    @Override public boolean offerFirst(@Nullable E element) { throw new UnsupportedOperationException(); }
    @Override public boolean offerLast(@Nullable E element) { throw new UnsupportedOperationException(); }
    @Override public E remove() { throw new UnsupportedOperationException(); }
    @Override public boolean remove(@Nullable Object o) { throw new UnsupportedOperationException(); }
    @Override public E removeFirst() { throw new UnsupportedOperationException(); }
    @Override public boolean removeFirstOccurrence(Object o) { throw new UnsupportedOperationException(); }
    @Override public E removeLast() { throw new UnsupportedOperationException(); }
    @Override public boolean removeLastOccurrence(Object o) { throw new UnsupportedOperationException(); }
    @Override public boolean offer(E e) { throw new UnsupportedOperationException(); }
    @Override public E poll() { throw new UnsupportedOperationException(); }
    @Override public E pollFirst() { throw new UnsupportedOperationException(); }
    @Override public E pollLast() { throw new UnsupportedOperationException(); }
    @Override public E pop() { throw new UnsupportedOperationException(); }
    @Override public void push(E e) { throw new UnsupportedOperationException(); }

}