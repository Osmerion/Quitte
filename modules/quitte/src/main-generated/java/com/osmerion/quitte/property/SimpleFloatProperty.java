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
package com.osmerion.quitte.property;

import com.osmerion.quitte.internal.addon.*;

/**
 * A specialized writable {@code float} property.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public class SimpleFloatProperty extends AbstractFloatProperty {

    private float value;

    /**
     * Creates a new property with the given initial value.
     *
     * @param initial   the initial value for the property
     *
     * @since   0.1.0
     */
    @PrimaryConstructor
    public SimpleFloatProperty(float initial) {
        this.value = initial;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final float get() {
        return this.getImpl();
    }

    @Override
    final float getImpl() {
        return this.value;
    }

    @Override
    final void setImpl(float value) {
        this.value = value;
    }

    @Override
    final boolean setImplDeferrable(float value) {
        return super.setImplDeferrable(this.intercept(value));
    }

    /**
     * Intercepts values before updating this property.
     *
     * @param value the value
     *
     * @return  the result
     *
     * @since   0.1.0
     */
    protected float intercept(float value) {
        return value;
    }

}