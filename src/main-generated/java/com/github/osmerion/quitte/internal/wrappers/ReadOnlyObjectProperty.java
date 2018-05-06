/*
 * Copyright (c) 2018 Leon Linhart,
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
 * A generic read-only property.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public final class ReadOnlyObjectProperty<T> implements ReadableObjectProperty<T> {

    protected final ReadableObjectProperty<T> property;

    public ReadOnlyObjectProperty(ReadableObjectProperty<T> property) {
        this.property = property;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public ReadableObjectProperty<T> asReadOnlyProperty() {
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public T get() {
        return this.property.get();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
	@Override
    public T getValue() {
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
    public boolean addListener(ObjectChangeListener listener) {
        return this.property.addListener(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
	@Override
    public boolean addListener(ChangeListener<T> listener) {
        return this.property.addListener(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public boolean removeListener(ObjectChangeListener listener) {
        return this.property.removeListener(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public boolean removeListener(ChangeListener<T> listener) {
        return this.property.removeListener(listener);
    }

}