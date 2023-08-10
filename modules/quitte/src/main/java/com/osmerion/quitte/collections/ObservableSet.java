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

import java.util.Set;

import com.osmerion.quitte.internal.collections.UnmodifiableObservableSet;
import com.osmerion.quitte.internal.collections.WrappingObservableSet;
import org.jspecify.annotations.Nullable;

/**
 * An observable set with support for tracking changes to the set's content.
 *
 * @param <E>   the type of elements in this set
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface ObservableSet<E extends @Nullable Object> extends Set<E>, ObservableCollection<E> {

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
     * Attaches the given {@link SetChangeListener change listener} to this set.
     *
     * <p>If the given listener is already attached to this set, this method does nothing and returns {@code false}.</p>
     *
     * <p>While an {@code SetChangeListener} is attached to a set, it will be {@link SetChangeListener#onChanged(ObservableSet, SetChangeListener.Change)}
     * notified} whenever the set is updated.</p>
     *
     * <p>This set stores a strong reference to the given listener until the listener is either removed explicitly by
     * calling {@link #removeChangeListener(SetChangeListener)} or implicitly when this set discovers that the listener
     * has become {@link SetChangeListener#isInvalid() invalid}. Generally, it is recommended to use an instance of
     * {@link WeakSetChangeListener} when possible to avoid leaking instances.</p>
     *
     * @param listener  the listener to be attached to this set
     *
     * @return  {@code true} if the listener was not previously attached to this set and has been successfully attached,
     *          or {@code false} otherwise
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @see #removeChangeListener(SetChangeListener)
     *
     * @since   0.8.0
     */
    boolean addChangeListener(SetChangeListener<? super E> listener);

    /**
     * Detaches the given {@link SetChangeListener change listener} from this set.
     *
     * <p>If the given listener is not attached to this set, this method does nothing and returns {@code false}.</p>
     *
     * @param listener  the listener to be detached from this set
     *
     * @return  {@code true} if the listener was attached to and has been detached from this set, or {@code false}
     *          otherwise
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @see #addChangeListener(SetChangeListener)
     *
     * @since   0.8.0
     */
    boolean removeChangeListener(SetChangeListener<? super E> listener);

}