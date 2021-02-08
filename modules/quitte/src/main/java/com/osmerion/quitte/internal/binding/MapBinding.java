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
package com.osmerion.quitte.internal.binding;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import com.osmerion.quitte.InvalidationListener;
import com.osmerion.quitte.WeakInvalidationListener;
import com.osmerion.quitte.collections.CollectionChangeListener;
import com.osmerion.quitte.collections.ObservableMap;
import com.osmerion.quitte.collections.WeakCollectionChangeListener;

/**
 * A specialized {@link Map} binding.
 *
 * @author  Leon Linhart
 */
public final class MapBinding<S, T, K, V> implements Binding {

    private final Deque<ObservableMap.Change<? extends S, ? extends T>> changes = new ArrayDeque<>();

    private final ObservableMap<S, T> source;

    private final InvalidationListener invalidationListener;
    private final CollectionChangeListener<ObservableMap.Change<? extends S, ? extends T>> changeListener;

    private final BiFunction<? super S, ? super T, Map.Entry<K, V>> transform;

    public MapBinding(Runnable invalidator, ObservableMap<S, T> source, BiFunction<? super S, ? super T, Map.Entry<K, V>> transform) {
        this.source = source;
        this.transform = transform;

        this.source.addListener(new WeakInvalidationListener(this.invalidationListener = (observable) -> invalidator.run()));
        this.source.addListener(new WeakCollectionChangeListener<>(this.changeListener = this.changes::addLast));
    }

    @SuppressWarnings("deprecation")
    public List<ObservableMap.Change<K, V>> getChanges() {
        List<ObservableMap.Change<K, V>> changes = new ArrayList<>(this.changes.size());
        Iterator<ObservableMap.Change<? extends S, ? extends T>> changeItr = this.changes.iterator();

        while (changeItr.hasNext()) {
            ObservableMap.Change<? extends S, ? extends T> change = changeItr.next();
            changes.add(change.copy(this.transform));
            changeItr.remove();
        }

        return changes;
    }

    @Override
    public void release() {
        this.source.removeListener(this.invalidationListener);
        this.source.removeListener(this.changeListener);
    }

}