/*
 * Copyright (c) 2018-2022 Leon Linhart,
 * All rights reserved.
 * MACHINE GENERATED FILE, DO NOT EDIT
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

import com.osmerion.quitte.internal.wrappers.*;
import com.osmerion.quitte.value.change.*;

import static java.util.Objects.*;

/**
 * A specialized observable {@code long} value.
 *
 * @see ObservableValue
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface ObservableLongValue extends ObservableValue<Long> {

    /**
     * Returns an observable read-only view of the given {@code value}.
     *
     * @param value the value to wrap
     *
     * @return  an observable view of the given {@code value}
     *
     * @since   0.1.0
     */
    static ObservableLongValue wrap(long value) {
        return new ReadOnlyLongWrapper(value);
    }

    /**
     * Returns the value represented by this object.
     *
     * @return  the current value
     *
     * @since   0.1.0
     */
    long get();

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    default Long getValue() {
        return this.get();
    }

    /**
     * Attaches the given {@link LongChangeListener} to this observable.
     *
     * <p>While an {@code LongChangeListener} is attached to an observable, it will be {@link LongChangeListener#onChanged(ObservableLongValue, long, long)
     * notified} whenever the value of the observable is changed.</p>
     *
     * <p>This observable stores a strong reference to the given listener until the listener is either removed
     * explicitly by calling {@link #removeListener(LongChangeListener)} or implicitly when this
     * observable discovers that the listener has become {@link LongChangeListener#isInvalid() invalid}.
     * Generally, it is recommended to use an instance of {@link com.osmerion.quitte.value.change.WeakLongChangeListener WeakChangeListener}
     * when possible to avoid leaking instances.</p>
     *
     * @param listener  the listener to be attached to this observable value
     *
     * @return  {@code true} if the listener was not previously attached to this observable and has been successfully
     *          attached, or {@code false} otherwise
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @see #removeListener(LongChangeListener)
     *
     * @since   0.1.0
     */
    boolean addListener(LongChangeListener listener);

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    default boolean addBoxedListener(ChangeListener<Long> listener) {
        return this.addListener(LongChangeListener.wrap(requireNonNull(listener)));
    }

    /**
     * Detaches the given {@link LongChangeListener} from this observable.
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
     * @see #addListener(LongChangeListener)
     *
     * @since   0.1.0
     */
    boolean removeListener(LongChangeListener listener);

}