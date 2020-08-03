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

import java.util.List;

/**
 * A listener that may be used to subscribe to changes to one or more {@link ObservableDeque observable deques}.
 *
 * @param <E>   the type of an observed deque's elements
 *
 * @see ObservableDeque
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
@FunctionalInterface
public interface DequeChangeListener<E> {

    /**
     * Processes changes to an {@link ObservableDeque} this listener is attached to.
     *
     * @param change    the change to process
     *
     * @since   0.1.0
     */
    void onChanged(Change<? extends E> change);

    /**
     * Returns whether or not this listener is invalid.
     *
     * @return  whether or not this listener is invalid
     *
     * @since   0.1.0
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
     * @since   0.1.0
     */
    final class Change<E> {

        private final List<LocalChange<E>> localChanges;

        Change(List<LocalChange<E>> localChanges) {
            this.localChanges = localChanges;
        }

        /**
         * Returns a list of changes that are local to parts of the deque.
         *
         * <p><b>It is important to process local changes in order, since the order of the elements matters.</b></p>
         *
         * @return  a list of changes that are local to parts of the deque
         *
         * @since   0.1.0
         */
        public List<LocalChange<E>> getLocalChanges() {
            return this.localChanges;
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
     * @since   0.1.0
     */
    abstract class LocalChange<E> {

        private final Site site;
        private final List<E> elements;

        // TODO This is an ideal candidate for a sealed record hierarchy.
        private LocalChange(Site site, List<E> elements) {
            this.site = site;
            this.elements = elements;
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
         * The {@link Site site} of the deque to which the change applies.
         *
         * @return  site of the deque to which the change applies
         *
         * @since   0.1.0
         */
        public final Site getSite() {
            return this.site;
        }

        /**
         * Represents insertion of one or more subsequent {@link #getElements() elements} starting from a given
         * {@link #getSite() site}.
         *
         * @since   0.1.0
         */
        public static final class Insertion<E> extends LocalChange<E> {

            Insertion(Site site, List<E> elements) {
                super(site, elements);
            }

        }

        /**
         * Represents removal of one or more subsequent {@link #getElements() elements} starting from a given
         * {@link #getSite() site}.
         *
         * @since   0.1.0
         */
        public static final class Removal<E> extends LocalChange<E> {

            Removal(Site site, List<E> elements) {
                super(site, elements);
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