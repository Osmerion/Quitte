/*
 * Copyright (c) 2018-2022 Leon Linhart,
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
import java.util.function.BiFunction;
import java.util.stream.Collectors;
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
    final class Change<K, V> {

        private final Map<K, V> added, removed;
        private final Map<K, Update<V>> updated;

        Change(@Nullable Map<K, V> added, @Nullable Map<K, V> removed, @Nullable Map<K, Update<V>> updated) {
            this.added = (added != null) ? Collections.unmodifiableMap(added) : Collections.emptyMap();
            this.removed = (removed != null) ? Collections.unmodifiableMap(removed) : Collections.emptyMap();
            this.updated = (updated != null) ? Collections.unmodifiableMap(updated) : Collections.emptyMap();
        }

        /**
         * Creates a copy of this change using the given {@code transform} to map the entries.
         *
         * @param <S>       the new type for the keys
         * @param <T>       the new type for the values
         * @param transform the transform function to be applied to the entries
         *
         * @return  a copy of this change
         *
         * @deprecated  This is an unsupported method that may be removed at any time.
         *
         * @since   0.1.0
         */
        @Deprecated
        public <S, T> Change<S, T> copy(BiFunction<? super K, ? super V, Entry<S, T>> transform) {
            return new Change<>(
                this.added.entrySet().stream().map(e -> transform.apply(e.getKey(), e.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                this.removed.entrySet().stream().map(e -> transform.apply(e.getKey(), e.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                this.updated.entrySet().stream().map(e -> {
                    Map.Entry<S, T> oldValue = transform.apply(e.getKey(), e.getValue().getOldValue());
                    Map.Entry<S, T> newValue = transform.apply(e.getKey(), e.getValue().getNewValue());

                    return Map.entry(newValue.getKey(), new Update<>(oldValue.getValue(), newValue.getValue()));
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }

        /**
         * Returns the entries that were added to the observed map as part of this change.
         *
         * @return  the entries that were added to the observed map as part of this change
         *
         * @since   0.1.0
         */
        public Map<K, V> getAddedElements() {
            return this.added;
        }

        /**
         * Returns the entries that were removed from the observed map as part of this change.
         *
         * @return  the entries that were removed from the observed map as part of this change
         *
         * @since   0.1.0
         */
        public Map<K, V> getRemovedElements() {
            return this.removed;
        }

        /**
         * Returns the entries that were updated in the observed map as part of this change.
         *
         * @return  the entries that were updated in the observed map as part of this change
         *
         * @since   0.1.0
         */
        public Map<K, Update<V>> getUpdatedElements() {
            return this.updated;
        }

        /**
         * Describes an update to a map entry's value.
         *
         * @since   0.1.0
         */
        public static final class Update<V> {

            @Nullable
            private final V oldValue, newValue;

            Update(@Nullable V oldValue, @Nullable V newValue) {
                this.oldValue = oldValue;
                this.newValue = newValue;
            }

            /**
             * Returns the old value.
             *
             * @return  the old value
             *
             * @since   0.1.0
             */
            @Nullable
            public V getOldValue() {
                return this.oldValue;
            }

            /**
             * Returns the new value.
             *
             * @return  the new value
             *
             * @since   0.1.0
             */
            @Nullable
            public V getNewValue() {
                return this.newValue;
            }

        }

    }

}