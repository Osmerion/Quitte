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
package com.osmerion.quitte.internal.wrappers;

import com.osmerion.quitte.value.*;
import com.osmerion.quitte.value.change.*;

/**
 * A wrapping change-listener.
 *
 * <p>The wrapper object is {@link Object#equals(Object) equal} to the underlying listener and shares the same
 * hashcode.</p>
 *
 * @see ChangeListener
 * @see LongChangeListener
 *
 * @author  Leon Linhart
 */
public final class WrappingLongChangeListener implements LongChangeListener {

    private final ChangeListener<Long> listener;

    public WrappingLongChangeListener(ChangeListener<Long> listener) {
        this.listener = listener;
    }

    @Override
    public void onChanged(ObservableLongValue observable, long oldValue, long newValue) {
        this.listener.onChanged(observable, oldValue, newValue);
    }

    @Override
    public boolean isInvalid() {
        return this.listener.isInvalid();
    }

    /**
     * Returns whether or not this wrapper is wrapping the given listener.
     *
     * @param listener  the listener to test for
     *
     * @return  whether or not this wrapper is wrappping the given listener
     */
    public boolean isWrapping(ChangeListener<Long> listener) {
        return this.listener == listener;
    }

}