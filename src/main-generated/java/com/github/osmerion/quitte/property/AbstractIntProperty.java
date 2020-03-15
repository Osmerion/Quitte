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

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.github.osmerion.quitte.*;
import com.github.osmerion.quitte.functional.*;
import com.github.osmerion.quitte.internal.binding.*;
import com.github.osmerion.quitte.value.*;
import com.github.osmerion.quitte.value.change.*;

/**
 * A basic implementation for a specialized writable {@code int} property.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class AbstractIntProperty implements WritableIntProperty {

    private final transient CopyOnWriteArraySet<IntChangeListener> changeListeners = new CopyOnWriteArraySet<>();
    private final transient CopyOnWriteArraySet<InvalidationListener> invalidationListeners = new CopyOnWriteArraySet<>();
    
    @Nullable
    private transient Binding binding;

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableValue<Integer> observable) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new GenericBinding<>(this, observable);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized <S> void bindTo(ObservableValue<S> observable, Function<S, Integer> transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new MutatingBinding<>(this, observable, transform);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableIntValue observable) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Int2IntBinding(this::setInternal, observable, it -> it);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableBoolValue observable, Bool2IntFunction transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Bool2IntBinding(this::setInternal, observable, transform);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableByteValue observable, Byte2IntFunction transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Byte2IntBinding(this::setInternal, observable, transform);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableShortValue observable, Short2IntFunction transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Short2IntBinding(this::setInternal, observable, transform);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableIntValue observable, Int2IntFunction transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Int2IntBinding(this::setInternal, observable, transform);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableLongValue observable, Long2IntFunction transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Long2IntBinding(this::setInternal, observable, transform);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableFloatValue observable, Float2IntFunction transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Float2IntBinding(this::setInternal, observable, transform);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableDoubleValue observable, Double2IntFunction transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Double2IntBinding(this::setInternal, observable, transform);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized <S> void bindTo(ObservableObjectValue<S> observable, Object2IntFunction<S> transform) {
        if (this.binding != null) throw new IllegalStateException();
        this.binding = new Object2IntBinding<>(this::setInternal, observable, transform);
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
    public final boolean addListener(IntChangeListener listener) {
        return this.changeListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addBoxedListener(ChangeListener<Integer> listener) {
        return this.changeListeners.add(IntChangeListener.wrap(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeListener(IntChangeListener listener) {
        return this.changeListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeBoxedListener(ChangeListener<Integer> listener) {
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

    private void notifyListeners(int prevValue, int newValue) {
        for (var itr = this.changeListeners.iterator(); itr.hasNext(); ) {
            var listener = itr.next();
            
            listener.onChanged(this, prevValue, newValue);
            if (listener.isInvalid()) itr.remove();
        }
    
        for (var itr = this.invalidationListeners.iterator(); itr.hasNext(); ) {
            var listener = itr.next();
            
            listener.onInvalidation(this);
            if (listener.isInvalid()) itr.remove();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public int get() {
        return this.getImpl();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final int set(int value) {
        if (this.binding != null) throw new IllegalStateException("A bound property's value may not be set explicitly");
        return this.setInternal(value);
    }

    private final int setInternal(int value) {
        int prev = this.getImpl();
        this.setImpl(value);
        this.notifyListeners(prev, value);

        return prev;
    }

    /**
     * TODO doc
     *
     * @since   0.1.0
     */
    protected abstract int getImpl();

    /**
     * TODO doc
     *
     * @since   0.1.0
     */
    protected abstract void setImpl(int value);

}