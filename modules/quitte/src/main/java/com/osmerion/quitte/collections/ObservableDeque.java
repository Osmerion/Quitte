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

import java.util.Deque;
import java.util.Iterator;
import javax.annotation.Nullable;
import com.osmerion.quitte.Observable;
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
public interface ObservableDeque<E> extends Deque<E>, Observable {

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
     * Returns an unmodifiable view of the specified {@link ObservableSet}.
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
     * Adds the given listener to this deque.
     *
     * @param listener  the listener for listening to changes to this deque
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @since   0.1.0
     */
    void addListener(DequeChangeListener<? super E> listener);

    /**
     * Removes the given listener from this deque. If the given listener has not been added to this deque before,
     * nothing happens.
     *
     * @param listener  the listener to remove
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @since   0.1.0
     */
    void removeListener(DequeChangeListener<? super E> listener);

    /**
     * {@inheritDoc}
     *
     * <p><b>Modifications to an observable deque using the returned iterator should be made with caution as they
     * produce {@link com.osmerion.quitte.collections.DequeChangeListener.LocalChange.OpaqueLocalChange opaque changes}.
     * </b></p>
     */
    @Override
    Iterator<E> iterator();

    /**
     * {@inheritDoc}
     *
     * <p><b>Modifications to an observable deque using the returned iterator should be made with caution as they
     * produce {@link com.osmerion.quitte.collections.DequeChangeListener.LocalChange.OpaqueLocalChange opaque changes}.
     * </b></p>
     */
    @Override
    Iterator<E> descendingIterator();

    /**
     * {@inheritDoc}
     *
     * <p><b>This method should be used with caution as it produces
     * {@link com.osmerion.quitte.collections.DequeChangeListener.LocalChange.OpaqueLocalChange opaque changes}.</b></p>
     */
    @Override
    boolean remove(@Nullable Object o);

    /**
     * {@inheritDoc}
     *
     * <p><b>This method should be used with caution as it produces
     * {@link com.osmerion.quitte.collections.DequeChangeListener.LocalChange.OpaqueLocalChange opaque changes}.</b></p>
     */
    @Override
    boolean removeFirstOccurrence(Object o);

    /**
     * {@inheritDoc}
     *
     * <p><b>This method should be used with caution as it produces
     * {@link com.osmerion.quitte.collections.DequeChangeListener.LocalChange.OpaqueLocalChange opaque changes}.</b></p>
     */
    @Override
    boolean removeLastOccurrence(Object o);

}