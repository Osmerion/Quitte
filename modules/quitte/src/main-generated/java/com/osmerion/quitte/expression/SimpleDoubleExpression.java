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
package com.osmerion.quitte.expression;

import java.util.Objects;
import java.util.function.Function;

import com.osmerion.quitte.*;
import com.osmerion.quitte.functional.*;
import com.osmerion.quitte.internal.binding.*;
import com.osmerion.quitte.value.*;
import com.osmerion.quitte.value.change.*;

/**
 * A basic implementation for a specialized {@code double} expression.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class SimpleDoubleExpression extends AbstractDoubleExpression {

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
    public static SimpleDoubleExpression of(ObservableBoolValue observable, Bool2DoubleFunction transform) {
        return new Transform(ex -> new Bool2DoubleBinding(ex::doInvalidate, observable, transform));
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
    public static SimpleDoubleExpression of(ObservableByteValue observable, Byte2DoubleFunction transform) {
        return new Transform(ex -> new Byte2DoubleBinding(ex::doInvalidate, observable, transform));
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
    public static SimpleDoubleExpression of(ObservableShortValue observable, Short2DoubleFunction transform) {
        return new Transform(ex -> new Short2DoubleBinding(ex::doInvalidate, observable, transform));
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
    public static SimpleDoubleExpression of(ObservableIntValue observable, Int2DoubleFunction transform) {
        return new Transform(ex -> new Int2DoubleBinding(ex::doInvalidate, observable, transform));
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
    public static SimpleDoubleExpression of(ObservableLongValue observable, Long2DoubleFunction transform) {
        return new Transform(ex -> new Long2DoubleBinding(ex::doInvalidate, observable, transform));
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
    public static SimpleDoubleExpression of(ObservableFloatValue observable, Float2DoubleFunction transform) {
        return new Transform(ex -> new Float2DoubleBinding(ex::doInvalidate, observable, transform));
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
    public static SimpleDoubleExpression of(ObservableDoubleValue observable, Double2DoubleFunction transform) {
        return new Transform(ex -> new Double2DoubleBinding(ex::doInvalidate, observable, transform));
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
    public static <S> SimpleDoubleExpression of(ObservableObjectValue<S> observable, Object2DoubleFunction<S> transform) {
        return new Transform(ex -> new Object2DoubleBinding<>(ex::doInvalidate, observable, transform));
    }

    /**
     * Returns a new simple expression which aliases a child property of an observable.
     *
     * <p>The parent observable must never evaluate to {@code null}.</p>
     *
     * @param observable    the parent observable
     * @param selector      the function that selects the child property
     * @param <S>           the parent type
     * 
     * @return  a new simple expression which aliases a child property of an observable
     *
     * @since   0.1.0
     */
    public static <S> SimpleDoubleExpression ofNested(ObservableObjectValue<S> observable, Function<S, ObservableDoubleValue> selector) {
        return new SimpleDoubleExpression() {

            final InvalidationListener nestedPropertyListener = ignored -> this.doInvalidate();

            {
                observable.addListener(ignored -> this.doInvalidate());

                ObjectChangeListener<S> parentChangeListener = (ignored, oldValue, newValue) -> {
                    if (oldValue != null) {
                        var nestedProperty = selector.apply(oldValue);
                        nestedProperty.removeListener(this.nestedPropertyListener);
                    }

                    var nestedProperty = selector.apply(Objects.requireNonNull(newValue));
                    nestedProperty.addListener(this.nestedPropertyListener);
                };
                observable.addListener(parentChangeListener);
                parentChangeListener.onChanged(observable, null, observable.get());
            }


            @Override
            protected double recomputeValue() {
                var parent = observable.get();
                return selector.apply(Objects.requireNonNull(parent)).get();
            }

        };
    }

    protected double value;

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    protected final double getImpl() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    protected final void setImpl(double value) {
        this.value = value;
    }

    /** A simple expression transforming a single value using the internal binding API. */
    private static final class Transform extends SimpleDoubleExpression {

        private final transient DoubleBinding binding;

        private Transform(Function<SimpleDoubleExpression, DoubleBinding> factory) {
            this.binding = factory.apply(this);
            this.value = this.recomputeValue();
        }

        @Override
        protected final double recomputeValue() {
            return this.binding.get();
        }

    }

}