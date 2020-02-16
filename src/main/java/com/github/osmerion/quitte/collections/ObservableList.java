/*
 * Copyright (c) 2018-2019 Leon Linhart,
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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import com.github.osmerion.quitte.collections.change.ListChangeListener;

/**
 * A observable list with support for tracking changes to the list's content.
 *
 * @param <E>   the type of elements in this list
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface ObservableList<E> extends List<E> {

    /**
     * Adds the given listener to this list.
     *
     * @param listener  the listener for listening to changes to this list
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @since   0.1.0
     */
    void addChangeListener(ListChangeListener<? super E> listener);

    /**
     * Removes the given listener from this list. If the given listener has not been added to this list before, nothing
     * happens.
     *
     * @param listener  the listener to remove
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @since   0.1.0
     */
    void removeChangeListener(ListChangeListener<? super E> listener);

    /**
     * See {@link #addAll(Collection)}.
     *
     * @return  {@code true} if this list changed as a result of the call
     *
     * @throws UnsupportedOperationException    if the {@code addAll} operation is not supported by this list
     * @throws ClassCastException               if the type of the specified element is incompatible with this list
     *                                          (optional)
     * @throws NullPointerException             if the specified element is {@code null} and this list does not permit
     *                                          {@code null}
     * @throws IllegalArgumentException         if some property of an element of the specified collection prevents it
     *                                          from being added to this list
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
     * @return  {@code true} if this list changed as a result of the call
     *
     * @throws UnsupportedOperationException    if the {@code removeAll} operation is not supported by this list
     * @throws ClassCastException               if the class of an element of this list is incompatible with the
     *                                          specified collection (optional)
     * @throws NullPointerException             if this list contains a {@code null} element and the specified
     *                                          collection does not permit {@code null} elements (optional), or if the
     *                                          specified collection is {@code null}
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
     * @return  {@code true} if this list changed as a result of the call
     *
     * @throws UnsupportedOperationException    if the {@code retainAll} operation is not supported by this list
     * @throws ClassCastException               if the class of an element of this list is incompatible with the
     *                                          specified collection (optional)
     * @throws NullPointerException             if this list contains a {@code null} element and the specified
     *                                          collection does not permit {@code null} elements (optional), or if the
     *                                          specified collection is {@code null}
     * @since   0.1.0
     */
    @SuppressWarnings("unchecked")
    default boolean retainAll(E... elements) {
        return this.retainAll(Arrays.asList(elements));
    }

    /**
     * See {@link #setAll(Collection)}.
     *
     * @return  {@code true} if this list changed as a result of the call
     *
     * @throws ClassCastException               if the type of the specified element is incompatible with this list
     *                                          (optional)
     * @throws NullPointerException             if the specified element is {@code null} and this list does not permit
     *                                          {@code null} elements (optional)
     * @throws UnsupportedOperationException    if the {@code remove} operation is not supported by this list
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
     * @param elements  the elements to be added
     *
     * @return  {@code true} if this list changed as a result of the call
     *
     * @throws ClassCastException               if the type of the specified element is incompatible with this list
     *                                          (optional)
     * @throws NullPointerException             if the specified element is {@code null} and this list does not permit
     *                                          {@code null} elements (optional)
     * @throws UnsupportedOperationException    if the {@code remove} operation is not supported by this list
     *
     * @since   0.1.0
     */
    boolean setAll(Collection<E> elements);

}