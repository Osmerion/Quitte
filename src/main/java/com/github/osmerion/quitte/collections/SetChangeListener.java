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

import java.util.Collections;
import java.util.Set;
import javax.annotation.Nullable;

/**
 * A listener that may be used to subscribe to changes to one or more {@link ObservableSet observable sets}.
 *
 * @param <E>   the type of an observed set's elements
 *
 * @see ObservableSet
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
@FunctionalInterface
public interface SetChangeListener<E> {

    /**
     * Processes changes to an {@link ObservableSet} this listener is attached to.
     *
     * @param change    the change to process
     *
     * @since   0.1.0
     */
    void onChanged(Change<? extends E> change);

    /**
     * Returns whether or not this listener is invalid.
     *
     * <p>Once an {@link ObservableSet} discovers that a listener is invalid, it will stop notifying the listener of
     * updates and release all strong references to the listener.</p>
     *
     * <p>Once this method returned {@code true}, it must never return {@code false} again for the same instance.
     * Breaking this contract may result in unexpected behavior.</p>
     *
     * @return  whether or not this listener is invalid
     *
     * @since   0.1.0
     */
    default boolean isInvalid() {
        return false;
    }

    /**
     * A change done to an {@link ObservableSet}.
     *
     * <p>Note that adding an element that is already in a set does not modify the set and therefor no change will be
     * generated.</p>
     *
     * @param <E>   the type of the set's elements
     *
     * @since   0.1.0
     */
    final class Change<E> {

        private final Set<E> added, removed;

        Change(@Nullable Set<E> added, @Nullable Set<E> removed) {
            this.added = added != null ? Collections.unmodifiableSet(added) : Collections.emptySet();
            this.removed = removed != null ? Collections.unmodifiableSet(removed) : Collections.emptySet();
        }

        /**
         * Returns the elements that were added to the observed set as part of this change.
         *
         * @return  the elements that were added to the observed set as part of this change
         *
         * @since   0.1.0
         */
        public Set<E> getAddedElements() {
            return this.added;
        }

        /**
         * Returns the elements that were removed from the observed set as part of this change.
         *
         * @return  the elements that were removed from the observed set as part of this change
         *
         * @since   0.1.0
         */
        public Set<E> getRemovedElements() {
            return this.removed;
        }

    }

}