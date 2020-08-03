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

import java.util.Deque;
import com.osmerion.quitte.collections.AbstractObservableDeque;

/**
 * Shared implementation for {@link UnmodifiableObservableDeque} and {@link WrappingObservableDeque}.
 *
 * @param <E>   the type of the deque's elements
 *
 * @author  Leon Linhart
 */
abstract class AbstractWrappingObservableDeque<E> extends AbstractObservableDeque<E> {

    Deque<E> impl;

    AbstractWrappingObservableDeque(Deque<E> impl) {
        this.impl = impl;
    }

    @Override public E element() { return this.impl.element(); }
    @Override public E getFirst() { return this.impl.getFirst(); }
    @Override public E getLast() { return this.impl.getLast(); }
    @Override public E peek() { return this.impl.peek(); }
    @Override public E peekFirst() { return this.impl.peekFirst(); }
    @Override public E peekLast() { return this.impl.peekLast(); }
    @Override public int size() { return this.impl.size(); }

}