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
package com.github.osmerion.quitte.expression;

import java.util.Objects;
import java.util.function.Function;

import com.github.osmerion.quitte.functional.*;
import com.github.osmerion.quitte.internal.binding.*;
import com.github.osmerion.quitte.value.*;

/**
 * A basic implementation for a specialized {@code float} expression.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class SimpleFloatExpression extends AbstractFloatExpression {

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param <S>           the type of the source value
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static <S> SimpleFloatExpression of(ObservableValue<S> observable, Function<S, Float> transform) {
        return new Transform(ex -> new FloatBinding.Generic<>(ex::onDependencyInvalidated, observable, it -> Objects.requireNonNull(transform.apply(it))));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static SimpleFloatExpression of(ObservableBoolValue observable, Bool2FloatFunction transform) {
        return new Transform(ex -> new Bool2FloatBinding(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static SimpleFloatExpression of(ObservableByteValue observable, Byte2FloatFunction transform) {
        return new Transform(ex -> new Byte2FloatBinding(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static SimpleFloatExpression of(ObservableShortValue observable, Short2FloatFunction transform) {
        return new Transform(ex -> new Short2FloatBinding(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static SimpleFloatExpression of(ObservableIntValue observable, Int2FloatFunction transform) {
        return new Transform(ex -> new Int2FloatBinding(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static SimpleFloatExpression of(ObservableLongValue observable, Long2FloatFunction transform) {
        return new Transform(ex -> new Long2FloatBinding(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static SimpleFloatExpression of(ObservableFloatValue observable, Float2FloatFunction transform) {
        return new Transform(ex -> new Float2FloatBinding(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static SimpleFloatExpression of(ObservableDoubleValue observable, Double2FloatFunction transform) {
        return new Transform(ex -> new Double2FloatBinding(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param <S>           the type of the source value
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static <S> SimpleFloatExpression of(ObservableObjectValue<S> observable, Object2FloatFunction<S> transform) {
        return new Transform(ex -> new Object2FloatBinding<>(ex::onDependencyInvalidated, observable, transform));
    }

    protected float value;

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    protected final float getImpl() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    protected final void setImpl(float value) {
        this.value = value;
    }

    /** A simple expression transforming a single value using the internal binding API. */
    private static final class Transform extends SimpleFloatExpression {

        private final transient FloatBinding binding;

        private Transform(Function<SimpleFloatExpression, FloatBinding> factory) {
            this.binding = factory.apply(this);
            this.value = this.recomputeValue();
        }

        @Override
        protected final float recomputeValue() {
            return this.binding.get();
        }

    }

}