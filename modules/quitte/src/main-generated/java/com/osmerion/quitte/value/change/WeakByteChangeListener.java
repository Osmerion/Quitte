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
package com.osmerion.quitte.value.change;

import java.lang.ref.WeakReference;
import java.util.Objects;

import com.osmerion.quitte.value.*;

/**
 * A {@code WeakByteChangeListener} may be used to wrap a listener that should only be referenced weakly
 * from an {@link ObservableValue}.
 *
 * <p>This listener does not keep a strong reference to the wrapped listener.</p>
 * 
 * @see WeakReference
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public final class WeakByteChangeListener implements ByteChangeListener {

    private final WeakReference<ByteChangeListener> ref;

    private boolean wasGarbageCollected;

    /**
     * Wraps the given {@link ByteChangeListener listener}.
     *
     * @param listener  the listener to wrap
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @since   0.1.0
     */
    public WeakByteChangeListener(ByteChangeListener listener) {
        this.ref = new WeakReference<>(Objects.requireNonNull(listener));
        this.wasGarbageCollected = false;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public void onChanged(ObservableByteValue observable, byte oldValue, byte newValue) {
        var listener = this.ref.get();

        if (listener != null) {
            listener.onChanged(observable, oldValue, newValue);
        } else {
            this.wasGarbageCollected = false;
        }
    }

    /**
     * Returns whether or not the underlying listener was garbage collected or has become invalid.
     *
     * @return  whether or not the underlying listener was garbage collected or has become invalid
     *
     * @since   0.1.0
     */
    @Override
    public boolean isInvalid() {
        if (this.wasGarbageCollected) return true;

        var listener = this.ref.get();
        return (listener != null && listener.isInvalid());
    }

}