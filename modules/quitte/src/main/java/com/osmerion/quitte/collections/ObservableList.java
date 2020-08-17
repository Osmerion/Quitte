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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import com.osmerion.quitte.Observable;
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
public interface ObservableList<E> extends List<E>, Observable {

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
     * Attaches the given {@link ListChangeListener change listener} to this list.
     *
     * <p>If the given listener is already attached to this list, this method does nothing and returns {@code false}.
     * </p>
     *
     * <p>While an {@code ListChangeListener} is attached to a list, it will be {@link ListChangeListener#onChanged(ListChangeListener.Change)}
     * notified} whenever the list is updated.</p>
     *
     * <p>This list stores a strong reference to the given listener until the listener is either removed explicitly by
     * calling {@link #removeListener(ListChangeListener)} or implicitly when this list discovers that the listener has
     * become {@link ListChangeListener#isInvalid() invalid}. Generally, it is recommended to use an instance of
     * {@link WeakListChangeListener} when possible to avoid leaking instances.</p>
     *
     * @param listener  the listener to be attached to this list
     *
     * @return  {@code true} if the listener was not previously attached to this list and has been successfully
     *          attached, or {@code false} otherwise
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @see #removeListener(ListChangeListener)
     *
     * @since   0.1.0
     */
    boolean addListener(ListChangeListener<? super E> listener);

    /**
     * Detaches the given {@link ListChangeListener change listener} from this list.
     *
     * <p>If the given listener is not attached to this list, this method does nothing and returns {@code false}.</p>
     *
     * @param listener  the listener to be detached from this list
     *
     * @return  {@code true} if the listener was attached to and has been detached from this list, or {@code false}
     *          otherwise
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @see #addListener(ListChangeListener)
     *
     * @since   0.1.0
     */
    boolean removeListener(ListChangeListener<? super E> listener);

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

}