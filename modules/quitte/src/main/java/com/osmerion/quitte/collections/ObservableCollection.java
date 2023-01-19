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

import com.osmerion.quitte.Observable;

/**
 * An observable collection.
 *
 * @param <C>   the type of change produced by this collection
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface ObservableCollection<C> extends Observable {

    /**
     * Attaches the given {@link CollectionChangeListener change listener} to this map.
     *
     * <p>If the given listener is already attached to this map, this method does nothing and returns {@code false}.</p>
     *
     * <p>While an {@code MapChangeListener} is attached to a map, it will be {@link CollectionChangeListener#onChanged(Object)}
     * notified} whenever the map is updated.</p>
     *
     * <p>This map stores a strong reference to the given listener until the listener is either removed explicitly by
     * calling {@link #removeChangeListener(CollectionChangeListener)} or implicitly when this map discovers that the
     * listener has become {@link CollectionChangeListener#isInvalid() invalid}. Generally, it is recommended to use an
     * instance of {@link WeakCollectionChangeListener} when possible to avoid leaking instances.</p>
     *
     * @param listener  the listener to be attached to this map
     *
     * @return  {@code true} if the listener was not previously attached to this map and has been successfully attached,
     *          or {@code false} otherwise
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @see #removeChangeListener(CollectionChangeListener)
     *
     * @since   0.1.0
     */
    boolean addChangeListener(CollectionChangeListener<? super C> listener);

    /**
     * Detaches the given {@link CollectionChangeListener change listener} from this map.
     *
     * <p>If the given listener is not attached to this map, this method does nothing and returns {@code false}.</p>
     *
     * @param listener  the listener to be detached from this map
     *
     * @return  {@code true} if the listener was attached to and has been detached from this map, or {@code false}
     *          otherwise
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @see #addChangeListener(CollectionChangeListener)
     *
     * @since   0.1.0
     */
    boolean removeChangeListener(CollectionChangeListener<? super C> listener);

}