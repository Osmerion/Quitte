/*
 * Copyright (c) 2018-2021 Leon Linhart,
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
import com.osmerion.quitte.property.*;
import com.osmerion.quitte.value.*;
import com.osmerion.quitte.value.change.*;

/**
 * A specialized lazy {@code byte} expression.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class LazyByteExpression extends AbstractByteExpression implements LazyValue {

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
    public static LazyByteExpression of(ObservableBoolValue observable, BoolToByteFunction transform) {
        return new Transform(ex -> new BoolToByteBinding(ex::doInvalidate, observable, transform));
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
    public static LazyByteExpression of(ObservableByteValue observable, ByteToByteFunction transform) {
        return new Transform(ex -> new ByteToByteBinding(ex::doInvalidate, observable, transform));
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
    public static LazyByteExpression of(ObservableShortValue observable, ShortToByteFunction transform) {
        return new Transform(ex -> new ShortToByteBinding(ex::doInvalidate, observable, transform));
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
    public static LazyByteExpression of(ObservableIntValue observable, IntToByteFunction transform) {
        return new Transform(ex -> new IntToByteBinding(ex::doInvalidate, observable, transform));
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
    public static LazyByteExpression of(ObservableLongValue observable, LongToByteFunction transform) {
        return new Transform(ex -> new LongToByteBinding(ex::doInvalidate, observable, transform));
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
    public static LazyByteExpression of(ObservableFloatValue observable, FloatToByteFunction transform) {
        return new Transform(ex -> new FloatToByteBinding(ex::doInvalidate, observable, transform));
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
    public static LazyByteExpression of(ObservableDoubleValue observable, DoubleToByteFunction transform) {
        return new Transform(ex -> new DoubleToByteBinding(ex::doInvalidate, observable, transform));
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
    public static <S> LazyByteExpression of(ObservableObjectValue<S> observable, ObjectToByteFunction<S> transform) {
        return new Transform(ex -> new ObjectToByteBinding<>(ex::doInvalidate, observable, transform));
    }

    /**
     * Returns a new lazy expression which aliases a child property of an observable.
     *
     * <p>The parent observable must never evaluate to {@code null}.</p>
     *
     * @param observable    the parent observable
     * @param selector      the function that selects the child property
     * @param <S>           the parent type
     * 
     * @return  a new lazy expression which aliases a child property of an observable
     *
     * @since   0.1.0
     */
    public static <S> LazyByteExpression ofNested(ObservableObjectValue<S> observable, Function<S, ObservableByteValue> selector) {
        return new LazyByteExpression() {

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
            protected byte recomputeValue() {
                var parent = observable.get();
                return selector.apply(Objects.requireNonNull(parent)).get();
            }

        };
    }

    private final SimpleObjectProperty<State> state = new SimpleObjectProperty<>(State.UNINITIALIZED) {

        @Override
        public void onChanged(@Nullable State prevValue, @Nullable State value) {
            //noinspection ConstantConditions
            if (!value.isValid()) LazyByteExpression.this.notifyInvalidationListeners();
        }

    };

    @Nullable
    private ByteSupplier provider = this::recomputeValue;

    protected byte value;

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
    public final byte get() {
        //noinspection ConstantConditions
        if (!this.state.get().isValid()) { 
            var provider = Objects.requireNonNull(this.provider);
            this.updateValue(provider.get(), !this.state.get().isValid());

            this.provider = null;
        }

        return this.value;
    }

    @Override
    final byte getImpl() {
        return this.value;
    }

    @Override
    final void setImpl(byte value) {
        this.value = value;
    }

    @Override
    final void doInvalidate() {
        this.provider = this::recomputeValue;

        //noinspection ConstantConditions
        if (this.state.get().isValid()) this.state.set(State.INVALID);
    }

    @Override
    final boolean onChangedInternal(byte oldValue, byte newValue) {
        if (this.state.get() != State.UNINITIALIZED) {
            this.state.set(State.VALID);
            return true;
        } else {
            this.state.set(State.INITIALIZED);
            return false;
        }
    }

    /** A simple expression transforming a single value using the internal binding API. */
    private static final class Transform extends LazyByteExpression {

        private final transient ByteBinding binding;

        private Transform(Function<LazyByteExpression, ByteBinding> factory) {
            this.binding = factory.apply(this);
        }

        @Override
        protected final byte recomputeValue() {
            return this.binding.get();
        }

    }

}