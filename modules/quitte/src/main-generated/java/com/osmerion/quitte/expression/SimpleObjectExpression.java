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
package com.osmerion.quitte.expression;

import java.util.Objects;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.osmerion.quitte.*;
import com.osmerion.quitte.functional.*;
import com.osmerion.quitte.internal.binding.*;
import com.osmerion.quitte.value.*;
import com.osmerion.quitte.value.change.*;

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
     * @param <T>           the type of the target value
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static <T> SimpleObjectExpression<T> of(ObservableBoolValue observable, BoolToObjectFunction<T> transform) {
        return new Transform<>(ex -> new BoolToObjectBinding<>(ex::doInvalidate, observable, transform));
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
    public static <T> SimpleObjectExpression<T> of(ObservableByteValue observable, ByteToObjectFunction<T> transform) {
        return new Transform<>(ex -> new ByteToObjectBinding<>(ex::doInvalidate, observable, transform));
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
    public static <T> SimpleObjectExpression<T> of(ObservableShortValue observable, ShortToObjectFunction<T> transform) {
        return new Transform<>(ex -> new ShortToObjectBinding<>(ex::doInvalidate, observable, transform));
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
    public static <T> SimpleObjectExpression<T> of(ObservableIntValue observable, IntToObjectFunction<T> transform) {
        return new Transform<>(ex -> new IntToObjectBinding<>(ex::doInvalidate, observable, transform));
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
    public static <T> SimpleObjectExpression<T> of(ObservableLongValue observable, LongToObjectFunction<T> transform) {
        return new Transform<>(ex -> new LongToObjectBinding<>(ex::doInvalidate, observable, transform));
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
    public static <T> SimpleObjectExpression<T> of(ObservableFloatValue observable, FloatToObjectFunction<T> transform) {
        return new Transform<>(ex -> new FloatToObjectBinding<>(ex::doInvalidate, observable, transform));
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
    public static <T> SimpleObjectExpression<T> of(ObservableDoubleValue observable, DoubleToObjectFunction<T> transform) {
        return new Transform<>(ex -> new DoubleToObjectBinding<>(ex::doInvalidate, observable, transform));
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
    public static <S, T> SimpleObjectExpression<T> of(ObservableObjectValue<S> observable, ObjectToObjectFunction<S, T> transform) {
        return new Transform<>(ex -> new ObjectToObjectBinding<>(ex::doInvalidate, observable, transform));
    }

    /**
     * Returns a new simple expression which aliases a child property of an observable.
     *
     * <p>The parent observable must never evaluate to {@code null}.</p>
     *
     * @param observable    the parent observable
     * @param selector      the function that selects the child property
     * @param <S>           the parent type
     * @param <T>           the child type
    *
     * @return  a new simple expression which aliases a child property of an observable
     *
     * @since   0.1.0
     */
    public static <S, T> SimpleObjectExpression<T> ofNested(ObservableObjectValue<S> observable, Function<S, ObservableObjectValue<T>> selector) {
        return new SimpleObjectExpression<>() {

            final InvalidationListener nestedPropertyListener = ignored -> this.doInvalidate();

            {
                observable.addInvalidationListener(ignored -> this.doInvalidate());

                ObjectChangeListener<S> parentChangeListener = (ignored, oldValue, newValue) -> {
                    if (oldValue != null) {
                        var nestedProperty = selector.apply(oldValue);
                        nestedProperty.removeInvalidationListener(this.nestedPropertyListener);
                    }

                    var nestedProperty = selector.apply(Objects.requireNonNull(newValue));
                    nestedProperty.addInvalidationListener(this.nestedPropertyListener);
                };
                observable.addListener(parentChangeListener);
                parentChangeListener.onChanged(observable, null, observable.get());
            }

            @Nullable
            @Override
            protected T recomputeValue() {
                var parent = observable.get();
                return selector.apply(Objects.requireNonNull(parent)).get();
            }

        };
    }

    /**
     * Returns a new simple expression which aliases a child property of an observable.
     *
     * <p>Returns {@code null} if the parent observable evaluates to {@code null}.</p>
     *
     * @param observable    the parent observable
     * @param selector      the function that selects the child property
     * @param <S>           the parent type
     * @param <T>           the child type
    *
     * @return  a new simple expression which aliases a child property of an observable
     *
     * @since   0.1.0
     */
    public static <S, T> SimpleObjectExpression<T> ofNestedOrNull(ObservableObjectValue<S> observable, Function<S, ObservableObjectValue<T>> selector) {
        return new SimpleObjectExpression<>() {

            final InvalidationListener nestedPropertyListener = ignored -> this.doInvalidate();

            {
                observable.addInvalidationListener(ignored -> this.doInvalidate());

                ObjectChangeListener<S> parentChangeListener = (ignored, oldValue, newValue) -> {
                    if (oldValue != null) {
                        var nestedProperty = selector.apply(oldValue);
                        nestedProperty.removeInvalidationListener(this.nestedPropertyListener);
                    }

                    if (newValue != null) {
                        var nestedProperty = selector.apply(newValue);
                        nestedProperty.addInvalidationListener(this.nestedPropertyListener);
                    }
                };
                observable.addListener(parentChangeListener);
                parentChangeListener.onChanged(observable, null, observable.get());
            }

            @Nullable
            @Override
            protected T recomputeValue() {
                var parent = observable.get();
                return (parent != null) ? selector.apply(parent).get() : null;
            }

        };
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