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
package com.osmerion.quitte.value;

import javax.annotation.Nullable;

import com.osmerion.quitte.Observable;
import com.osmerion.quitte.value.change.ChangeListener;

/**
 * An {@code ObservableValue} object wraps a single value state and allows {@link #addBoxedChangeListener(ChangeListener)
 * listening} for changes of the wrapped value.
 *
 * <p><b>Specialized versions of this interface should be used whenever possible.</b></p>
 *
 * @param <T>   the type of the value
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface ObservableValue<T> extends Observable {

    /**
     * Returns the value represented by this object.
     *
     * <p><b>Specialized versions of this method should be used whenever possible.</b></p>
     *
     * @return  the current value
     *
     * @since   0.1.0
     */
    @Nullable
    T getValue();

    /**
     * Attaches the given {@link ChangeListener} to this observable.
     *
     * <p><b>Specialized versions of this method should be used whenever possible.</b></p>
     *
     * <p>If the given listener is already attached to this observable, this method does nothing and returns
     * {@code false}.</p>
     *
     * <p>While an {@code ChangeListener} is attached to an observable, it will be {@link ChangeListener#onChanged(ObservableValue, Object, Object)
     * notified} whenever the value of the observable is changed.</p>
     *
     * <p>This observable stores a strong reference to the given listener until the listener is either removed
     * explicitly by calling {@link #removeBoxedChangeListener(ChangeListener)} or implicitly when this observable
     * discovers that the listener has become {@link ChangeListener#isInvalid() invalid}. Generally, it is recommended
     * to use an instance of {@link com.osmerion.quitte.value.change.WeakChangeListener WeakChangeListener} when
     * possible to avoid leaking instances.</p>
     *
     * @param listener  the listener to be attached to this observable
     *
     * @return  {@code true} if the listener was not previously attached to this observable and has been successfully
     *          attached, or {@code false} otherwise
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @see #removeBoxedChangeListener(ChangeListener)
     *
     * @since   0.1.0
     */
    boolean addBoxedChangeListener(ChangeListener<T> listener);

    /**
     * Detaches the given {@link ChangeListener} from this observable.
     *
     * <p>If the given listener is not attached to this observable, this method does nothing and returns {@code false}.
     * </p>
     *
     * @param listener  the listener to be detached from this observable value
     *
     * @return  {@code true} if the listener was attached to and has been detached from this observable, or
     *          {@code false} otherwise
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @see #addBoxedChangeListener(ChangeListener)
     *
     * @since   0.1.0
     */
    boolean removeBoxedChangeListener(ChangeListener<T> listener);

}