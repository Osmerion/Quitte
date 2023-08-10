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
 * A specialized lazy {@code boolean} expression.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class LazyBoolExpression extends AbstractBoolExpression implements LazyValue {

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
    public static LazyBoolExpression of(ObservableBoolValue observable, BoolToBoolFunction transform) {
        return new Transform(ex -> new BoolToBoolBinding(ex::doInvalidate, observable, transform));
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
    public static LazyBoolExpression of(ObservableByteValue observable, ByteToBoolFunction transform) {
        return new Transform(ex -> new ByteToBoolBinding(ex::doInvalidate, observable, transform));
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
    public static LazyBoolExpression of(ObservableShortValue observable, ShortToBoolFunction transform) {
        return new Transform(ex -> new ShortToBoolBinding(ex::doInvalidate, observable, transform));
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
    public static LazyBoolExpression of(ObservableIntValue observable, IntToBoolFunction transform) {
        return new Transform(ex -> new IntToBoolBinding(ex::doInvalidate, observable, transform));
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
    public static LazyBoolExpression of(ObservableLongValue observable, LongToBoolFunction transform) {
        return new Transform(ex -> new LongToBoolBinding(ex::doInvalidate, observable, transform));
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
    public static LazyBoolExpression of(ObservableFloatValue observable, FloatToBoolFunction transform) {
        return new Transform(ex -> new FloatToBoolBinding(ex::doInvalidate, observable, transform));
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
    public static LazyBoolExpression of(ObservableDoubleValue observable, DoubleToBoolFunction transform) {
        return new Transform(ex -> new DoubleToBoolBinding(ex::doInvalidate, observable, transform));
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
    public static <S extends @Nullable Object> LazyBoolExpression of(ObservableObjectValue<S> observable, ObjectToBoolFunction<S> transform) {
        return new Transform(ex -> new ObjectToBoolBinding<>(ex::doInvalidate, observable, transform));
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
    public static <S extends @Nullable Object> LazyBoolExpression ofNested(ObservableObjectValue<S> observable, Function<S, ObservableBoolValue> selector) {
        return new LazyBoolExpression() {

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
            protected boolean recomputeValue() {
                var parent = observable.get();
                return selector.apply(Objects.requireNonNull(parent)).get();
            }

        };
    }

    private final SimpleObjectProperty<State> state = new SimpleObjectProperty<>(State.UNINITIALIZED) {

        @Override
        public void onChanged(@Nullable State prevValue, @Nullable State value) {
            //noinspection ConstantConditions
            if (!value.isValid()) LazyBoolExpression.this.notifyInvalidationListeners();
        }

    };

    @Nullable
    private BoolSupplier provider = this::recomputeValue;
    private boolean value;

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
    public final boolean get() {
        //noinspection ConstantConditions
        if (!this.state.get().isValid()) { 
            var provider = Objects.requireNonNull(this.provider);
            this.updateValue(provider.get(), !this.state.get().isValid());

            this.provider = null;
        }

        return this.value;
    }

    @Override
    final boolean getImpl() {
        return this.value;
    }

    @Override
    final void setImpl(boolean value) {
        this.value = value;
    }

    @Override
    final void doInvalidate() {
        this.provider = this::recomputeValue;

        //noinspection ConstantConditions
        if (this.state.get().isValid()) this.state.set(State.INVALID);
    }

    @Override
    final boolean onChangedInternal(boolean oldValue, boolean newValue) {
        if (this.state.get() != State.UNINITIALIZED) {
            this.state.set(State.VALID);
            return true;
        } else {
            this.state.set(State.INITIALIZED);
            return false;
        }
    }

    /** A simple expression transforming a single value using the internal binding API. */
    private static final class Transform extends LazyBoolExpression {

        private final transient BoolBinding binding;

        private Transform(Function<LazyBoolExpression, BoolBinding> factory) {
            this.binding = factory.apply(this);
        }

        @Override
        protected boolean recomputeValue() {
            return this.binding.get();
        }

    }

}