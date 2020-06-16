/*
 * Copyright (c) 2018-2019 Leon Linhart,
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
package com.github.osmerion.quitte.value;

import com.github.osmerion.quitte.property.ReadableObjectProperty;

/**
 * An object representing a lazily evaluated value.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface LazyValue {

    /**
     * Returns a read-only view of the state of this lazy value.
     *
     * @return  a read-only view of the state of this lazy value
     *
     * @since   0.1.0
     */
    ReadableObjectProperty<State> stateProperty();

    /**
     * Returns the state of this lazy value.
     *
     * @return  the state of this lazy value
     *
     * @since   0.1.0
     */
    State getState();

    /**
     * The state of a lazy value.
     *
     * @since   0.1.0
     */
    enum State {
        /**
         * Describes an invalid value that was never valid.
         *
         * @since   0.1.0
         */
        UNINITIALIZED,
        /**
         * Describes the first time the value is valid.
         *
         * @since   0.1.0
         */
        INITIALIZED,
        /**
         * Describes a valid value.
         *
         * @since   0.1.0
         */
        VALID,
        /**
         * Describes an invalid value.
         *
         * @since   0.1.0
         */
        INVALID;

        /**
         * Returns whether or not this state describes a valid value.
         *
         * @return  whether or not this state describes a valid value
         *
         * @since   0.1.0
         */
        public boolean isValid() {
            return this == INITIALIZED || this == VALID;
        }

    }

}