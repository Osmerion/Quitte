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
package com.osmerion.quitte.property;

import java.util.Objects;

import com.osmerion.quitte.functional.*;
import com.osmerion.quitte.internal.addon.*;
import com.osmerion.quitte.value.*;
import org.jspecify.annotations.*;

/**
 * A specialized lazy {@code double} property.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public class LazyDoubleProperty extends AbstractDoubleProperty implements LazyValue {

    private final SimpleObjectProperty<State> state = new SimpleObjectProperty<>(State.UNINITIALIZED) {

        @Override
        public void onChanged(@Nullable State prevValue, @Nullable State value) {
            //noinspection ConstantConditions
            if (!value.isValid()) LazyDoubleProperty.this.invalidate();
        }

    };

    @Nullable
    private DoubleSupplier provider;
    protected double value;

    /**
     * Creates a new property with the given initial value.
     *
     * @param initial   the initial value for the property
     *
     * @since   0.1.0
     */
    @PrimaryConstructor
    public LazyDoubleProperty(double initial) {
        this.value = initial;
        this.state.set(State.INITIALIZED);
    }

    /**
     * Creates a new, initially uninitialized, property.
     *
     * <p>The given supplier is considered to be the initial source of this property's value. When the property is
     * initialized, registered ChangeListeners will be called with an {@code oldValue} of {@code 0.0D} (the
     * initial value of the property's underlying variable.)</p>
     *
     * @param initial   the initial value provider for the property
     *
     * @since   0.1.0
     */
    public LazyDoubleProperty(DoubleSupplier initial) {
        this.provider = initial;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final ReadableObjectProperty<State> stateProperty() {
        return this.state.asReadOnlyProperty();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
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
    public final double get() {
        //noinspection ConstantConditions
        if (!this.state.get().isValid()) {
            var provider = Objects.requireNonNull(this.provider); 
            this.updateValue(this.intercept(provider.get()), !this.state.get().isValid());

            this.provider = null;
        }

        return this.value;
    }

    /**
     * Sets the function to recompute the value of this property and invalidates this property.
     *
     * @param supplier  the function to recompute this property's value
     *
     * @since   0.1.0
     */
    public final void set(DoubleSupplier supplier) {
        if (this.isBound()) throw new IllegalStateException("A bound property's value may not be set explicitly");

        this.provider = supplier;

        //noinspection ConstantConditions
        if (this.state.get().isValid()) this.state.set(State.INVALID);
    }

    @Override
    final double getImpl() {
        return this.value;
    }

    @Override
    final void setImpl(double value) {
        this.value = value;
    }

    final boolean setImplDeferrable(double value) {
        this.provider = () -> value;

        //noinspection ConstantConditions
        if (this.state.get().isValid()) this.state.set(State.INVALID);

        return false;
    }

    @Override
    final void onBindingInvalidated() {
        this.provider = this::getBoundValue;

        //noinspection ConstantConditions
        if (this.state.get().isValid()) this.state.set(State.INVALID);
    }

    @Override
    final boolean onChangedInternal(double oldValue, double newValue) {
        if (this.state.get() != State.UNINITIALIZED) {
            this.state.set(State.VALID);
            return true;
        } else {
            this.state.set(State.INITIALIZED);
            return false;
        }
    }

    /**
     * Intercepts values before updating this property.
     *
     * @param value the value
     *
     * @return  the result
     *
     * @since   0.1.0
     */
    protected double intercept(double value) {
        return value;
    }

}