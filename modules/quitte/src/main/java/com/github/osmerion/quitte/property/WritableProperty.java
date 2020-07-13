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
package com.github.osmerion.quitte.property;

import java.util.function.Function;
import com.github.osmerion.quitte.value.ObservableValue;
import com.github.osmerion.quitte.value.WritableValue;

/**
 * A basic writable property.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface WritableProperty<T> extends ReadableProperty<T>, WritableValue<T> {

    /**
     * Binds this property to the given observable value.
     *
     * <p>This method creates a unidirectional binding between this property and the given observable. This binding can
     * be destroyed again by calling {@link #unbind()}. However, to avoid memory leaks, the given observable will not
     * hold a strong reference to this property.</p>
     *
     * <p>While a property is bound, its value will depend on the value of the observable it is bound to. A property
     * that is bound by calling this method, is not {@link #isWritable() writable}.</p>
     *
     * <p>If the underlying property is set to {@code null} and this property does not support {@code null}, a
     * {@link NullPointerException} is thrown when this property's value is updated to reflect the changes.</p>
     *
     * @param observable    the observable to bind this property to
     *
     * @throws  IllegalStateException   if this property is already bound
     *
     * @since   0.1.0
     */
    void bindToBoxing(ObservableValue<T> observable);

    /**
     * Binds this property to the given observable value.
     *
     * <p>This method creates a unidirectional binding between this property and the given observable. This binding can
     * be destroyed again by calling {@link #unbind()}. However, to avoid memory leaks, the given observable will not
     * hold a strong reference to this property.</p>
     *
     * <p>While a property is bound, its value will depend on the value of the observable it is bound to. A property
     * that is bound by calling this method, is not {@link #isWritable() writable}.</p>
     *
     * <p>If the given transform function returns {@code null} and this property does not support {@code null}, a
     * {@link NullPointerException} is thrown when this properties value is updated to reflect the changes.</p>
     *
     * @param <S>           the type of the value of the given observable
     * @param observable    the observable to bind this property to
     * @param transform     the transform function to be applied to the value before updating this property's value
     *
     * @since   0.1.0
     */
    <S> void bindToBoxing(ObservableValue<S> observable, Function<S, T> transform);

    /**
     * Unbinds this property.
     *
     * <p>If this property {@link #isBound() is unbound}, this method does nothing.</p>
     *
     * @since   0.1.0
     */
    void unbind();

}