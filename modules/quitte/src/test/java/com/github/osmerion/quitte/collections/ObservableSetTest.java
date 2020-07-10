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
package com.github.osmerion.quitte.collections;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO doc
 *
 * @author  Leon Linhart
 */
@SuppressWarnings("ConstantConditions")
public final class ObservableSetTest {

    private Set<String> underlyingSet;
    private ObservableSet<String> observableSet;
    private MockSetChangeListener<String> changeListener;

    @BeforeEach
    public void reset() {
        this.observableSet = ObservableSet.of(this.underlyingSet = new HashSet<>());
        this.observableSet.addListener(this.changeListener = new MockSetChangeListener<>());
    }

    @Test
    @DisplayName("ObservableSet#clear()")
    public void testClear() {
        try (var changeCtx = this.changeListener.push()) {
            this.observableSet.addAll(List.of("foo", "bar"));
            changeCtx.assertAddition("foo");
            changeCtx.assertAddition("bar");
            changeCtx.assertEmpty();

            this.observableSet.add("foo");
            changeCtx.assertEmpty();

            this.observableSet.clear();
            changeCtx.assertRemoval("foo");
            changeCtx.assertRemoval("bar");
            changeCtx.assertEmpty();
        }
    }

    @Test
    @DisplayName("ObservableSet#isEmpty() after modification")
    public void testIsEmpty() {
        this.observableSet.add("foo");
        assertFalse(this.observableSet.isEmpty());
        assertFalse(this.underlyingSet.isEmpty());

        this.observableSet.clear();
        assertTrue(this.observableSet.isEmpty());
        assertTrue(this.underlyingSet.isEmpty());
    }

    @Test
    @DisplayName("ObservableSet#isEmpty() after modification of underlying Set")
    public void testIsEmptyWithUnderlyingModification() {
        this.underlyingSet.add("foo");
        assertFalse(this.observableSet.isEmpty());

        this.underlyingSet.clear();
        assertTrue(this.observableSet.isEmpty());
    }

    @Test
    @DisplayName("ObservableSet addition/removal of `null`")
    public void testNull() {
        try (var changeCtx = this.changeListener.push()) {
            this.observableSet.add(null);
            assertEquals(1, this.underlyingSet.size());
            assertEquals(1, this.observableSet.size());
            changeCtx.assertAddition(null);
            changeCtx.assertEmpty();

            this.observableSet.remove(null);
            assertEquals(0, this.underlyingSet.size());
            assertEquals(0, this.observableSet.size());
            changeCtx.assertRemoval(null);
            changeCtx.assertEmpty();
        }
    }

    @Test
    @DisplayName("ObservableSet#size()")
    public void testSize() {
        assertEquals(0, this.underlyingSet.size());
        assertEquals(0, this.observableSet.size());

        this.observableSet.add("foo");
        assertEquals(1, this.observableSet.size());

        this.observableSet.add("foo");
        assertEquals(1, this.observableSet.size());

        this.observableSet.remove("foo");
        assertEquals(0, this.underlyingSet.size());
        assertEquals(0, this.observableSet.size());
    }

    @Test
    @DisplayName("ObservableSet#size() after modification of underlying Set")
    public void testSizeWithUnderlyingModification() {
        assertEquals(0, this.underlyingSet.size());
        assertEquals(0, this.observableSet.size());

        this.underlyingSet.add("foo");
        assertEquals(1, this.observableSet.size());

        this.observableSet.add("foo"); // Using observableSet on purpose to check consistency
        assertEquals(1, this.observableSet.size());

        this.underlyingSet.remove("foo");
        assertEquals(0, this.underlyingSet.size());
        assertEquals(0, this.observableSet.size());
    }

}