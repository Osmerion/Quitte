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
package com.osmerion.quitte.property;

import java.util.Deque;
import java.util.Iterator;

import com.osmerion.quitte.TestValues;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class DequePropertyTest {

    private static void assertDequeEquals(Deque<?> expected, Deque<?> actual) {
        assertEquals(expected.size(), actual.size());

        Iterator<?> expectedItr = expected.iterator();
        Iterator<?> actualItr = actual.iterator();

        while (expectedItr.hasNext()) {
            assertEquals(expectedItr.next(), actualItr.next());
        }
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#asReadOnlyProperty                                                                           *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void testReadableProperty$asReadOnlyProperty_NewInstanceAvoidance() {
        DequeProperty<Object> other = new DequeProperty<>();
        ReadableDequeProperty<Object> property = other.asReadOnlyProperty();
        assertSame(property, property.asReadOnlyProperty());
    }

    @Test
    public void testReadableProperty$asReadOnlyProperty_IsBound() {
        DequeProperty<Object> other1 = new DequeProperty<>();
        ReadableDequeProperty<Object> property = other1.asReadOnlyProperty();
        assertEquals(other1.isBound(), property.isBound());

        DequeProperty<Object> other2 = new DequeProperty<>();
        other1.bindTo(other2);
        assertEquals(other1.isBound(), property.isBound());

        other1.unbind();
        assertEquals(other1.isBound(), property.isBound());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#isBound                                                                                      *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ReadableProperty$isBound_Initial() {
        //noinspection MismatchedQueryAndUpdateOfCollection
        DequeProperty<Object> property = new DequeProperty<>();
        assertFalse(property.isBound());
    }

    @Test
    public void test_ReadableProperty$isBound_BoundProperty() {
        DequeProperty<Object> other = new DequeProperty<>();
        DequeProperty<Object> property = new DequeProperty<>();
        assertFalse(property.isBound());

        property.bindTo(other);
        assertTrue(property.isBound());

        property.unbind();
        assertFalse(property.isBound());
    }

    @Test
    public void test_ReadableProperty$isBound_ReadOnlyProperty() {
        DequeProperty<Object> other = new DequeProperty<>();
        ReadableDequeProperty<Object> property = other.asReadOnlyProperty();
        assertFalse(property.isWritable());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#isWritable                                                                                   *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ReadableProperty$isWritable_Initial() {
        //noinspection MismatchedQueryAndUpdateOfCollection
        DequeProperty<Object> property = new DequeProperty<>();
        assertTrue(property.isWritable());
    }

    @Test
    public void test_ReadableProperty$isWritable_BoundProperty() {
        DequeProperty<Object> other = new DequeProperty<>();
        DequeProperty<Object> property = new DequeProperty<>();
        assertTrue(property.isWritable());

        property.bindTo(other);
        assertFalse(property.isWritable());

        property.unbind();
        assertTrue(property.isWritable());
    }

    @Test
    public void test_ReadableProperty$isWritable_ReadOnlyProperty() {
        DequeProperty<Object> other = new DequeProperty<>();
        ReadableDequeProperty<Object> property = other.asReadOnlyProperty();
        assertFalse(property.isWritable());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableDequeProperty#bindTo                                                                                       *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableDequeProperty$bindTo_Sync() {
        DequeProperty<Object> other1 = new DequeProperty<>();
        other1.add(TestValues.ObjectValue_L);

        DequeProperty<Object> property = new DequeProperty<>();
        property.bindTo(other1);

        assertDequeEquals(other1, property);
    }

    @Test
    public void test_WritableDequeProperty$bindTo_ThrowForBound() {
        DequeProperty<Object> other1 = new DequeProperty<>();
        DequeProperty<Object> property = new DequeProperty<>();
        property.bindTo(other1);

        DequeProperty<Object> other2 = new DequeProperty<>();
        assertThrows(IllegalStateException.class, () -> property.bindTo(other2));
    }

    @Test
    public void test_WritableDequeProperty$bindTo_Update() {
        DequeProperty<Object> other1 = new DequeProperty<>();

        DequeProperty<Object> property = new DequeProperty<>();
        property.bindTo(other1);

        assertDequeEquals(other1, property);

        other1.add(TestValues.ObjectValue_H);
        assertDequeEquals(other1, property);
    }

}