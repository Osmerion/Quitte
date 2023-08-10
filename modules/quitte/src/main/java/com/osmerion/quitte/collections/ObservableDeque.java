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

import java.util.Deque;
import java.util.Iterator;

import com.osmerion.quitte.internal.collections.UnmodifiableObservableDeque;
import com.osmerion.quitte.internal.collections.WrappingObservableDeque;
import org.jspecify.annotations.Nullable;

/**
 * An observable deque with support for tracking changes to the deque's content.
 * 
 * @param <E>   the type of the deque's elements
 *
 * @since   0.1.0
 * 
 * @author  Leon Linhart
 */
public interface ObservableDeque<E extends @Nullable Object> extends Deque<E>, ObservableCollection<E> {

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
     * Attaches the given {@link DequeChangeListener change listener} to this deque.
     *
     * <p>If the given listener is already attached to this deque, this method does nothing and returns {@code false}.
     * </p>
     *
     * <p>While an {@code DequeChangeListener} is attached to a deque, it will be {@link DequeChangeListener#onChanged(ObservableDeque, DequeChangeListener.Change)}
     * notified} whenever the deque is updated.</p>
     *
     * <p>This deque stores a strong reference to the given listener until the listener is either removed explicitly by
     * calling {@link #removeChangeListener(DequeChangeListener)} or implicitly when this list discovers that the
     * listener has become {@link DequeChangeListener#isInvalid() invalid}. Generally, it is recommended to use an
     * instance of {@link WeakDequeChangeListener} when possible to avoid leaking instances.</p>
     *
     * @param listener  the listener to be attached to this deque
     *
     * @return  {@code true} if the listener was not previously attached to this deque and has been successfully
     *          attached, or {@code false} otherwise
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @see #removeChangeListener(DequeChangeListener)
     *
     * @since   0.8.0
     */
    boolean addChangeListener(DequeChangeListener<? super E> listener);

    /**
     * Detaches the given {@link DequeChangeListener change listener} from this deque.
     *
     * <p>If the given listener is not attached to this deque, this method does nothing and returns {@code false}.</p>
     *
     * @param listener  the listener to be detached from this deque
     *
     * @return  {@code true} if the listener was attached to and has been detached from this deque, or {@code false}
     *          otherwise
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @see #addChangeListener(DequeChangeListener)
     *
     * @since   0.8.0
     */
    boolean removeChangeListener(DequeChangeListener<? super E> listener);

    /**
     * {@inheritDoc}
     *
     * <p><b>Modifications to an observable deque using the returned iterator should be made with caution as they
     * produce {@link DequeChangeListener.Site#OPAQUE opaque changes}.
     * </b></p>
     */
    @Override
    Iterator<E> iterator();

    /**
     * {@inheritDoc}
     *
     * <p><b>Modifications to an observable deque using the returned iterator should be made with caution as they
     * produce {@link DequeChangeListener.Site#OPAQUE opaque changes}.</b></p>
     */
    @Override
    Iterator<E> descendingIterator();

    /**
     * {@inheritDoc}
     *
     * <p><b>This method should be used with caution as it produces {@link DequeChangeListener.Site#OPAQUE opaque changes}.</b></p>
     */
    @Override
    boolean remove(@Nullable Object o);

    /**
     * {@inheritDoc}
     *
     * <p><b>This method should be used with caution as it produces {@link DequeChangeListener.Site#OPAQUE opaque changes}.</b></p>
     */
    @Override
    boolean removeFirstOccurrence(Object o);

    /**
     * {@inheritDoc}
     *
     * <p><b>This method should be used with caution as it produces {@link DequeChangeListener.Site#OPAQUE opaque changes}.</b></p>
     */
    @Override
    boolean removeLastOccurrence(Object o);

}