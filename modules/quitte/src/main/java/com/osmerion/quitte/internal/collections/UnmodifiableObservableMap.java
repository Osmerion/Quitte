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
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;

import com.osmerion.quitte.InvalidationListener;
import com.osmerion.quitte.collections.MapChangeListener;
import com.osmerion.quitte.collections.ObservableMap;
import com.osmerion.quitte.collections.ObservableSet;

/**
 * A wrapper for an {@link ObservableMap} that blocks mutation.
 *
 * @param <K>   the type of the map's keys
 * @param <V>   the type of the map's values
 *
 * @see com.osmerion.quitte.collections.ObservableMap#unmodifiableViewOf(ObservableMap)
 *
 * @serial include
 *
 * @author  Leon Linhart
 */
public class UnmodifiableObservableMap<K, V> implements ObservableMap<K, V> {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = -6114641986818029081L;

    private final ObservableMap<K, V> impl;

    public UnmodifiableObservableMap(ObservableMap<K, V> impl) {
        this.impl = Objects.requireNonNull(impl);
    }

    @Override public boolean addInvalidationListener(InvalidationListener listener) { return this.impl.addInvalidationListener(listener); }
    @Override public boolean removeInvalidationListener(InvalidationListener listener) { return this.impl.removeInvalidationListener(listener); }
    @Override public boolean addChangeListener(MapChangeListener<? super K, ? super V> listener) { return this.impl.addChangeListener(listener); }
    @Override public boolean removeChangeListener(MapChangeListener<? super K, ? super V> listener) { return this.impl.removeChangeListener(listener); }

    @Override public boolean containsKey(Object key) { return this.impl.containsKey(key); }
    @Override public boolean containsValue(Object value) { return this.impl.containsValue(value); }

    @Nullable
    private transient ObservableSet<Entry<K, V>> entrySet;

    @Override
    public ObservableSet<Entry<K, V>> entrySet() {
        if (this.entrySet == null) this.entrySet = ObservableSet.unmodifiableViewOf(this.impl.entrySet());
        return this.entrySet;
    }

    @Override public V get(Object key) { return this.impl.get(key); }

    @Override public boolean isEmpty() { return this.impl.isEmpty(); }

    @Override
    public ObservableSet<K> keySet() {
        return ObservableSet.unmodifiableViewOf(this.impl.keySet());
    }

    @Override public int size() { return this.impl.size(); }

    @Override
    public Collection<V> values() {
        return Collections.unmodifiableCollection(this.impl.values());
    }

    @Override public void clear() { throw new UnsupportedOperationException(); }
    @Override public V put(K key, V value) { throw new UnsupportedOperationException(); }
    @Override public void putAll(Map<? extends K, ? extends V> m) { throw new UnsupportedOperationException(); }
    @Override public V remove(Object key) { throw new UnsupportedOperationException(); }

}