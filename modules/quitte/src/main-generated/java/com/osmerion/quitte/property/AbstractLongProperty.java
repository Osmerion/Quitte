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
 * A basic implementation for a specialized writable {@code long} property.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class AbstractLongProperty implements WritableLongProperty {

    private final transient CopyOnWriteArraySet<LongChangeListener> changeListeners = new CopyOnWriteArraySet<>();
    private final transient CopyOnWriteArraySet<InvalidationListener> invalidationListeners = new CopyOnWriteArraySet<>();

    @Nullable
    private transient LongBinding binding;

    // package-private constructor for an effectively sealed class
    AbstractLongProperty() {}

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindToBoxing(ObservableValue<Long> observable) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new LongBinding.Generic<>(this::onBindingInvalidated, observable, Objects::requireNonNull);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized <S> void bindToBoxing(ObservableValue<S> observable, Function<S, Long> transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new LongBinding.Generic<>(this::onBindingInvalidated, observable, it -> Objects.requireNonNull(transform.apply(it)));
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableLongValue observable) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new LongToLongBinding(this::onBindingInvalidated, observable, it -> it);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableBoolValue observable, BoolToLongFunction transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new BoolToLongBinding(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableByteValue observable, ByteToLongFunction transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new ByteToLongBinding(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableShortValue observable, ShortToLongFunction transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new ShortToLongBinding(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableIntValue observable, IntToLongFunction transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new IntToLongBinding(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableLongValue observable, LongToLongFunction transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new LongToLongBinding(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableFloatValue observable, FloatToLongFunction transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new FloatToLongBinding(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableDoubleValue observable, DoubleToLongFunction transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new DoubleToLongBinding(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized <S> void bindTo(ObservableObjectValue<S> observable, ObjectToLongFunction<S> transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new ObjectToLongBinding<>(this::onBindingInvalidated, observable, transform);
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
    public final boolean addChangeListener(LongChangeListener listener) {
        return this.changeListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addBoxedChangeListener(ChangeListener<Long> listener) {
        if (this.changeListeners.stream().anyMatch(it -> it instanceof WrappingLongChangeListener && ((WrappingLongChangeListener) it).isWrapping(listener))) return false;
        return this.changeListeners.add(LongChangeListener.wrap(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeChangeListener(LongChangeListener listener) {
        return this.changeListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeBoxedChangeListener(ChangeListener<Long> listener) {
        return this.changeListeners.removeIf(it -> it instanceof WrappingLongChangeListener && ((WrappingLongChangeListener) it).isWrapping(listener));
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
    abstract long getImpl();

    final long getBoundValue() {
        return Objects.requireNonNull(this.binding).get();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final void set(long value) {
        if (this.binding != null) throw new IllegalStateException("A bound property's value may not be set explicitly");

        this.setInternal(value);
    }

    private void setInternal(long value) {
        if (this.setImplDeferrable(value)) this.invalidate();
    }

    /**
     * <b>This method must provide raw setter access and should not be called directly.</b>
     *
     * @param value the value
     */
    abstract void setImpl(long value);

    /**
     * Attempts to set the value of this property and returns whether the current value was invalidated.
     *
     * @return  whether the value has been invalidated
     */
    boolean setImplDeferrable(long value) {
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

    protected final void updateValue(long value, boolean notifyListeners) {
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

    boolean onChangedInternal(long oldValue, long newValue) {
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
    protected void onChanged(long oldValue, long newValue) {}

    /**
     * Called when this property was invalidated.
     *
     * @since   0.1.0
     */
    protected void onInvalidated() {}

}