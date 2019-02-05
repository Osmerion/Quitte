/*
 * Copyright (c) 2018-2019 Leon Linhart,
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
package com.github.osmerion.quitte.internal.wrappers;

import com.github.osmerion.quitte.property.*;
import com.github.osmerion.quitte.value.change.*;

/**
 * A specialized read-only {@code boolean} property.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public final class ReadOnlyBoolProperty implements ReadableBoolProperty {

    protected final ReadableBoolProperty property;

    public ReadOnlyBoolProperty(ReadableBoolProperty property) {
        this.property = property;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public ReadableBoolProperty asReadOnlyProperty() {
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public boolean get() {
        return this.property.get();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
	@Override
    public Boolean getValue() {
        return this.property.getValue();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
	@Override
    public boolean isBound() {
        return this.property.isBound();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public boolean isWritable() {
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public boolean addListener(BoolChangeListener listener) {
        return this.property.addListener(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
	@Override
    public boolean addListener(ChangeListener<Boolean> listener) {
        return this.property.addListener(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public boolean removeListener(BoolChangeListener listener) {
        return this.property.removeListener(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public boolean removeListener(ChangeListener<Boolean> listener) {
        return this.property.removeListener(listener);
    }

}