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
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

import com.osmerion.quitte.internal.collections.UnmodifiableObservableSet;
import com.osmerion.quitte.internal.collections.WrappingObservableSet;

/**
 * An observable set with support for tracking changes to the set's content.
 *
 * @param <E>   the type of elements in this set
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface ObservableSet<E> extends Set<E>, ObservableCollection<ObservableSet.Change<? extends E>> {

    /**
     * Returns an observable view of the specified set. Query operations on the returned set "read and write through"
     * to the specified set.
     *
     * <p>The returned set will be serializable if the specified set is serializable. Similarly, the returned set will
     * permit {@code null} if the specified set does.</p>
     *
     * @param <T>   the type of the set's elements
     * @param set   the set to wrap
     *
     * @return  an observable view of the specified set
     *
     * @throws NullPointerException if the given set is {@code null}
     *
     * @since   0.1.0
     */
    static <T> ObservableSet<T> of(Set<T> set) {
        return new WrappingObservableSet<>(set);
    }

    /**
     * Returns an unmodifiable view of the specified {@link ObservableSet}.
     *
     * @param <T>   the type of the set's elements
     * @param set   the set to wrap
     *
     * @return  an unmodifiable view of the specified set
     *
     * @throws NullPointerException if the given set is {@code null}
     *
     * @see java.util.Collections#unmodifiableSet(Set) 
     * 
     * @since   0.1.0
     */
    static <T> ObservableSet<T> unmodifiableViewOf(ObservableSet<T> set) {
        return new UnmodifiableObservableSet<>(set);
    }

    /**
     * A change done to an {@link ObservableSet}.
     *
     * <p>Note that adding an element that is already in a set does not modify the set and therefore no change will be
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
            return new Change<>(
                this.added.stream().map(transform).collect(Collectors.toUnmodifiableSet()),
                this.removed.stream().map(transform).collect(Collectors.toUnmodifiableSet())
            );
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