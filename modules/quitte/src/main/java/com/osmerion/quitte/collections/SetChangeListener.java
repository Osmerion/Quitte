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
import java.util.HashSet;
import java.util.Set;

/**
 * A listener that may be used to subscribe to changes to one or more {@link ObservableSet observable set}.
 *
 * @param <E>   the type of the set elements
 *
 * @since   0.8.0
 */
public interface SetChangeListener<E> {

    /**
     * Processes changes to an {@link ObservableSet observable set} this listener is attached to.
     *
     * @param observable    the observable set
     * @param change        the change to process
     *
     * @since   0.8.0
     */
    void onChanged(ObservableSet<? extends E> observable, Change<? extends E> change);

    /**
     * {@return whether this listener is invalid}
     *
     * <p>Once an observable collection discovers that a listener is invalid, it will stop notifying the listener of
     * updates and release all strong references to the listener.</p>
     *
     * <p>Once this method returned {@code true}, it must never return {@code false} again for the same instance.
     * Breaking this contract may result in unexpected behavior.</p>
     *
     * @since   0.1.0
     */
    default boolean isInvalid() {
        return false;
    }

    /**
     * A change done to an {@link ObservableSet}.
     *
     * <p>Note that adding an element that is already in a set does not modify the set and therefore no change will be
     * generated.</p>
     *
     * @param <E>               the type of the set's elements
     * @param addedElements     the added elements
     * @param removedElements   the removed elements
     *
     * @since   0.8.0
     */
    record Change<E>(Set<E> addedElements, Set<E> removedElements) {

        /**
         * Creates a new {@code Change}.
         *
         * @param addedElements     the added elements
         * @param removedElements   the removed elements
         *
         * @since   0.8.0
         */
        @SuppressWarnings("Java9CollectionFactory") // Cannot replace the code with Set::copyOf because the maps might contain null keys and values
        public Change(@Nullable Set<E> addedElements, @Nullable Set<E> removedElements) {
            this.addedElements = addedElements != null ? Collections.unmodifiableSet(new HashSet<>(addedElements)) : Set.of();
            this.removedElements = removedElements != null ? Collections.unmodifiableSet(new HashSet<>(removedElements)) : Set.of();
        }

    }

}