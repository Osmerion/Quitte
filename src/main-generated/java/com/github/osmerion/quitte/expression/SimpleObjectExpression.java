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

import java.util.function.Function;

import javax.annotation.Nullable;

import com.github.osmerion.quitte.functional.*;
import com.github.osmerion.quitte.internal.binding.*;
import com.github.osmerion.quitte.value.*;

/**
 * A basic implementation for a generic expression.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class SimpleObjectExpression<T> extends AbstractObjectExpression<T> {

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param <S>           the type of the source value
     * @param <T>           the type of the target value
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static <S, T> SimpleObjectExpression<T> of(ObservableValue<S> observable, Function<S, T> transform) {
        return new Transform<>(ex -> new ObjectBinding.Generic<>(ex::onDependencyInvalidated, observable, transform::apply));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param <T>           the type of the target value
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static <T> SimpleObjectExpression<T> of(ObservableBoolValue observable, Bool2ObjectFunction<T> transform) {
        return new Transform<>(ex -> new Bool2ObjectBinding<>(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param <T>           the type of the target value
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static <T> SimpleObjectExpression<T> of(ObservableByteValue observable, Byte2ObjectFunction<T> transform) {
        return new Transform<>(ex -> new Byte2ObjectBinding<>(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param <T>           the type of the target value
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static <T> SimpleObjectExpression<T> of(ObservableShortValue observable, Short2ObjectFunction<T> transform) {
        return new Transform<>(ex -> new Short2ObjectBinding<>(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param <T>           the type of the target value
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static <T> SimpleObjectExpression<T> of(ObservableIntValue observable, Int2ObjectFunction<T> transform) {
        return new Transform<>(ex -> new Int2ObjectBinding<>(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param <T>           the type of the target value
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static <T> SimpleObjectExpression<T> of(ObservableLongValue observable, Long2ObjectFunction<T> transform) {
        return new Transform<>(ex -> new Long2ObjectBinding<>(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param <T>           the type of the target value
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static <T> SimpleObjectExpression<T> of(ObservableFloatValue observable, Float2ObjectFunction<T> transform) {
        return new Transform<>(ex -> new Float2ObjectBinding<>(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param <T>           the type of the target value
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static <T> SimpleObjectExpression<T> of(ObservableDoubleValue observable, Double2ObjectFunction<T> transform) {
        return new Transform<>(ex -> new Double2ObjectBinding<>(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new simple expression which applies a given transformation to a given observable.
     *
     * @param <S>           the type of the source value
     * @param <T>           the type of the target value
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static <S, T> SimpleObjectExpression<T> of(ObservableObjectValue<S> observable, Object2ObjectFunction<S, T> transform) {
        return new Transform<>(ex -> new Object2ObjectBinding<>(ex::onDependencyInvalidated, observable, transform));
    }

    @Nullable
    protected T value;

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    @Nullable
    protected final T getImpl() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    protected final void setImpl(@Nullable T value) {
        this.value = value;
    }

    /** A simple expression transforming a single value using the internal binding API. */
    private static final class Transform<T> extends SimpleObjectExpression<T> {

        private final transient ObjectBinding<T> binding;

        private Transform(Function<SimpleObjectExpression<T>, ObjectBinding<T>> factory) {
            this.binding = factory.apply(this);
            this.value = this.recomputeValue();
        }

        @Override
        @Nullable
        protected final T recomputeValue() {
            return this.binding.get();
        }

    }

}