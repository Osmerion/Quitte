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
package com.osmerion.quitte.collections;

import java.util.Collections;
import java.util.Map;
import javax.annotation.Nullable;

import com.osmerion.quitte.internal.collections.UnmodifiableObservableMap;
import com.osmerion.quitte.internal.collections.WrappingObservableMap;

/**
 * An observable map with support for tracking changes to the map's content.
 *
 * @param <K>   the type of the map's keys
 * @param <V>   the type of the map's values
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface ObservableMap<K, V> extends Map<K, V>, ObservableCollection<ObservableMap.Change<? extends K, ? extends V>> {

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
     * A change done to an {@link ObservableMap}.
     *
     * @param <K>   the type of the map's elements
     * @param <V>   the type of the map's elements
     *
     * @since   0.1.0
     */
    record Change<K, V>(
        Map<K, V> addedElements,
        Map<K, V> removedElements,
        Map<K, Update<V>> updatedElements
    ) {

        public Change(@Nullable Map<K, V> addedElements, @Nullable Map<K, V> removedElements, @Nullable Map<K, Update<V>> updatedElements) {
            this.addedElements = (addedElements != null) ? Collections.unmodifiableMap(addedElements) : Collections.emptyMap();
            this.removedElements = (removedElements != null) ? Collections.unmodifiableMap(removedElements) : Collections.emptyMap();
            this.updatedElements = (updatedElements != null) ? Collections.unmodifiableMap(updatedElements) : Collections.emptyMap();
        }

        /**
         * Describes an update to a map entry's value.
         *
         * @since   0.1.0
         */
        public record Update<V>(@Nullable V oldValue, @Nullable V newValue) {

            /**
             * Returns the old value.
             *
             * @return  the old value
             *
             * @deprecated  Deprecated in favor of canonical record accessor {@link #oldValue()}.
             *
             * @since   0.1.0
             */
            @Deprecated(since = "0.8.0", forRemoval = true)
            @Nullable
            public V getOldValue() {
                return this.oldValue;
            }

            /**
             * Returns the new value.
             *
             * @return  the new value
             *
             * @deprecated  Deprecated in favor of canonical record accessor {@link #newValue()}.
             *
             * @since   0.1.0
             */
            @Deprecated(since = "0.8.0", forRemoval = true)
            @Nullable
            public V getNewValue() {
                return this.newValue;
            }

        }

    }

}