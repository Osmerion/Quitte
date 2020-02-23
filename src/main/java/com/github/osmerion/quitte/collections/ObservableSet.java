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
package com.github.osmerion.quitte.collections;

import java.util.Set;
import com.github.osmerion.quitte.internal.collections.UnmodifiableObservableSet;
import com.github.osmerion.quitte.internal.collections.WrappingObservableSet;

/**
 * A observable set with support for tracking changes to the set's content.
 *
 * @param <E>   the type of elements in this set
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface ObservableSet<E> extends Set<E> {

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
     * Adds the given listener to this set.
     *
     * @param listener  the listener for listening to changes to this set
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @since   0.1.0
     */
    void addChangeListener(SetChangeListener<? super E> listener);

    /**
     * Removes the given listener from this set. If the given listener has not been added to this set before, nothing
     * happens.
     *
     * @param listener  the listener to remove
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @since   0.1.0
     */
    void removeChangeListener(SetChangeListener<? super E> listener);

}