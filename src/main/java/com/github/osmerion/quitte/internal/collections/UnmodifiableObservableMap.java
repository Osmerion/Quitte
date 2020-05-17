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

import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import com.github.osmerion.quitte.collections.AbstractObservableMap;
import com.github.osmerion.quitte.collections.ObservableMap;

/**
 * A wrapper for an observable map that blocks mutation.
 *
 * @param <K>   the type of the map's keys
 * @param <V>   the type of the map's values
 *
 * @see com.github.osmerion.quitte.collections.ObservableMap#unmodifiableViewOf(ObservableMap)
 *
 * @serial include
 *
 * @author  Leon Linhart
 */
public final class UnmodifiableObservableMap<K, V> extends AbstractObservableMap<K, V> {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = -6114641986818029081L;

    private final ObservableMap<K, V> impl;

    @Nullable
    private transient Set<Entry<K, V>> entrySet;

    public UnmodifiableObservableMap(ObservableMap<K, V> impl) {
        this.impl = Objects.requireNonNull(impl);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        if (this.entrySet == null) this.entrySet = new UnmodifiableObservableEntrySet(this.impl.entrySet());
        return this.entrySet;
    }

    @Nullable
    @Override
    protected V putImpl(@Nullable K key, @Nullable V value) {
        throw new UnsupportedOperationException();
    }

}