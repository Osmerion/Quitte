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
package com.github.osmerion.quitte.value;

import com.github.osmerion.quitte.value.change.*;

import static java.util.Objects.*;

/**
 * A specialized observable {@code byte} value.
 *
 * @see ObservableValue
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface ObservableByteValue extends ObservableValue<Byte> {

    /**
     * Returns the value represented by this object.
     *
     * @return  the current value
     *
     * @since   0.1.0
     */
    byte get();

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    default Byte getValue() {
        return this.get();
    }

    /**
     * Attaches the specified listener to this observable value.
     *
     * <p>If the given listener is already attached to this observable value, this method returns {@code false}.</p>
     *
     * <p>As long as the listener is attached it will be notified whenever the value of this {@code ObservableValue}
     * changes via {@link ChangeListener#onChanged(ObservableValue, Object, Object)}.</p>
     *
     * @param listener  the listener to be attached to this observable value
     *
     * @return  {@code true} if the listener was not previously attached to this observable value has been successfully
     *          attached, {@code false} otherwise
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @see #removeListener(ByteChangeListener)
     *
     * @since   0.1.0
     */
    boolean addListener(ByteChangeListener listener);

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    default boolean addBoxedListener(ChangeListener<Byte> listener) {
        return this.addListener(ByteChangeListener.wrap(requireNonNull(listener)));
    }

    /**
     * Detaches all listeners that are {@link Object#equals(Object) equal} to the given listener from this observable
     * value.
     *
     * @param listener  the listener to be detached from this observable value
     *
     * @return  {@code true} if at least one listener has been removed, {@code false} otherwise
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @see #addListener(ByteChangeListener)
     *
     * @since   0.1.0
     */
    boolean removeListener(ByteChangeListener listener);

}