/*
 * Copyright (c) 2018-2020 Leon Linhart,
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
package com.github.osmerion.quitte.collections;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * TODO doc
 *
 * @param <E>
 *
 * @see WeakReference
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public final class WeakSetChangeListener<E> implements SetChangeListener<E> {

    private final WeakReference<SetChangeListener<E>> ref;

    /**
     * TODO doc
     *
     * @param ref
     *
     * @throws NullPointerException
     *
     * @since   0.1.0
     */
    public WeakSetChangeListener(SetChangeListener<E> ref) {
        this.ref = new WeakReference<>(Objects.requireNonNull(ref));
    }

    /**
     * {@inheritDoc}
     *
     * @implNote    TODO doc
     *
     * @since   0.1.0
     */
    @Override
    public void onChanged(SetChangeListener.Change<? extends E> change) {
        SetChangeListener<E> listener = this.ref.get();

        if (listener != null) {
            listener.onChanged(change);
        } else {
            change.removeListener(this);
        }
    }

}