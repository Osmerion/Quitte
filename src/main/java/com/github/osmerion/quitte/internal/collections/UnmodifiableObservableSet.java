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
package com.github.osmerion.quitte.internal.collections;

import java.util.Iterator;
import java.util.Objects;
import javax.annotation.Nullable;
import com.github.osmerion.quitte.collections.AbstractObservableSet;
import com.github.osmerion.quitte.collections.ObservableSet;

/**
 * A wrapper for an observable set that blocks mutation.
 *
 * @param <E>   the type of the set's elements
 *
 * @author  Leon Linhart
 */
public final class UnmodifiableObservableSet<E> extends AbstractObservableSet<E> {

    private final ObservableSet<E> impl;

    public UnmodifiableObservableSet(ObservableSet<E> impl) {
        this.impl = Objects.requireNonNull(impl);
    }

    @Override
    protected boolean addImpl(@Nullable E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean removeImpl(@Nullable Object element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return this.impl.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private final Iterator<E> impl = UnmodifiableObservableSet.this.impl.iterator();

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
    public int size() {
        return this.impl.size();
    }

}