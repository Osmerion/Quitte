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
package com.osmerion.quitte.compose

import androidx.compose.runtime.*
import com.osmerion.quitte.InvalidationListener
import com.osmerion.quitte.value.ObservableValue

/**
 * Starts observing this [ObservableValue] and represents its values via
 * [State]. When the observable is invalidated, the returned state will be
 * updated.
 *
 * The returned [State] conflates values; it will not be updated if the new
 * value is [equal][Any.equals] to the previous value, and observers may only
 * see the latest value if several values are set in rapid succession.
 *
 * The returned [State] automatically starts observing when it enters the
 * composition and stops when it leaves the composition again. (This effectively
 * causes [lazy values][com.osmerion.quitte.value.LazyValue] to be immediately
 * evaluated while the state is in the composition.)
 *
 * @since   0.4.0
 */
@Composable
public fun <T> ObservableValue<T>.observeAsState(): State<T?> =
    produceState(value) {
        val listener = InvalidationListener { value = this@observeAsState.value }
        addInvalidationListener(listener)

        awaitDispose {
            removeInvalidationListener(listener)
        }
    }