/*
 * Copyright (c) 2018-2020 Leon Linhart,
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
package com.github.osmerion.quitte.property;

import com.github.osmerion.quitte.functional.*;
import com.github.osmerion.quitte.value.*;

/**
 * A specialized writable {@code double} property.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface WritableDoubleProperty extends WritableProperty<Double>, ReadableDoubleProperty, WritableDoubleValue {

    /**
     * Binds this property to the given observable value.
     *
     * <p>While a property is bound, its value will be equal to the observable value. Any attempt to set the value of a
     * bound property explicitly will fail. A bound property may be unbound by calling {@link #unbind()}.</p>
     *
     * @param observable    the observable to bind this property to
     *
     * @since   0.1.0
     */
    void bindTo(ObservableDoubleValue observable);

    /**
     * Binds this property to the given observable value.
     *
     * <p>While a property is bound, its value will be equal to the observable value. Any attempt to set the value of a
     * bound property explicitly will fail. A bound property may be unbound by calling {@link #unbind()}.</p>
     *
     * @param observable    the observable to bind this property to
     * @param transform     the transform function to be applied to the value before updating this property
     *
     * @since   0.1.0
     */
    void bindTo(ObservableBoolValue observable, Bool2DoubleFunction transform);

    /**
     * Binds this property to the given observable value.
     *
     * <p>While a property is bound, its value will be equal to the observable value. Any attempt to set the value of a
     * bound property explicitly will fail. A bound property may be unbound by calling {@link #unbind()}.</p>
     *
     * @param observable    the observable to bind this property to
     * @param transform     the transform function to be applied to the value before updating this property
     *
     * @since   0.1.0
     */
    void bindTo(ObservableByteValue observable, Byte2DoubleFunction transform);

    /**
     * Binds this property to the given observable value.
     *
     * <p>While a property is bound, its value will be equal to the observable value. Any attempt to set the value of a
     * bound property explicitly will fail. A bound property may be unbound by calling {@link #unbind()}.</p>
     *
     * @param observable    the observable to bind this property to
     * @param transform     the transform function to be applied to the value before updating this property
     *
     * @since   0.1.0
     */
    void bindTo(ObservableShortValue observable, Short2DoubleFunction transform);

    /**
     * Binds this property to the given observable value.
     *
     * <p>While a property is bound, its value will be equal to the observable value. Any attempt to set the value of a
     * bound property explicitly will fail. A bound property may be unbound by calling {@link #unbind()}.</p>
     *
     * @param observable    the observable to bind this property to
     * @param transform     the transform function to be applied to the value before updating this property
     *
     * @since   0.1.0
     */
    void bindTo(ObservableIntValue observable, Int2DoubleFunction transform);

    /**
     * Binds this property to the given observable value.
     *
     * <p>While a property is bound, its value will be equal to the observable value. Any attempt to set the value of a
     * bound property explicitly will fail. A bound property may be unbound by calling {@link #unbind()}.</p>
     *
     * @param observable    the observable to bind this property to
     * @param transform     the transform function to be applied to the value before updating this property
     *
     * @since   0.1.0
     */
    void bindTo(ObservableLongValue observable, Long2DoubleFunction transform);

    /**
     * Binds this property to the given observable value.
     *
     * <p>While a property is bound, its value will be equal to the observable value. Any attempt to set the value of a
     * bound property explicitly will fail. A bound property may be unbound by calling {@link #unbind()}.</p>
     *
     * @param observable    the observable to bind this property to
     * @param transform     the transform function to be applied to the value before updating this property
     *
     * @since   0.1.0
     */
    void bindTo(ObservableFloatValue observable, Float2DoubleFunction transform);

    /**
     * Binds this property to the given observable value.
     *
     * <p>While a property is bound, its value will be equal to the observable value. Any attempt to set the value of a
     * bound property explicitly will fail. A bound property may be unbound by calling {@link #unbind()}.</p>
     *
     * @param observable    the observable to bind this property to
     * @param transform     the transform function to be applied to the value before updating this property
     *
     * @since   0.1.0
     */
    void bindTo(ObservableDoubleValue observable, Double2DoubleFunction transform);

    /**
     * Binds this property to the given observable value.
     *
     * <p>While a property is bound, its value will be equal to the observable value. Any attempt to set the value of a
     * bound property explicitly will fail. A bound property may be unbound by calling {@link #unbind()}.</p>
     *
     * @param <S>           the type of the value of the given observable
     * @param observable    the observable to bind this property to
     * @param transform     the transform function to be applied to the value before updating this property
     *
     * @since   0.1.0
     */
    <S> void bindTo(ObservableObjectValue<S> observable, Object2DoubleFunction<S> transform);

}