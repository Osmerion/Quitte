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
package com.osmerion.quitte.collections;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ObservableDeque}.
 *
 * @author  Leon Linhart
 */
@SuppressWarnings("ConstantConditions")
public final class ObservableDequeTest {

    private Deque<String> underlyingDeque;
    private ObservableDeque<String> observableDeque;
    private MockDequeChangeListener<String> changeListener;

    @BeforeEach
    public void reset() {
        this.observableDeque = ObservableDeque.of(this.underlyingDeque = new ArrayDeque<>());
        this.observableDeque.addChangeListener(this.changeListener = new MockDequeChangeListener<>());
    }

    @Test
    @DisplayName("ObservableSet#clear()")
    public void testClear() {
        try (var changeCtx = this.changeListener.push()) {
            this.observableDeque.addAll(List.of("foo", "bar"));
            changeCtx.assertInsertion(ObservableDeque.Site.TAIL, List.of("foo", "bar"));
            changeCtx.assertEmpty();

            this.observableDeque.add("foo");
            changeCtx.assertInsertion(ObservableDeque.Site.TAIL, "foo");

            this.observableDeque.clear();
            changeCtx.assertRemoval(ObservableDeque.Site.OPAQUE, List.of("foo", "bar", "foo"));
            changeCtx.assertEmpty();
        }
    }

    @Test
    @DisplayName("ObservableSet#isEmpty() after modification")
    public void testIsEmpty() {
        this.observableDeque.add("foo");
        assertFalse(this.observableDeque.isEmpty());
        assertFalse(this.underlyingDeque.isEmpty());

        this.observableDeque.clear();
        assertTrue(this.observableDeque.isEmpty());
        assertTrue(this.underlyingDeque.isEmpty());
    }

}
