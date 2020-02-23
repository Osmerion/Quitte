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
import java.util.Set;
import javax.annotation.Nullable;
import com.github.osmerion.quitte.collections.AbstractObservableSet;

/**
 * An observable wrapper for a {@link Set}.
 *
 * @param <E>   the type of the set's elements
 *           
 * @see com.github.osmerion.quitte.collections.ObservableSet#of(Set)
 *           
 * @serial include
 *
 * @author  Leon Linhart
 */
public final class WrappingObservableSet<E> extends AbstractObservableSet<E> {

    private static final long serialVersionUID = 2971132266946625119L;

    private final Set<E> impl;

    public WrappingObservableSet(Set<E> impl) {
        this.impl = Objects.requireNonNull(impl);
    }

    @Override
    protected boolean addImpl(@Nullable E element) {
        return this.impl.add(element);
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    protected boolean removeImpl(@Nullable Object element) {
        return this.impl.remove(element);
    }

    @Override
    public boolean isEmpty() {
        return this.impl.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private final Iterator<E> impl = WrappingObservableSet.this.impl.iterator();
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
                try (ChangeBuilder changeBuilder = WrappingObservableSet.this.beginChange()) {
                    this.impl.remove();
                    changeBuilder.logRemove(this.cursor);
                }
            }

        };
    }

    @Override
    public int size() {
        return this.impl.size();
    }

}