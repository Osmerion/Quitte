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
package com.github.osmerion.quitte;

/**
 * TODO doc
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface Observable {

    /**
     * Attaches the specified listener to this observable.
     *
     * <p>If the given listener is already attached to this observable, this method returns {@code false}.</p>
     *
     * <p>As long as the listener is attached it will be notified whenever this observable is invalidated.</p>
     *
     * @param listener  the listener to be attached to this observable
     *
     * @return  {@code true} if the listener was not previously attached to this observable value has been successfully
     *          attached, or {@code false} otherwise
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @see #removeListener(InvalidationListener)
     *
     * @since   0.1.0
     */
    boolean addListener(InvalidationListener listener);

    /**
     * Detaches the given listener from this observable.
     *
     * <p>If the given listener is not attached to this observable, this method does nothing and returns {@code false}.
     * </p>
     *
     * @param listener  the listener to be detached from this observable
     *
     * @return  {@code true} if the listener was attached and has been detached from this observable, or {@code false}
     *          otherwise
     *
     * @throws NullPointerException if the given listener is {@code null}
     *
     * @see #addListener(InvalidationListener)
     *
     * @since   0.1.0
     */
    boolean removeListener(InvalidationListener listener);

}