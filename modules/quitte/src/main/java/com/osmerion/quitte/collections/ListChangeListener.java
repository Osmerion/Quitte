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
package com.osmerion.quitte.collections;

import java.util.Collections;
import java.util.List;

/**
 * A listener that may be used to subscribe to changes to one or more {@link ObservableList observable lists}.
 *
 * @param <E>   the type of an observed list's elements
 *
 * @see ObservableList
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
@FunctionalInterface
public interface ListChangeListener<E> {

    /**
     * Processes changes to an {@link ObservableList} this listener is attached to.
     *
     * @param change    the change to process
     *
     * @since   0.1.0
     */
    void onChanged(Change<? extends E> change);

    /**
     * Returns whether or not this listener is invalid.
     *
     * <p>Once an {@link ObservableList} discovers that a listener is invalid, it will stop notifying the listener of
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
     * A change to a list may either be a {@link Permutation permutation}, or one or more local updates to parts of the
     * list (represented as {@link LocalChange}).
     *
     * <p>Using {@code instanceof} checks (or similar future pattern matching mechanisms) is recommended when working
     * with {@code Change} objects.</p>
     *
     * @param <E>   the type of the list's elements
     *
     * @since   0.1.0
     */
    abstract class Change<E> {

        private Change() {} // TODO This is an ideal candidate for a sealed record hierarchy.

        /**
         * A change to a list in which its elements are rearranged.
         *
         * @since   0.1.0
         */
        public static final class Permutation<E> extends Change<E> {

            private final List<Integer> indices;

            Permutation(List<Integer> indices) {
                this.indices = indices;
            }

            /**
             * A list of integers that represents the mapping of indices used to create the current permutation.
             *
             * @return  a list of integers that represents the mapping of indices used to create the current permutation
             *
             * @since   0.1.0
             */
            public List<Integer> getIndices() {
                return this.indices;
            }

        }

        /**
         * A change to a list that consists of one or more local changes to the list.
         *
         * @since   0.1.0
         */
        public static final class Update<E> extends Change<E> {

            private final List<LocalChange<E>> localChanges;

            Update(List<LocalChange<E>> localChanges) {
                this.localChanges = localChanges;
            }

            /**
             * Returns a list of changes that are local to parts of the list.
             *
             * <p><b>It is important to process local changes in order, since their indices depend on another.</b></p>
             *
             * @return  a list of changes that are local to parts of the list
             *
             * @since   0.1.0
             */
            public List<LocalChange<E>> getLocalChanges() {
                return this.localChanges;
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
     * @since   0.1.0
     */
    abstract class LocalChange<E> {

        private final int index;
        private final List<E> elements;

        // TODO This is an ideal candidate for a sealed record hierarchy.
        private LocalChange(int index, List<E> elements) {
            this.index = index;
            this.elements = Collections.unmodifiableList(elements);
        }

        /**
         * Returns the index of the first element affected by this change.
         *
         * @return  the index of the first element affected by this change
         *
         * @since   0.1.0
         */
        public final int getIndex() {
            return this.index;
        }

        /**
         * A list of elements related to this change. How this list should be interpreted is defined by an implementing
         * class.
         *
         * @return  a list of elements related to this change
         *
         * @since   0.1.0
         */
        public final List<E> getElements() {
            return this.elements;
        }

        /**
         * Represents insertion of one or more subsequent {@link #getElements() elements} starting from a given
         * {@link #getIndex() index}.
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
         * @since   0.1.0
         */
        public static final class Insertion<E> extends LocalChange<E> {

            Insertion(int index, List<E> elements) {
                super(index, elements);
            }

        }

        /**
         * Represents removal of one or more subsequent {@link #getElements() elements} starting from a given
         * {@link #getIndex() index}.
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
         * @since   0.1.0
         */
        public static final class Removal<E> extends LocalChange<E> {

            Removal(int index, List<E> elements) {
                super(index, elements);
            }

        }

        /**
         * Represents an update of one or more subsequent {@link #getElements() elements} starting from a given
         * {@link #getIndex() index}.
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
         * @since   0.1.0
         */
        public static final class Update<E> extends LocalChange<E> {

            Update(int index, List<E> elements) {
                super(index, elements);
            }

        }

    }

}