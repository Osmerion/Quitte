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
package com.github.osmerion.quitte.collections;

import java.util.Map;
import com.github.osmerion.quitte.Observable;
import com.github.osmerion.quitte.internal.collections.UnmodifiableObservableMap;
import com.github.osmerion.quitte.internal.collections.WrappingObservableMap;

/**
 *  A observable map with support for tracking changes to the map's content.
 *
 * @param <K>   the type of the map's keys
 * @param <V>   the type of the map's values
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface ObservableMap<K, V> extends Map<K, V>, Observable {

    /**
     * Returns an observable view of the specified map. Query operations on the returned map "read and write through"
     * to the specified map.
     *
     * <p>The returned set will be serializable if the specified set is serializable. Similarly, the returned map will
     * permit {@code null} if the specified set does.</p>
     *
     * @param <K>   the type of the map's keys
     * @param <V>   the type of the map's values
     * @param map   the map to wrap
     *
     * @return  an observable view of the specified map
     *
     * @throws NullPointerException if the given map is {@code null}
     *
     * @since   0.1.0
     */
    static <K, V> ObservableMap<K, V> of(Map<K, V> map) {
        return new WrappingObservableMap<>(map);
    }

    /**
     * Returns an unmodifiable view of the specified {@link ObservableMap}.
     *
     * @param <K>   the type of the map's keys
     * @param <V>   the type of the map's values
     * @param map   the map to wrap
     *
     * @return  an unmodifiable view of the specified map
     *
     * @throws NullPointerException if the given map is {@code null}
     *
     * @see java.util.Collections#unmodifiableMap(Map)
     *
     * @since   0.1.0
     */
    static <K, V> ObservableMap<K, V> unmodifiableViewOf(ObservableMap<K, V> map) {
        return new UnmodifiableObservableMap<>(map);
    }

    /**
     * Adds the given listener to this map.
     *
     * @param listener  the listener for listening to changes to this map
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @since   0.1.0
     */
    void addListener(MapChangeListener<? super K, ? super V> listener);

    /**
     * Removes the given listener from this map. If the given listener has not been added to this map before, nothing
     * happens.
     *
     * @param listener  the listener to remove
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @since   0.1.0
     */
    void removeListener(MapChangeListener<? super K, ? super V> listener);

}