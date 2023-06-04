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

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A listener that may be used to subscribe to changes to one or more {@link ObservableMap observable map}.
 *
 * @param <K>   the type of the map elements
 * @param <V>   the type of the map elements
 *
 * @since   0.8.0
 *
 * @author  Leon Linhart
 */
public interface MapChangeListener<K, V>  {

    /**
     * Processes changes to an {@link ObservableMap observable map} this listener is attached to.
     *
     * @param observable    the observable map
     * @param change        the change to process
     *
     * @since   0.8.0
     */
    void onChanged(ObservableMap<? extends K, ? extends V> observable, Change<? extends K, ? extends V> change);

    /**
     * {@return whether this listener is invalid}
     *
     * <p>Once an observable map discovers that a listener is invalid, it will stop notifying the listener of updates
     * and release all strong references to the listener.</p>
     *
     * <p>Once this method returned {@code true}, it must never return {@code false} again for the same instance.
     * Breaking this contract may result in unexpected behavior.</p>
     *
     * @since   0.8.0
     */
    default boolean isInvalid() {
        return false;
    }

    /**
     * A change done to an {@link ObservableMap}.
     *
     * @param <K>               the type of the map's elements
     * @param <V>               the type of the map's elements
     * @param addedElements     the elements added to the map
     * @param removedElements   the elements removed from the map
     * @param updatedElements   the elements in the map that were updated
     *
     *
     * @since   0.8.0
     */
    record Change<K, V>(
        Map<K, V> addedElements,
        Map<K, V> removedElements,
        Map<K, Update<V>> updatedElements
    ) {

        @SuppressWarnings("Java9CollectionFactory") // Cannot replace the code with Map::copyOf because the maps might contain null keys and values
        public Change(@Nullable Map<K, V> addedElements, @Nullable Map<K, V> removedElements, @Nullable Map<K, Update<V>> updatedElements) {
            this.addedElements = (addedElements != null) ? Collections.unmodifiableMap(new HashMap<>(addedElements)) : Map.of();
            this.removedElements = (removedElements != null) ? Collections.unmodifiableMap(new HashMap<>(removedElements)) : Map.of();
            this.updatedElements = (updatedElements != null) ? Collections.unmodifiableMap(new HashMap<>(updatedElements)) : Map.of();
        }

        /**
         * Describes an update to a map entry's value.
         *
         * @since   0.8.0
         */
        public record Update<V>(@Nullable V oldValue, @Nullable V newValue) {}

    }

}