/*
 * Copyright (c) 2018-2021 Leon Linhart,
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.osmerion.quitte.internal.collections.UnmodifiableRandomAccessObservableList;
import com.osmerion.quitte.internal.collections.WrappingObservableList;
import com.osmerion.quitte.internal.collections.WrappingRandomAccessObservableList;

/**
 * A observable list with support for tracking changes to the list's content.
 *
 * @param <E>   the type of elements in this list
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface ObservableList<E> extends List<E>, ObservableCollection<ObservableList.Change<? extends E>> {

    /**
     * Returns an observable view of the specified list. Query operations on the returned list "read and write through"
     * to the specified list.
     *
     * <p>The returned list will be serializable if the specified list is serializable. Similarly, the returned list
     * will implement {@link RandomAccess} if the specified list does.</p>
     *
     * @param <T>   the type of the list's elements
     * @param list  the list to wrap
     *
     * @return  an observable view of the specified list
     *
     * @throws NullPointerException if the given list is {@code null}
     *
     * @since   0.1.0
     */
    static <T> ObservableList<T> of(List<T> list) {
        return list instanceof RandomAccess ? new WrappingRandomAccessObservableList<>(list) : new WrappingObservableList<>(list);
    }

    /**
     * Returns an unmodifiable view of the specified {@link ObservableList}.
     *
     * @param <T>   the type of the list's elements
     * @param list  the list to wrap
     *
     * @return  an unmodifiable view of the specified list
     *
     * @throws NullPointerException if the given list is {@code null}
     *
     * @see java.util.Collections#unmodifiableList(List)
     *
     * @since   0.1.0
     */
    static <T> ObservableList<T> unmodifiableViewOf(ObservableList<T> list) {
        return list instanceof RandomAccess ? new UnmodifiableRandomAccessObservableList<>(list) : new UnmodifiableRandomAccessObservableList<>(list);
    }

    /**
     * See {@link #addAll(Collection)}.
     *
     * @param elements  the elements to be added to this list
     *
     * @return  {@code true} if this list changed as a result of the call, or {@code false} otherwise
     *
     * @throws ClassCastException               if the type of the specified element is incompatible with this list
     *                                          (optional)
     * @throws IllegalArgumentException         if some property of an element of the specified collection prevents it
     *                                          from being added to this list
     * @throws NullPointerException             if the specified element is {@code null} and this list does not permit
     *                                          {@code null}
     * @throws UnsupportedOperationException    if the {@code addAll} operation is not supported by this list
     * 
     * @since   0.1.0
     */
    @SuppressWarnings("unchecked")
    default boolean addAll(E... elements) {
        return this.addAll(Arrays.asList(elements));
    }

    /**
     * See {@link #removeAll(Collection)}.
     *
     * @param elements  the elements to be removed
     *
     * @return  {@code true} if this list changed as a result of the call, or {@code false} otherwise
     *
     * @throws ClassCastException               if the class of an element of this list is incompatible with the
     *                                          specified collection (optional)
     * @throws NullPointerException             if this list contains a {@code null} element and the specified
     *                                          collection does not permit {@code null} elements (optional), or if the
     *                                          specified collection is {@code null}
     * @throws UnsupportedOperationException    if the {@code removeAll} operation is not supported by this list
     * 
     * @since   0.1.0
     */
    @SuppressWarnings("unchecked")
    default boolean removeAll(E... elements) {
        return this.removeAll(Arrays.asList(elements));
    }

    /**
     * See {@link #retainAll(Collection)}.
     *
     * @param elements  the elements to be retained
     *
     * @return  {@code true} if this list changed as a result of the call, or {@code false} otherwise
     *
     * @throws ClassCastException               if the class of an element of this list is incompatible with the
     *                                          specified collection (optional)
     * @throws NullPointerException             if this list contains a {@code null} element and the specified
     *                                          collection does not permit {@code null} elements (optional), or if the
     *                                          specified collection is {@code null}
     * @throws UnsupportedOperationException    if the {@code retainAll} operation is not supported by this list
     * 
     * @since   0.1.0
     */
    @SuppressWarnings("unchecked")
    default boolean retainAll(E... elements) {
        return this.retainAll(Arrays.asList(elements));
    }

    /**
     * See {@link #setAll(Collection)}.
     *
     * @param elements  the elements to replace the content of this list
     *
     * @return  {@code true} if this list changed as a result of the call, or {@code false} otherwise
     *
     * @throws ClassCastException               if the type of the specified element is incompatible with this list
     *                                          (optional)
     * @throws NullPointerException             if the specified element is {@code null} and this list does not permit
     *                                          {@code null} elements (optional)
     * @throws UnsupportedOperationException    if the {@code setAll} operation is not supported by this list
     *
     * @since   0.1.0
     */
    @SuppressWarnings("unchecked")
    default boolean setAll(E... elements) {
        return this.setAll(Arrays.asList(elements));
    }

    /**
     * Clears the list and adds all elements from the given collection.
     *
     * @param elements  the elements to replace the content of this list
     *
     * @return  {@code true} if this list changed as a result of the call, or {@code false} otherwise
     *
     * @throws ClassCastException               if the type of the specified element is incompatible with this list
     *                                          (optional)
     * @throws NullPointerException             if the specified element is {@code null} and this list does not permit
     *                                          {@code null} elements (optional)
     * @throws UnsupportedOperationException    if the {@code setAll} operation is not supported by this list
     *
     * @since   0.1.0
     */
    boolean setAll(Collection<E> elements);

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
        public abstract <T> Change<T> copy(Function<? super E, T> transform);

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
             * {@inheritDoc}
             *
             * @since   0.1.0
             */
            @SuppressWarnings("unchecked")
            @Deprecated
            @Override
            public <T> Change<T> copy(Function<? super E, T> transform) {
                return (Permutation<T>) this;
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
             * {@inheritDoc}
             *
             * @since   0.1.0
             */
            @Deprecated
            @Override
            public <T> Change<T> copy(Function<? super E, T> transform) {
                return new Update<>(this.localChanges.stream().map(it -> it.copy(transform)).collect(Collectors.toUnmodifiableList()));
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

        @Deprecated
        abstract <T> LocalChange<T> copy(Function<? super E, T> transform);

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

            @Override
            <T> LocalChange<T> copy(Function<? super E, T> transform) {
                return new Insertion<>(this.getIndex(), this.getElements().stream().map(transform).collect(Collectors.toUnmodifiableList()));
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

            @Override
            <T> LocalChange<T> copy(Function<? super E, T> transform) {
                return new Removal<>(this.getIndex(), this.getElements().stream().map(transform).collect(Collectors.toUnmodifiableList()));
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

            @Override
            <T> LocalChange<T> copy(Function<? super E, T> transform) {
                return new Update<>(this.getIndex(), this.getElements().stream().map(transform).collect(Collectors.toUnmodifiableList()));
            }

        }

    }

}