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
package com.osmerion.quitte;

/**
 * An {@code InvalidationListener} may be {@link Observable#addListener(InvalidationListener) attached} to an
 * {@link Observable} to get notified when the observable is invalidated. A listener may be attached to multiple
 * observables.
 *
 * @see Observable
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
@FunctionalInterface
public interface InvalidationListener {

    /**
     * Processes invalidation of an {@link Observable} this listener is attached to.
     *
     * @param observable    the observable that was invalidated
     *
     * @since   0.1.0
     */
    void onInvalidation(Observable observable);

    /**
     * Returns whether or not this listener is invalid.
     *
     * <p>Once an {@link Observable observable} discovers that a listener is invalid, it will stop notifying the
     * listener of updates and release all strong references to the listener.</p>
     *
     * <p>Once this method returned {@code true}, it must never return {@code false} again for the same instance.
     * Breaking this contract may result in unexpected behavior.</p>
     *
     * @return  whether or not this listener is invalid
     *
     * @since   0.1.0
     */
    default boolean isInvalid() {
        return false;
    }

}