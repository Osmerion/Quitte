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
@file:OptIn(ExperimentalCoroutinesApi::class)
package com.osmerion.quitte.kotlinx.coroutines

import app.cash.turbine.test
import com.osmerion.quitte.property.LazyIntProperty
import com.osmerion.quitte.property.SimpleIntProperty
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*
import java.util.concurrent.Executors

class ObservableValueAsFlowTest {

    @Test
    fun testOrder_Simple() = runTest {
        val property = SimpleIntProperty(0)

        val items = 1_000
        val flow = property.asFlow().takeWhile { it != items }

        val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

        flow.test {
            for (i in 1..items) {
                launch(dispatcher) {
                    property.set(i)
                }
            }

            for (i in 0 until items) {
                assertEquals(i, awaitItem())
            }

            awaitComplete()
        }
    }

    @Test
    fun testOrder_Lazy() = runTest {
        val property = LazyIntProperty(0)

        val items = 1_000
        val flow = property.asFlow().takeWhile { it != items }

        val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

        flow.test {
            for (i in 1..items) {
                launch(dispatcher) {
                    property.set { i }
                }
            }

            for (i in 0 until items) {
                assertEquals(i, awaitItem())
            }

            awaitComplete()
        }
    }

    @Test
    fun testIntermediateCrash_Simple() = runTest {
        class TestException : RuntimeException()

        val property = SimpleIntProperty(0)

        assertThrows<TestException> {
            property.asFlow().onEach {
                yield()
                throw TestException()
            }.collect()
        }
    }

    @Test
    fun testIntermediateCrash_Lazy() = runTest {
        class TestException : RuntimeException()

        val property = SimpleIntProperty(0)

        assertThrows<TestException> {
            property.asFlow().onEach {
                yield()
                throw TestException()
            }.collect()
        }
    }

}