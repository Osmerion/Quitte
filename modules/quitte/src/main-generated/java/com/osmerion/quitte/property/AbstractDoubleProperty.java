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
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.osmerion.quitte.*;
import com.osmerion.quitte.functional.*;
import com.osmerion.quitte.internal.binding.*;
import com.osmerion.quitte.internal.wrappers.*;
import com.osmerion.quitte.value.*;
import com.osmerion.quitte.value.change.*;

/**
 * A basic implementation for a specialized writable {@code double} property.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class AbstractDoubleProperty implements WritableDoubleProperty {

    private final transient CopyOnWriteArraySet<DoubleChangeListener> changeListeners = new CopyOnWriteArraySet<>();
    private final transient CopyOnWriteArraySet<InvalidationListener> invalidationListeners = new CopyOnWriteArraySet<>();

    @Nullable
    private transient DoubleBinding binding;

    // package-private constructor for an effectively sealed class
    AbstractDoubleProperty() {}

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindToBoxing(ObservableValue<Double> observable) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new DoubleBinding.Generic<>(this::onBindingInvalidated, observable, Objects::requireNonNull);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized <S> void bindToBoxing(ObservableValue<S> observable, Function<S, Double> transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new DoubleBinding.Generic<>(this::onBindingInvalidated, observable, it -> Objects.requireNonNull(transform.apply(it)));
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableDoubleValue observable) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new DoubleToDoubleBinding(this::onBindingInvalidated, observable, it -> it);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableBoolValue observable, BoolToDoubleFunction transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new BoolToDoubleBinding(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableByteValue observable, ByteToDoubleFunction transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new ByteToDoubleBinding(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableShortValue observable, ShortToDoubleFunction transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new ShortToDoubleBinding(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableIntValue observable, IntToDoubleFunction transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new IntToDoubleBinding(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableLongValue observable, LongToDoubleFunction transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new LongToDoubleBinding(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableFloatValue observable, FloatToDoubleFunction transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new FloatToDoubleBinding(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableDoubleValue observable, DoubleToDoubleFunction transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new DoubleToDoubleBinding(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized <S> void bindTo(ObservableObjectValue<S> observable, ObjectToDoubleFunction<S> transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new ObjectToDoubleBinding<>(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized boolean isBound() {
        return (this.binding != null);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean isWritable() {
        return !this.isBound();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void unbind() {
        if (!this.isBound()) throw new IllegalStateException();

        this.binding.release();
        this.binding = null;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addChangeListener(DoubleChangeListener listener) {
        return this.changeListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addBoxedChangeListener(ChangeListener<Double> listener) {
        if (this.changeListeners.stream().anyMatch(it -> it instanceof WrappingDoubleChangeListener && ((WrappingDoubleChangeListener) it).isWrapping(listener))) return false;
        return this.changeListeners.add(DoubleChangeListener.wrap(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeChangeListener(DoubleChangeListener listener) {
        return this.changeListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeBoxedChangeListener(ChangeListener<Double> listener) {
        return this.changeListeners.removeIf(it -> it instanceof WrappingDoubleChangeListener && ((WrappingDoubleChangeListener) it).isWrapping(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addInvalidationListener(InvalidationListener listener) {
        return this.invalidationListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeInvalidationListener(InvalidationListener listener) {
        return this.invalidationListeners.remove(listener);
    }

    /** <b>This method must provide raw setter access and should not be called directly.</b> */
    abstract double getImpl();

    final double getBoundValue() {
        return Objects.requireNonNull(this.binding).get();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final void set(double value) {
        if (this.binding != null) throw new IllegalStateException("A bound property's value may not be set explicitly");

        this.setInternal(value);
    }

    private void setInternal(double value) {
        if (this.setImplDeferrable(value)) this.invalidate();
    }

    /**
     * <b>This method must provide raw setter access and should not be called directly.</b>
     *
     * @param value the value
     */
    abstract void setImpl(double value);

    /**
     * Attempts to set the value of this property and returns whether the current value was invalidated.
     *
     * @return  whether the value has been invalidated
     */
    boolean setImplDeferrable(double value) {
        var prev = this.getImpl();
        if (prev == value) return false;

        this.updateValue(value, false);
        return true;
    }

    protected final void invalidate() {
        this.onInvalidated();

        for (var listener : this.invalidationListeners) {
            if (listener.isInvalid()) {
                this.invalidationListeners.remove(listener);
                continue;
            }

            listener.onInvalidation(this);
            if (listener.isInvalid()) this.invalidationListeners.remove(listener);
        }
    }

    protected final void updateValue(double value, boolean notifyListeners) {
        var prev = this.getImpl();
        var changed = prev != value;

        if (changed) {
            this.setImpl(value);
            notifyListeners = true;
        }

        if (notifyListeners) {
            if (this.onChangedInternal(prev, value) && !changed) return;
            this.onChanged(prev, value);

            for (var listener : this.changeListeners) {
                if (listener.isInvalid()) {
                    this.changeListeners.remove(listener);
                    continue;
                }

                listener.onChanged(this, prev, this.getImpl());
                if (listener.isInvalid()) this.changeListeners.remove(listener);
            }
        }
    }

    void onBindingInvalidated() {
        this.setInternal(this.getBoundValue());
    }

    boolean onChangedInternal(double oldValue, double newValue) {
        return true;
    }

    /**
     * Called when this property's value has changed.
     *
     * @param oldValue  the old value
     * @param newValue  the new value
     *
     * @since   0.1.0
     */
    protected void onChanged(double oldValue, double newValue) {}

    /**
     * Called when this property was invalidated.
     *
     * @since   0.1.0
     */
    protected void onInvalidated() {}

}