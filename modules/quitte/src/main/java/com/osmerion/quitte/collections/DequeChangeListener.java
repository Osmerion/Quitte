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

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * A listener that may be used to subscribe to changes to one or more {@link ObservableDeque observable deque}.
 *
 * @param <E>   the type of the deque's elements
 *
 * @since   0.8.0
 *
 * @author  Leon Linhart
 */
public interface DequeChangeListener<E extends @Nullable Object> {

    /**
     * Processes changes to an {@link ObservableDeque observable deque} this listener is attached to.
     *
     * @param observable    the observable deque
     * @param change        the change to process
     *
     * @since   0.8.0
     */
    void onChanged(ObservableDeque<? extends E> observable, DequeChangeListener.Change<? extends E> change);

    /**
     * {@return whether this listener is invalid}
     *
     * <p>Once an observable collection discovers that a listener is invalid, it will stop notifying the listener of
     * updates and release all strong references to the listener.</p>
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
     * A change to a deque consists of one or more {@link LocalChange local updates} that apply to a specific
     * {@link Site site} of the deque.
     *
     * @param <E>   the type of the deque's elements
     *
     * @since   0.8.0
     */
    record Change<E>(List<? extends LocalChange<E>> localChanges) {

        public Change {
            localChanges = List.copyOf(localChanges);
        }

    }

    /**
     * A change to a deque. This might either be an {@link Insertion}, or a {@link Removal}.
     *
     * <p>Using {@code instanceof} checks (or similar future pattern matching mechanisms) is recommended when working
     * with {@code LocalChange} objects.</p>
     *
     * @param <E>   the type of the deque's elements
     *
     * @since   0.8.0
     */
    sealed interface LocalChange<E> {

        /**
         * A list of elements related to this change. How this list should be interpreted is defined by an implementing
         * class.
         *
         * @return  a list of elements related to this change
         *
         * @since   0.8.0
         */
        List<E> elements();

        /**
         * {@return {@link Site site} of the deque to which the change applies}
         *
         * @since   0.8.0
         */
        Site site();

        /**
         * Represents insertion of one or more subsequent {@link #elements() elements} starting from a given
         * {@link #site() site}.
         *
         * @since   0.8.0
         */
        record Insertion<E>(
            Site site,
            List<E> elements
        ) implements LocalChange<E> {

            @SuppressWarnings("Java9CollectionFactory") // Cannot replace the code with List::copyOf because the maps might contain null keys and values
            public Insertion {
                elements = Collections.unmodifiableList(new ArrayList<>(elements));
            }

        }

        /**
         * Represents removal of one or more subsequent {@link #elements() elements} starting from a given
         * {@link #site() site}.
         *
         * @since   0.8.0
         */
        record Removal<E>(
            Site site,
            List<E> elements
        ) implements LocalChange<E> {

            @SuppressWarnings("Java9CollectionFactory") // Cannot replace the code with List::copyOf because the maps might contain null keys and values
            public Removal {
                elements = Collections.unmodifiableList(new ArrayList<>(elements));
            }

        }

    }

    /**
     * A modifiable site of a deque.
     *
     * @since   0.1.0
     */
    enum Site {
        /**
         * The "head" (or "front") of the deque.
         *
         * @since   0.1.0
         */
        HEAD,
        /**
         * The "tail" (or "back") of the deque.
         *
         * @since   0.1.0
         */
        TAIL,
        /**
         * An opaque position in the deque.
         *
         * <p>Using operations which produce "opaque change" is discouraged.</p>
         *
         * @since   0.1.0
         */
        OPAQUE
    }

}