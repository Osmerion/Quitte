/*
 * Copyright (c) 2018-2022 Leon Linhart,
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ObservableList} and bundled implementations.
 *
 * @author  Leon Linhart
 */
@SuppressWarnings("ConstantConditions")
public final class ObservableListTest {

    private List<String> underlyingList;
    private ObservableList<String> observableList;
    private MockListChangeListener<String> changeListener;

    @BeforeEach
    public void reset() {
        this.observableList = ObservableList.of(this.underlyingList = new ArrayList<>());
        this.observableList.addChangeListener(this.changeListener = new MockListChangeListener<>());
    }

    @Test
    @DisplayName("ObservableList#clear()")
    public void testClear() {
        try (var changeCtx = this.changeListener.push()) {
            this.observableList.addAll(List.of("foo", "bar"));
            changeCtx.assertInsertion(0, "foo");
            changeCtx.assertInsertion(1, "bar");
            changeCtx.assertEmpty();

            this.observableList.add("foo");
            changeCtx.assertInsertion(2, "foo");
            changeCtx.assertEmpty();

            this.observableList.clear();
            changeCtx.assertRemoval(0, "foo");
            changeCtx.assertRemoval(0, "bar");
            changeCtx.assertRemoval(0, "foo");
            changeCtx.assertEmpty();
        }
    }

    @Test
    @DisplayName("ObservableSet#isEmpty() after modification")
    public void testIsEmpty() {
        this.observableList.add("foo");
        assertFalse(this.observableList.isEmpty());
        assertFalse(this.underlyingList.isEmpty());

        this.observableList.clear();
        assertTrue(this.observableList.isEmpty());
        assertTrue(this.underlyingList.isEmpty());
    }

    @Test
    @DisplayName("ObservableSet#isEmpty() after modification of underlying Set")
    public void testIsEmptyWithUnderlyingModification() {
        this.underlyingList.add("foo");
        assertFalse(this.observableList.isEmpty());

        this.underlyingList.clear();
        assertTrue(this.observableList.isEmpty());
    }

    @Test
    @DisplayName("ObservableList addition/removal of `null`")
    public void testNull() {
        try (var changeCtx = this.changeListener.push()) {
            this.observableList.add(null);
            assertEquals(1, this.underlyingList.size());
            assertEquals(1, this.observableList.size());
            changeCtx.assertInsertion(0, Collections.singletonList(null));
            changeCtx.assertEmpty();

            this.observableList.remove(null);
            assertEquals(0, this.underlyingList.size());
            assertEquals(0, this.observableList.size());
            changeCtx.assertRemoval(0, Collections.singletonList(null));
            changeCtx.assertEmpty();
        }
    }

    @Test
    @DisplayName("ObservableList permutation detection")
    public void testPerm() {
        try (var changeCtx = this.changeListener.push()) {
            this.observableList.addAll(List.of("foo", "bar", "blub", "dup"));
            changeCtx.assertInsertion(0, "foo");
            changeCtx.assertInsertion(1, "bar");
            changeCtx.assertInsertion(2, "blub");
            changeCtx.assertInsertion(3, "dup");
            changeCtx.assertEmpty();

            this.observableList.sort(Comparator.naturalOrder());
            changeCtx.assertPermutation(List.of(3, 0, 1, 2));
            changeCtx.assertEmpty();
        }
    }

    @Test
    @DisplayName("ObservableSet#setAll()")
    public void testSetAll() {
        List<String> elements = List.of("A", "b");
        ObservableList<String> observableList = ObservableList.of(new ArrayList<>(elements));
        assertFalse(observableList.setAll(elements));

        List<String> newElements = List.of("d", "e", "f");

        observableList.setAll(newElements);
        assertEquals(newElements, observableList);
    }

    @Test
    @DisplayName("ObservableList#size()")
    public void testSize() {
        assertEquals(0, this.underlyingList.size());
        assertEquals(0, this.observableList.size());

        this.observableList.add("foo");
        assertEquals(1, this.observableList.size());

        this.observableList.add("foo");
        assertEquals(2, this.observableList.size());

        this.observableList.remove("foo");
        assertEquals(1, this.underlyingList.size());
        assertEquals(1, this.observableList.size());
    }

    @Test
    @DisplayName("ObservableList#size() after modification of underlying List")
    public void testSizeWithUnderlyingModification() {
        assertEquals(0, this.underlyingList.size());
        assertEquals(0, this.observableList.size());

        this.underlyingList.add("foo");
        assertEquals(1, this.observableList.size());

        this.observableList.add("foo"); // Using observableList on purpose to check consistency
        assertEquals(2, this.observableList.size());

        this.underlyingList.remove("foo");
        assertEquals(1, this.underlyingList.size());
        assertEquals(1, this.observableList.size());
    }


}