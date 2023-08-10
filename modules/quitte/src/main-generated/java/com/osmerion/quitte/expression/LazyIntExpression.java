/*
 * Copyright (c) 2018-2023 Leon Linhart,
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
import com.osmerion.quitte.property.*;
import com.osmerion.quitte.value.*;
import com.osmerion.quitte.value.change.*;
import org.jspecify.annotations.*;

/**
 * A specialized lazy {@code int} expression.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class LazyIntExpression extends AbstractIntExpression implements LazyValue {

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
    public static LazyIntExpression of(ObservableBoolValue observable, BoolToIntFunction transform) {
        return new Transform(ex -> new BoolToIntBinding(ex::doInvalidate, observable, transform));
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
    public static LazyIntExpression of(ObservableByteValue observable, ByteToIntFunction transform) {
        return new Transform(ex -> new ByteToIntBinding(ex::doInvalidate, observable, transform));
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
    public static LazyIntExpression of(ObservableShortValue observable, ShortToIntFunction transform) {
        return new Transform(ex -> new ShortToIntBinding(ex::doInvalidate, observable, transform));
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
    public static LazyIntExpression of(ObservableIntValue observable, IntToIntFunction transform) {
        return new Transform(ex -> new IntToIntBinding(ex::doInvalidate, observable, transform));
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
    public static LazyIntExpression of(ObservableLongValue observable, LongToIntFunction transform) {
        return new Transform(ex -> new LongToIntBinding(ex::doInvalidate, observable, transform));
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
    public static LazyIntExpression of(ObservableFloatValue observable, FloatToIntFunction transform) {
        return new Transform(ex -> new FloatToIntBinding(ex::doInvalidate, observable, transform));
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
    public static LazyIntExpression of(ObservableDoubleValue observable, DoubleToIntFunction transform) {
        return new Transform(ex -> new DoubleToIntBinding(ex::doInvalidate, observable, transform));
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
    public static <S extends @Nullable Object> LazyIntExpression of(ObservableObjectValue<S> observable, ObjectToIntFunction<S> transform) {
        return new Transform(ex -> new ObjectToIntBinding<>(ex::doInvalidate, observable, transform));
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
    public static <S extends @Nullable Object> LazyIntExpression ofNested(ObservableObjectValue<S> observable, Function<S, ObservableIntValue> selector) {
        return new LazyIntExpression() {

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
                observable.addChangeListener(parentChangeListener);
                parentChangeListener.onChanged(observable, null, observable.get());
            }

            @Override
            protected int recomputeValue() {
                var parent = observable.get();
                return selector.apply(Objects.requireNonNull(parent)).get();
            }

        };
    }

    private final SimpleObjectProperty<State> state = new SimpleObjectProperty<>(State.UNINITIALIZED) {

        @Override
        public void onChanged(@Nullable State prevValue, @Nullable State value) {
            //noinspection ConstantConditions
            if (!value.isValid()) LazyIntExpression.this.notifyInvalidationListeners();
        }

    };

    @Nullable
    private IntSupplier provider = this::recomputeValue;
    private int value;

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
    public final int get() {
        //noinspection ConstantConditions
        if (!this.state.get().isValid()) { 
            var provider = Objects.requireNonNull(this.provider);
            this.updateValue(provider.get(), !this.state.get().isValid());

            this.provider = null;
        }

        return this.value;
    }

    @Override
    final int getImpl() {
        return this.value;
    }

    @Override
    final void setImpl(int value) {
        this.value = value;
    }

    @Override
    final void doInvalidate() {
        this.provider = this::recomputeValue;

        //noinspection ConstantConditions
        if (this.state.get().isValid()) this.state.set(State.INVALID);
    }

    @Override
    final boolean onChangedInternal(int oldValue, int newValue) {
        if (this.state.get() != State.UNINITIALIZED) {
            this.state.set(State.VALID);
            return true;
        } else {
            this.state.set(State.INITIALIZED);
            return false;
        }
    }

    /** A simple expression transforming a single value using the internal binding API. */
    private static final class Transform extends LazyIntExpression {

        private final transient IntBinding binding;

        private Transform(Function<LazyIntExpression, IntBinding> factory) {
            this.binding = factory.apply(this);
        }

        @Override
        protected int recomputeValue() {
            return this.binding.get();
        }

    }

}