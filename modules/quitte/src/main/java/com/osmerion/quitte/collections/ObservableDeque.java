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

import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;

import com.osmerion.quitte.internal.collections.UnmodifiableObservableDeque;
import com.osmerion.quitte.internal.collections.WrappingObservableDeque;

/**
 * A observable deque with support for tracking changes to the deque's content.
 * 
 * @param <E>   the type of the deque's elements
 *
 * @since   0.1.0
 * 
 * @author  Leon Linhart
 */
public interface ObservableDeque<E> extends Deque<E>, ObservableCollection<ObservableDeque.Change<? extends E>> {

    /**
     * Returns an observable view of the specified deque. Query operations on the returned deque "read and write
     * through" to the specified deque.
     *
     * <p>The returned deque will be serializable if the specified deque is serializable. Similarly, the returned deque
     * will permit {@code null} if the specified deque does.</p>
     *
     * @param <T>   the type of the deques elements
     * @param deque the deque to wrap
     *
     * @return  an observable view of the specified deque
     *
     * @throws NullPointerException if the given deque is {@code null}
     *
     * @since   0.1.0
     */
    static <T> ObservableDeque<T> of(Deque<T> deque) {
        return new WrappingObservableDeque<>(deque);
    }

    /**
     * Returns an unmodifiable view of the specified {@link ObservableDeque}.
     *
     * @param <T>   the type of deque's elements
     * @param deque the deque to wrap
     *
     * @return  an unmodifiable view of the specified deque
     *
     * @throws NullPointerException if the given deque is {@code null}
     *
     * @see QuitteCollections#unmodifiableDeque(Deque)
     *
     * @since   0.1.0
     */
    static <T> ObservableDeque<T> unmodifiableViewOf(ObservableDeque<T> deque) {
        return new UnmodifiableObservableDeque<>(deque);
    }

    /**
     * {@inheritDoc}
     *
     * <p><b>Modifications to an observable deque using the returned iterator should be made with caution as they
     * produce {@link Site#OPAQUE opaque changes}.
     * </b></p>
     */
    @Override
    Iterator<E> iterator();

    /**
     * {@inheritDoc}
     *
     * <p><b>Modifications to an observable deque using the returned iterator should be made with caution as they
     * produce {@link Site#OPAQUE opaque changes}.</b></p>
     */
    @Override
    Iterator<E> descendingIterator();

    /**
     * {@inheritDoc}
     *
     * <p><b>This method should be used with caution as it produces {@link Site#OPAQUE opaque changes}.</b></p>
     */
    @Override
    boolean remove(@Nullable Object o);

    /**
     * {@inheritDoc}
     *
     * <p><b>This method should be used with caution as it produces {@link Site#OPAQUE opaque changes}.</b></p>
     */
    @Override
    boolean removeFirstOccurrence(Object o);

    /**
     * {@inheritDoc}
     *
     * <p><b>This method should be used with caution as it produces {@link Site#OPAQUE opaque changes}.</b></p>
     */
    @Override
    boolean removeLastOccurrence(Object o);

    /**
     * A change to a deque consists of one or more {@link LocalChange local updates} that apply to a specific
     * {@link Site site} of the deque.
     *
     * @param <E>   the type of the deque's elements
     *
     * @since   0.1.0
     */
    record Change<E>(List<LocalChange<E>> localChanges) {

        public Change {
            localChanges = List.copyOf(localChanges);
        }

        /**
         * Creates a copy of this change using the given {@code transform} to map the elements.
         *
         * @param <T>       the new type for the elements
         * @param transform the transform function to be applied to the elements
         *
         * @return  a copy of this change
         *
         * @deprecated  This is an unsupported method that may be removed at any time.
         *
         * @since   0.1.0
         */
        @Deprecated
        public <T> Change<T> copy(Function<? super E, T> transform) {
            return new Change<>(this.localChanges.stream().map(it -> it.copy(transform)).toList());
        }

        /**
         * Returns a list of changes that are local to parts of the deque.
         *
         * <p><b>It is important to process local changes in order, since the order of the elements matters.</b></p>
         *
         * @return  a list of changes that are local to parts of the deque
         *
         * @deprecated  Deprecated in favor of canonical record accessor {@link #localChanges()}.
         *
         * @since   0.1.0
         */
        @Deprecated(since = "0.3.0", forRemoval = true)
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

        @Deprecated
        abstract <T> LocalChange<T> copy(Function<? super E, T> transform);

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

            @Override
            <T> LocalChange<T> copy(Function<? super E, T> transform) {
                return new Insertion<>(this.getSite(), this.getElements().stream().map(transform).toList());
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

            @Override
            <T> LocalChange<T> copy(Function<? super E, T> transform) {
                return new Removal<>(this.getSite(), this.getElements().stream().map(transform).toList());
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