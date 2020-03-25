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
package com.github.osmerion.quitte.property;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.github.osmerion.quitte.*;
import com.github.osmerion.quitte.functional.*;
import com.github.osmerion.quitte.internal.binding.*;
import com.github.osmerion.quitte.value.*;
import com.github.osmerion.quitte.value.change.*;

/**
 * A basic implementation for a generic writable property.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class AbstractObjectProperty<T> implements WritableObjectProperty<T> {

    private final transient CopyOnWriteArraySet<ObjectChangeListener<T>> changeListeners = new CopyOnWriteArraySet<>();
    private final transient CopyOnWriteArraySet<InvalidationListener> invalidationListeners = new CopyOnWriteArraySet<>();

    @Nullable
    private transient ObjectBinding<T> binding;

    // package-private constructor for an effectively sealed class
    AbstractObjectProperty() {}

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableValue<T> observable) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new ObjectBinding.Generic<>(this::onBindingInvalidated, observable, it -> it);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized <S> void bindTo(ObservableValue<S> observable, Function<S, T> transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new ObjectBinding.Generic<>(this::onBindingInvalidated, observable, transform::apply);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableObjectValue<T> observable) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Object2ObjectBinding<>(this::onBindingInvalidated, observable, it -> it);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableBoolValue observable, Bool2ObjectFunction<T> transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Bool2ObjectBinding<>(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableByteValue observable, Byte2ObjectFunction<T> transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Byte2ObjectBinding<>(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableShortValue observable, Short2ObjectFunction<T> transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Short2ObjectBinding<>(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableIntValue observable, Int2ObjectFunction<T> transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Int2ObjectBinding<>(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableLongValue observable, Long2ObjectFunction<T> transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Long2ObjectBinding<>(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableFloatValue observable, Float2ObjectFunction<T> transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Float2ObjectBinding<>(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableDoubleValue observable, Double2ObjectFunction<T> transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Double2ObjectBinding<>(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized <S> void bindTo(ObservableObjectValue<S> observable, Object2ObjectFunction<S, T> transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Object2ObjectBinding<>(this::onBindingInvalidated, observable, transform);
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
    public final synchronized void unbind() {
        if (this.binding == null) throw new IllegalStateException();

        this.binding.release();
        this.binding = null;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addListener(ObjectChangeListener<T> listener) {
        return this.changeListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addBoxedListener(ChangeListener<T> listener) {
        return this.changeListeners.add(ObjectChangeListener.wrap(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeListener(ObjectChangeListener<T> listener) {
        return this.changeListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeBoxedListener(ChangeListener<T> listener) {
        //noinspection SuspiciousMethodCalls
        return this.changeListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    public final boolean addListener(InvalidationListener listener) {
        return this.invalidationListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    public final boolean removeListener(InvalidationListener listener) {
        return this.invalidationListeners.remove(listener);
    }

    /**
     * <b>This method must provide raw setter access and should not be called directly.</b>
     *
     * @since   0.1.0
     */
    @Nullable
    abstract T getImpl();

    @Nullable
    final T getBoundValue() {
        return Objects.requireNonNull(this.binding).get();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    @Nullable
    public final T set(@Nullable T value) {
        if (this.binding != null) throw new IllegalStateException("A bound property's value may not be set explicitly");
        return this.setInternal(value);
    }

    @Nullable
    private T setInternal(@Nullable T value) {
        var prev = this.getImpl();
        if (this.setImplDeferrable(value)) this.invalidate();

        return prev;
    }

    /**
     * <b>This method must provide raw setter access and should not be called directly.</b>
     *
     * @param value the value
     */
    abstract void setImpl(@Nullable T value);

    /**
     * Attempts to set the value of this property and returns whether or not the current value was invalidated.
     *
     * @return  whether or not the value has been invalidated
     */
    boolean setImplDeferrable(@Nullable T value) {
        var prev = this.getImpl();
        if (prev == value) return false;

        this.updateValue(value);
        return true;
    }

    protected final void invalidate() {
        this.onInvalidated();

        for (var listener : this.invalidationListeners) {
            listener.onInvalidation(this);
            if (listener.isInvalid()) this.invalidationListeners.remove(listener);
        }
    }

    protected final boolean updateValue(@Nullable T value) {
        var prev = this.getImpl();
        if (prev == value) return false;

        this.setImpl(value);
        this.onChangedInternal(prev, value);
        this.onChanged(prev, value);

        for (var listener : this.changeListeners) {
            listener.onChanged(this, prev, this.getImpl());
            if (listener.isInvalid()) this.changeListeners.remove(listener);
        }

        return true;
    }

    void onBindingInvalidated() {
        this.setInternal(this.getBoundValue());
    }

    void onChangedInternal(@Nullable T oldValue, @Nullable T newValue) {}

    /**
     * Called when this property's value has changed.
     *
     * @param oldValue  the old value
     * @param newValue  the new value
     *
     * @since   0.1.0
     */
    protected void onChanged(@Nullable T oldValue, @Nullable T newValue) {}

    /**
     * Called when this property was invalidated.
     *
     * @since   0.1.0
     */
    protected void onInvalidated() {}

}