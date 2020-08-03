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
package com.osmerion.quitte.internal.collections;

import java.util.Iterator;
import javax.annotation.Nullable;
import com.osmerion.quitte.collections.ObservableDeque;

/**
 * A wrapper for an observable deque that blocks mutation.
 *
 * @param <E>   the type of the deque's elements
 *
 * @see ObservableDeque#unmodifiableViewOf(ObservableDeque)
 *
 * @serial include
 *     
 * @author  Leon Linhart
 */
public final class UnmodifiableObservableDeque<E> extends AbstractWrappingObservableDeque<E> {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = -1042323408562421394L;

    public UnmodifiableObservableDeque(ObservableDeque<E> impl) {
        super(impl);
    }

    @Override public void addFirstImpl(@Nullable E element) { throw new UnsupportedOperationException(); }
    @Override public void addLastImpl(@Nullable E element) { throw new UnsupportedOperationException(); }
    @Override public boolean offerFirstImpl(@Nullable E element) { throw new UnsupportedOperationException(); }
    @Override public boolean offerLastImpl(@Nullable E element) { throw new UnsupportedOperationException(); }
    @Override public E removeFirstImpl() { throw new UnsupportedOperationException(); }
    @Override public E removeLastImpl() { throw new UnsupportedOperationException(); }
    @Override public E pollFirstImpl() { throw new UnsupportedOperationException(); }
    @Override public E pollLastImpl() { throw new UnsupportedOperationException(); }

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

}