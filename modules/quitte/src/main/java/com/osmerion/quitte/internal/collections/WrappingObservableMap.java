/*
 * Copyright (c) 2018-2021 Leon Linhart,
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

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import com.osmerion.quitte.collections.AbstractObservableMap;

/**
 * An observable wrapper for a {@link Map}.
 *
 * @param <K>   the type of the map's keys
 * @param <V>   the type of the map's values
 *
 * @see com.osmerion.quitte.collections.ObservableMap#of(Map)
 *
 * @serial include
 * 
 * @author  Leon Linhart
 */
public final class WrappingObservableMap<K, V> extends AbstractObservableMap<K, V> {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = -3886697616002676509L;

    private final Map<K, V> impl;

    @Nullable
    private transient Set<Entry<K, V>> entrySet;

    public WrappingObservableMap(Map<K, V> impl) {
        this.impl = Objects.requireNonNull(impl);
    }

    @Override public V putImpl(@Nullable K key, @Nullable V value) { return this.impl.put(key, value); }

    @Override
    public Set<Entry<K, V>> entrySet() {
        if (this.entrySet == null) this.entrySet = new WrappingObservableEntrySet(this.impl.entrySet());
        return this.entrySet;
    }

}