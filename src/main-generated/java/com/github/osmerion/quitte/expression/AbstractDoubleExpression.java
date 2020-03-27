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

import java.util.concurrent.CopyOnWriteArraySet;

import com.github.osmerion.quitte.*;
import com.github.osmerion.quitte.value.*;
import com.github.osmerion.quitte.value.change.*;

/**
 * A basic implementation for a specialized {@code double} expression.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class AbstractDoubleExpression implements Expression<Double>, ObservableDoubleValue {

    private final transient CopyOnWriteArraySet<DoubleChangeListener> changeListeners = new CopyOnWriteArraySet<>();
    private final transient CopyOnWriteArraySet<InvalidationListener> invalidationListeners = new CopyOnWriteArraySet<>();

    // package-private constructor for an effectively sealed class
    AbstractDoubleExpression() {}

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addListener(DoubleChangeListener listener) {
        return this.changeListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addBoxedListener(ChangeListener<Double> listener) {
        return this.changeListeners.add(DoubleChangeListener.wrap(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeListener(DoubleChangeListener listener) {
        return this.changeListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeBoxedListener(ChangeListener<Double> listener) {
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
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public double get() {
        return this.getImpl();
    }

    /** <b>This method must provide raw setter access and should not be called directly.</b> */
    abstract double getImpl();

    /**
     * <b>This method must provide raw setter access and should not be called directly.</b>
     *
     * @param value the value
     */
    abstract void setImpl(double value);

    protected final void invalidate() {
        for (var listener : this.invalidationListeners) {
            listener.onInvalidation(this);
            if (listener.isInvalid()) this.invalidationListeners.remove(listener);
        }
    }

    void onDependencyInvalidated() {
        if (this.updateValue(this.recomputeValue())) this.invalidate();
    }

    protected abstract double recomputeValue();

    final boolean updateValue(double value) {
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

    void onChangedInternal(double oldValue, double newValue) {}

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