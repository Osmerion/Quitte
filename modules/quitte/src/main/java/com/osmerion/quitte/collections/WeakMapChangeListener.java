/*
 * Copyright (c) 2018-2023 Leon Linhart,
 * All rights reserved.
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
package com.osmerion.quitte.collections;

import org.jspecify.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * A {@code WeakMapChangeListener} may be used to wrap a listener that should only be referenced weakly from an
 * {@link ObservableMap}.
 *
 * <p>This listener does not keep a strong reference to the wrapped listener.</p>
 *
 * @param <K>   the type of the map keys
 * @param <V>   the type of the map values
 *
 * @see WeakReference
 *
 * @since   0.8.0
 *
 * @author  Leon Linhart
 */
public final class WeakMapChangeListener<K extends @Nullable Object, V extends @Nullable Object> implements MapChangeListener<K, V> {

    private final WeakReference<MapChangeListener<K, V>> ref;

    private boolean wasGarbageCollected;

    /**
     * Wraps the given {@link MapChangeListener listener}.
     *
     * @param listener  the listener to wrap
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @since   0.8.0
     */
    public WeakMapChangeListener(MapChangeListener<K, V> listener) {
        this.ref = new WeakReference<>(Objects.requireNonNull(listener));
        this.wasGarbageCollected = false;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.8.0
     */
    @Override
    public void onChanged(ObservableMap<? extends K, ? extends V> observable, MapChangeListener.Change<? extends K, ? extends V> change) {
        var listener = this.ref.get();

        if (listener != null) {
            listener.onChanged(observable, change);
        } else {
            this.wasGarbageCollected = true;
        }
    }

    /**
     * {@return whether the underlying listener was garbage collected or has become invalid}
     *
     * @since   0.8.0
     */
    @Override
    public boolean isInvalid() {
        if (this.wasGarbageCollected) return true;

        var listener = this.ref.get();
        return (listener != null && listener.isInvalid());
    }

}