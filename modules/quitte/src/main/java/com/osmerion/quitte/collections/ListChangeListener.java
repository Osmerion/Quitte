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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A listener that may be used to subscribe to changes to one or more {@link ObservableList observable list}.
 *
 * @param <E>   the type of the list elements
 *
 * @since   0.8.0
 *
 * @author  Leon Linhart
 */
public interface ListChangeListener<E> {

    /**
     * Processes changes to an {@link ObservableList observable list} this listener is attached to.
     *
     * @param observable    the observable list
     * @param change        the change to process
     *
     * @since   0.8.0
     */
    void onChanged(ObservableList<? extends E> observable, Change<? extends E> change);

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
     * A change to a list may either be a {@link Permutation permutation}, or one or more local updates to parts of the
     * list (represented as {@link LocalChange}).
     *
     * <p>Using {@code instanceof} checks (or similar future pattern matching mechanisms) is recommended when working
     * with {@code Change} objects.</p>
     *
     * @param <E>   the type of the list's elements
     *
     * @since   0.8.0
     */
    sealed interface Change<E> {

        /**
         * A change to a list in which its elements are rearranged.
         *
         * @since   0.8.0
         */
        record Permutation<E>(
            List<Integer> indices
        ) implements Change<E> {

            public Permutation {
                indices = List.copyOf(indices);
            }

        }

        /**
         * A change to a list that consists of one or more local changes to the list.
         *
         * @since   0.8.0
         */
        record Update<E>(
            List<? extends LocalChange<E>> localChanges
        ) implements Change<E> {

            public Update {
                localChanges = List.copyOf(localChanges);
            }

        }

    }

    /**
     * A change to a list. This might either be an {@link Insertion}, a {@link Removal}, or an {@link Update}.
     *
     * <p>Using {@code instanceof} checks (or similar future pattern matching mechanisms) is recommended when working
     * with {@code LocalChange} objects.</p>
     *
     * @param <E>   the type of the list's elements
     *
     * @since   0.8.0
     */
    sealed interface LocalChange<E> {

        /**
         * Returns the index of the first element affected by this change.
         *
         * @return  the index of the first element affected by this change
         *
         * @since   0.8.0
         */
        int index();

        /**
         * Represents insertion of one or more subsequent {@link #elements() elements} starting from a given
         * {@link #index() index}.
         *
         * <p><b>Example:</b></p>
         * <pre>
         * Initial
         * Indices:   0 1 2 3 4 5
         * Elements:  A B C D E F
         *              ^
         * Insertion    |
         * Index:       1
         * Elements:    X Y Z
         *
         * Result
         * Indices:   0 1 2 3 4 5 6 7 8
         * Elements:  A X Y Z B C D E F
         *
         * </pre>
         *
         * @param index     the index of the first element affected by this change
         * @param elements  the list of inserted elements
         *
         * @since   0.8.0
         */
        record Insertion<E>(
            int index,
            List<E> elements
        ) implements LocalChange<E> {

            @SuppressWarnings("Java9CollectionFactory") // Cannot replace the code with List::copyOf because the maps might contain null keys and values
            public Insertion {
                elements = Collections.unmodifiableList(new ArrayList<>(elements));
            }

        }

        /**
         * Represents removal of one or more subsequent {@link #elements() elements} starting from a given
         * {@link #index() index}.
         *
         * <p><b>Example:</b></p>
         * <pre>
         * Initial
         * Indices:   0 1 2 3 4 5
         * Elements:  A B C D E F
         *              ^
         * Removal      |
         * Index:       1
         * Elements:    B C D
         *
         * Result
         * Indices:   0 1 2
         * Elements:  A E F
         *
         * </pre>
         *
         * @param index     the index of the first element affected by this change
         * @param elements  the list of removed elements
         *
         * @since   0.8.0
         */
        record Removal<E>(
            int index,
            List<E> elements
        ) implements LocalChange<E> {

            @SuppressWarnings("Java9CollectionFactory") // Cannot replace the code with List::copyOf because the maps might contain null keys and values
            public Removal {
                elements = Collections.unmodifiableList(new ArrayList<>(elements));
            }

        }

        /**
         * Represents an update of one or more subsequent {@link #newElements() elements} starting from a given
         * {@link #index() index}.
         *
         * <p><b>Example:</b></p>
         * <pre>
         * Initial
         * Indices:   0 1 2 3 4 5
         * Elements:  A B C D E F
         *              ^
         * Update       |
         * Index:       1
         * Elements:    X Y Z
         *
         * Result
         * Indices:   0 1 2 3 4 5
         * Elements:  A X Y Z E F
         *
         * </pre>
         *
         * @param index         the index of the first element affected by this change
         * @param oldElements   the list of previous elements
         * @param newElements   the list of updated elements
         *
         * @since   0.8.0
         */
        record Update<E>(
            int index,
            List<E> oldElements,
            List<E> newElements
        ) implements LocalChange<E> {

            @SuppressWarnings("Java9CollectionFactory") // Cannot replace the code with List::copyOf because the maps might contain null keys and values
            public Update {
                oldElements = Collections.unmodifiableList(new ArrayList<>(oldElements));
                newElements = Collections.unmodifiableList(new ArrayList<>(newElements));
            }

        }

    }

}