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

import javax.annotation.Nullable;

import com.github.osmerion.quitte.functional.*;
import com.github.osmerion.quitte.internal.binding.*;
import com.github.osmerion.quitte.property.*;
import com.github.osmerion.quitte.value.*;

/**
 * A specialized lazy {@code float} expression.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class LazyFloatExpression extends AbstractFloatExpression implements LazyValue {

    /**
     * Returns a new lazy expression which applies a given transformation to a given observable.
     *
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new lazy expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static LazyFloatExpression of(ObservableBoolValue observable, Bool2FloatFunction transform) {
        return new Transform(ex -> new Bool2FloatBinding(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new lazy expression which applies a given transformation to a given observable.
     *
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new lazy expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static LazyFloatExpression of(ObservableByteValue observable, Byte2FloatFunction transform) {
        return new Transform(ex -> new Byte2FloatBinding(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new lazy expression which applies a given transformation to a given observable.
     *
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new lazy expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static LazyFloatExpression of(ObservableShortValue observable, Short2FloatFunction transform) {
        return new Transform(ex -> new Short2FloatBinding(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new lazy expression which applies a given transformation to a given observable.
     *
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new lazy expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static LazyFloatExpression of(ObservableIntValue observable, Int2FloatFunction transform) {
        return new Transform(ex -> new Int2FloatBinding(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new lazy expression which applies a given transformation to a given observable.
     *
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new lazy expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static LazyFloatExpression of(ObservableLongValue observable, Long2FloatFunction transform) {
        return new Transform(ex -> new Long2FloatBinding(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new lazy expression which applies a given transformation to a given observable.
     *
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new lazy expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static LazyFloatExpression of(ObservableFloatValue observable, Float2FloatFunction transform) {
        return new Transform(ex -> new Float2FloatBinding(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new lazy expression which applies a given transformation to a given observable.
     *
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new lazy expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static LazyFloatExpression of(ObservableDoubleValue observable, Double2FloatFunction transform) {
        return new Transform(ex -> new Double2FloatBinding(ex::onDependencyInvalidated, observable, transform));
    }

    /**
     * Returns a new lazy expression which applies a given transformation to a given observable.
     *
     * @param <S>           the type of the source value
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new lazy expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static <S> LazyFloatExpression of(ObservableObjectValue<S> observable, Object2FloatFunction<S> transform) {
        return new Transform(ex -> new Object2FloatBinding<>(ex::onDependencyInvalidated, observable, transform));
    }

    private final SimpleObjectProperty<State> state = new SimpleObjectProperty<>(State.UNINITIALIZED) {

        @Override
        public void onChanged(@Nullable State prevValue, @Nullable State value) {
            //noinspection ConstantConditions
            if (!value.isValid()) LazyFloatExpression.this.invalidate();
        }

    };

    @Nullable
    private FloatSupplier provider = this::recomputeValue;

    protected float value;

    /**
     * Returns a read-only view of this expression's state.
     *
     * @return  a read-only view of this expression's state
     *
     * @implNote    The view is not cached since calling this method is expected to be a rare operation.
     *
     * @since   0.1.0
     */
    public final ReadableObjectProperty<State> stateProperty() {
        return this.state.asReadOnlyProperty();
    }

    /**
     * Returns the current state of this expression.
     *
     * @return  the current state of this expression
     *
     * @since   0.1.0
     */
    public final State getState() {
        //noinspection ConstantConditions
        return this.state.get();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final float get() {
        if (this.state.get() != State.VALID) {
            var provider = Objects.requireNonNull(this.provider); 
            if (!this.updateValue(provider.get())) {
                if (this.state.get() != State.UNINITIALIZED) {
                    this.state.set(State.VALID);
                } else {
                    this.state.set(State.INITIALIZED);
                }
            }

            this.provider = null;
        }

        return this.value;
    }

    @Override
    final float getImpl() {
        return this.value;
    }

    @Override
    final void setImpl(float value) {
        this.value = value;
    }

    @Override
    final void onDependencyInvalidated() {
        this.provider = this::recomputeValue;

        //noinspection ConstantConditions
        if (this.state.get().isValid()) this.state.set(State.INVALID);
    }

    @Override
    final void onChangedInternal(float oldValue, float newValue) {
        if (this.state.get() != State.UNINITIALIZED) {
            this.state.set(State.VALID);
        } else {
            this.state.set(State.INITIALIZED);
        }
    }

    /** A simple expression transforming a single value using the internal binding API. */
    private static final class Transform extends LazyFloatExpression {

        private final transient FloatBinding binding;

        private Transform(Function<LazyFloatExpression, FloatBinding> factory) {
            this.binding = factory.apply(this);
        }

        @Override
        protected final float recomputeValue() {
            return this.binding.get();
        }

    }

}