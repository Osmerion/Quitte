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

import java.util.Deque;
import java.util.Iterator;
import javax.annotation.Nullable;

import com.osmerion.quitte.collections.AbstractObservableDeque;
import com.osmerion.quitte.collections.ObservableDeque;

/**
 * An observable wrapper for a {@link Deque}.
 *
 * @param <E>   the type of the deque's elements
 *
 * @see com.osmerion.quitte.collections.ObservableDeque#of(Deque)
 *
 * @serial include
 *
 * @author  Leon Linhart
 */
public final class WrappingObservableDeque<E> extends AbstractObservableDeque<E> {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = -4084123783773885840L;

    private final Deque<E> impl;

    public WrappingObservableDeque(Deque<E> impl) {
        this.impl = impl;
    }

    @Override protected void addFirstImpl(@Nullable E element) { this.impl.addFirst(element); }
    @Override protected void addLastImpl(@Nullable E element) { this.impl.addLast(element); }
    @Override protected boolean offerFirstImpl(@Nullable E element) { return this.impl.offerFirst(element); }
    @Override protected boolean offerLastImpl(@Nullable E element) { return this.impl.offerLast(element); }
    @Override protected E removeFirstImpl() { return this.impl.removeFirst(); }
    @Override protected E removeLastImpl() { return this.impl.removeLast(); }
    @Override protected E pollFirstImpl() { return this.impl.pollFirst(); }
    @Override protected E pollLastImpl() { return this.impl.pollLast(); }

    @Override public E element() { return this.impl.element(); }

    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<>() {

            private final Iterator<E> impl = WrappingObservableDeque.this.impl.descendingIterator();
            @Nullable private E cursor;

            @Override
            public boolean hasNext() {
                return this.impl.hasNext();
            }

            @Override
            public E next() {
                return (this.cursor = this.impl.next());
            }

            @Override
            public void remove() {
                try (ChangeBuilder changeBuilder = WrappingObservableDeque.this.beginChange()) {
                    this.impl.remove();
                    changeBuilder.logRemove(ObservableDeque.Site.OPAQUE, this.cursor);
                }
            }

        };
    }

    @Override public E getFirst() { return this.impl.getFirst(); }
    @Override public E getLast() { return this.impl.getLast(); }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            private final Iterator<E> impl = WrappingObservableDeque.this.impl.iterator();
            @Nullable private E cursor;

            @Override
            public boolean hasNext() {
                return this.impl.hasNext();
            }

            @Override
            public E next() {
                return (this.cursor = this.impl.next());
            }

            @Override
            public void remove() {
                try (ChangeBuilder changeBuilder = WrappingObservableDeque.this.beginChange()) {
                    this.impl.remove();
                    changeBuilder.logRemove(ObservableDeque.Site.OPAQUE, this.cursor);
                }
            }

        };
    }

    @Override public E peek() { return this.impl.peek(); }
    @Override public E peekFirst() { return this.impl.peekFirst(); }
    @Override public E peekLast() { return this.impl.peekLast(); }
    @Override public int size() { return this.impl.size(); }

}