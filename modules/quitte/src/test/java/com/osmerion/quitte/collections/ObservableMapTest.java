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
package com.osmerion.quitte.collections;

import java.util.HashMap;
import java.util.Map;
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
public final class ObservableMapTest {

    private Map<String, String> underlyingMap;
    private ObservableMap<String, String> observableMap;
    private MockMapChangeListener<String, String> changeListener;

    @BeforeEach
    public void reset() {
        this.observableMap = ObservableMap.of(this.underlyingMap = new HashMap<>());
        this.observableMap.addListener(this.changeListener = new MockMapChangeListener<>());
    }

    @Test
    @DisplayName("ObservableMap#clear()")
    public void testClear() {
        try (var changeCtx = this.changeListener.push()) {
            this.observableMap.putAll(Map.of("foo", "bar", "wackel", "pudding"));
            changeCtx.assertAddition("foo", "bar");
            changeCtx.assertAddition("wackel", "pudding");
            changeCtx.assertEmpty();

            this.observableMap.put("foo", "blub");
            changeCtx.assertUpdate("foo", "bar", "blub");
            changeCtx.assertEmpty();

            this.observableMap.clear();
            changeCtx.assertRemoval("foo", "blub");
            changeCtx.assertRemoval("wackel", "pudding");
            changeCtx.assertEmpty();
        }
    }

    @Test
    @DisplayName("ObservableMap#isEmpty() after modification")
    public void testIsEmpty() {
        this.observableMap.put("foo", "bar");
        assertFalse(this.observableMap.isEmpty());
        assertFalse(this.underlyingMap.isEmpty());

        this.observableMap.clear();
        assertTrue(this.observableMap.isEmpty());
        assertTrue(this.underlyingMap.isEmpty());
    }

    @Test
    @DisplayName("ObservableMap#isEmpty() after modification of underlying Map")
    public void testIsEmptyWithUnderlyingModification() {
        this.underlyingMap.put("foo", "bar");
        assertFalse(this.observableMap.isEmpty());

        this.underlyingMap.clear();
        assertTrue(this.observableMap.isEmpty());
    }

    @Test
    @DisplayName("ObservableMap addition/removal of `null`")
    public void testNull() {
        try (var changeCtx = this.changeListener.push()) {
            this.observableMap.put(null, null);
            assertEquals(1, this.underlyingMap.size());
            assertEquals(1, this.observableMap.size());
            changeCtx.assertAddition(null, null);
            changeCtx.assertEmpty();

            this.observableMap.remove(null);
            assertEquals(0, this.underlyingMap.size());
            assertEquals(0, this.observableMap.size());
            changeCtx.assertRemoval(null, null);
            changeCtx.assertEmpty();
        }
    }

    @Test
    @DisplayName("ObservableMap#size()")
    public void testSize() {
        assertEquals(0, this.underlyingMap.size());
        assertEquals(0, this.observableMap.size());

        this.observableMap.put("foo", "bar");
        assertEquals(1, this.observableMap.size());

        this.observableMap.put("foo", "bar");
        assertEquals(1, this.observableMap.size());

        this.observableMap.remove("foo");
        assertEquals(0, this.underlyingMap.size());
        assertEquals(0, this.observableMap.size());
    }

    @Test
    @DisplayName("ObservableMap#size() after modification of underlying Map")
    public void testSizeWithUnderlyingModification() {
        assertEquals(0, this.underlyingMap.size());
        assertEquals(0, this.observableMap.size());

        this.underlyingMap.put("foo", "bar");
        assertEquals(1, this.observableMap.size());

        this.observableMap.put("foo", "bar"); // Using observableMap on purpose to check consistency
        assertEquals(1, this.observableMap.size());

        this.underlyingMap.remove("foo");
        assertEquals(0, this.underlyingMap.size());
        assertEquals(0, this.observableMap.size());
    }

}