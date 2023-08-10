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
package com.osmerion.quitte.kotlinx.coroutines

import com.osmerion.quitte.InvalidationListener
import com.osmerion.quitte.collections.ListChangeListener
import com.osmerion.quitte.collections.MapChangeListener
import com.osmerion.quitte.collections.ObservableList
import com.osmerion.quitte.collections.ObservableMap
import com.osmerion.quitte.collections.ObservableSet
import com.osmerion.quitte.collections.SetChangeListener
import com.osmerion.quitte.value.ObservableValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

/**
 * Creates and instance of a cold [Flow] that subscribes to the given
 * [ObservableValue] and emits its values as they change. The resulting flow is
 * conflated, meaning that if several values arrive in quick succession, only
 * the last one will be emitted.
 *
 * Since this implementation uses an [InvalidationListener], even if this
 * [ObservableValue] supports lazy evaluation, eager computation will be
 * enforced while the flow is being collected.
 *
 * The resulting flow emits at least the initial value.
 *
 * @since   0.3.0
 */
public fun <T> ObservableValue<T>.asFlow(): Flow<T> = callbackFlow {
    val listener = InvalidationListener {
        trySend(value as T)
    }

    addInvalidationListener(listener)
    send(value as T)

    awaitClose {
        removeInvalidationListener(listener)
    }
}.conflate()

/**
 * Creates and instance of a cold [Flow] that subscribes to the given
 * [ObservableList] and emits its values as they change. The resulting flow is
 * conflated, meaning that if several values arrive in quick succession, only
 * the last one will be emitted.
 *
 * The resulting flow emits at least the initial value.
 *
 * @since   0.3.0
 */
public fun <E> ObservableList<E>.asFlow(): Flow<List<E>> = callbackFlow {
    val listener = ListChangeListener<E> { _, _ ->
        trySend(this@asFlow.toList())
    }

    addChangeListener(listener)
    send(this@asFlow.toList())

    awaitClose {
        removeChangeListener(listener)
    }
}.conflate()

/**
 * Creates and instance of a cold [Flow] that subscribes to the given
 * [ObservableMap] and emits its values as they change. The resulting flow is
 * conflated, meaning that if several values arrive in quick succession, only
 * the last one will be emitted.
 *
 * The resulting flow emits at least the initial value.
 *
 * @since   0.3.0
 */
public fun <K, V> ObservableMap<K, V>.asFlow(): Flow<Map<K, V>> = callbackFlow {
    val listener = MapChangeListener<K, V> { _, _ ->
        trySend(this@asFlow.toMap())
    }

    addChangeListener(listener)
    send(this@asFlow.toMap())

    awaitClose {
        removeChangeListener(listener)
    }
}.conflate()

/**
 * Creates and instance of a cold [Flow] that subscribes to the given
 * [ObservableSet] and emits its values as they change. The resulting flow is
 * conflated, meaning that if several values arrive in quick succession, only
 * the last one will be emitted.
 *
 * The resulting flow emits at least the initial value.
 *
 * @since   0.3.0
 */
public fun <E> ObservableSet<E>.asFlow(): Flow<Set<E>> = callbackFlow {
    val listener = SetChangeListener<E> { _, _ ->
        trySend(this@asFlow.toSet())
    }

    addChangeListener(listener)
    send(this@asFlow.toSet())

    awaitClose {
        removeChangeListener(listener)
    }
}.conflate()