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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import com.osmerion.quitte.collections.AbstractObservableList;

/**
 * An observable wrapper for a {@link List}.
 *
 * @param <E>   the type of the list's elements
 *
 * @see com.osmerion.quitte.collections.ObservableList#of(List)
 *
 * @serial include
 *
 * @author  Leon Linhart
 */
public class WrappingObservableList<E> extends AbstractObservableList<E> {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 8317257705662471582L;

    private final List<E> impl;

    public WrappingObservableList(List<E> impl) {
        this.impl = Objects.requireNonNull(impl);
    }

    @Override public void addImpl(int index, @Nullable E element) { this.impl.add(index, element); }
    @Override public E removeImpl(int index) { return this.impl.remove(index); }
    @Override public E setImpl(int index, @Nullable E element) { return this.impl.set(index, element); }
    @Override protected void sortImpl(Comparator<? super E> comparator) { this.impl.sort(comparator); }

    @Override public E get(int index) { return this.impl.get(index); }
    @Override public int size() { return this.impl.size(); }

}