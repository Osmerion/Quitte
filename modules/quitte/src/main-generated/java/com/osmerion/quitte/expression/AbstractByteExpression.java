/*
 * Copyright (c) 2018-2022 Leon Linhart,
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

import java.util.concurrent.CopyOnWriteArraySet;

import com.osmerion.quitte.internal.wrappers.*;
import com.osmerion.quitte.value.*;
import com.osmerion.quitte.value.change.*;

/**
 * A basic implementation for a specialized {@code byte} expression.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class AbstractByteExpression extends AbstractExpression implements ValueExpression<Byte>, ObservableByteValue {

    private final transient CopyOnWriteArraySet<ByteChangeListener> changeListeners = new CopyOnWriteArraySet<>();

    // package-private constructor for an effectively sealed class
    AbstractByteExpression() {}

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addChangeListener(ByteChangeListener listener) {
        return this.changeListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addBoxedChangeListener(ChangeListener<Byte> listener) {
        if (this.changeListeners.stream().anyMatch(it -> it instanceof WrappingByteChangeListener && ((WrappingByteChangeListener) it).isWrapping(listener))) return false;
        return this.changeListeners.add(ByteChangeListener.wrap(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeChangeListener(ByteChangeListener listener) {
        return this.changeListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeBoxedChangeListener(ChangeListener<Byte> listener) {
        return this.changeListeners.removeIf(it -> it instanceof WrappingByteChangeListener && ((WrappingByteChangeListener) it).isWrapping(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public byte get() {
        return this.getImpl();
    }

    /** <b>This method must provide raw setter access and should not be called directly.</b> */
    abstract byte getImpl();

    /**
     * <b>This method must provide raw setter access and should not be called directly.</b>
     *
     * @param value the value
     */
    abstract void setImpl(byte value);

    @Override
    void doInvalidate() {
        if (this.updateValue(this.recomputeValue(), false)) this.notifyInvalidationListeners();
    }

    protected abstract byte recomputeValue();

    final boolean updateValue(byte value, boolean notifyListeners) {
        var prev = this.getImpl();
        var changed = prev != value;

        if (changed) {
            this.setImpl(value);
            notifyListeners = true;
        }

        if (notifyListeners) {
            if (this.onChangedInternal(prev, value) && !changed) return false;
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

        return changed;
    }

    boolean onChangedInternal(byte oldValue, byte newValue) {
        return true;
    }

    /**
     * Called when this expression's value has changed.
     *
     * @param oldValue  the old value
     * @param newValue  the new value
     *
     * @since   0.1.0
     */
    protected void onChanged(byte oldValue, byte newValue) {}

    /**
     * Called when this expression was invalidated.
     *
     * @since   0.1.0
     */
    protected void onInvalidated() {}

}