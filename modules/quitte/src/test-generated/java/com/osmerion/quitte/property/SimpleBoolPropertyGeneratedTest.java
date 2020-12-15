/*
 * Copyright (c) 2018-2020 Leon Linhart,
 * All rights reserved.
 * MACHINE GENERATED FILE, DO NOT EDIT
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

import java.util.concurrent.atomic.AtomicInteger;

import com.osmerion.quitte.*;
import com.osmerion.quitte.value.*;
import com.osmerion.quitte.value.change.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
            
/**
 * Generated tests for {@link SimpleBoolProperty}.
 *
 * @author  Leon Linhart
 */
public final class SimpleBoolPropertyGeneratedTest {

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#asReadOnlyProperty                                                                           *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void testReadableProperty$asReadOnlyProperty_NewInstanceAvoidance() {
        SimpleBoolProperty other = new SimpleBoolProperty(TestValues.BoolValue_L);
        ReadableBoolProperty property = other.asReadOnlyProperty();
        assertSame(property, property.asReadOnlyProperty());
    }

    @Test
    public void testReadableProperty$asReadOnlyProperty_Get() {
        SimpleBoolProperty other = new SimpleBoolProperty(TestValues.BoolValue_L);
        ReadableBoolProperty property = other.asReadOnlyProperty();
        assertEquals(other.get(), property.get());

        other.set(TestValues.BoolValue_H);
        assertEquals(other.get(), property.get());
    }

    @Test
    public void testReadableProperty$asReadOnlyProperty_GetValue() {
        SimpleBoolProperty other = new SimpleBoolProperty(TestValues.BoolValue_L);
        ReadableBoolProperty property = other.asReadOnlyProperty();
        assertEquals(other.getValue(), property.getValue());

        other.set(TestValues.BoolValue_H);
        assertEquals(other.getValue(), property.getValue());
    }

    @Test
    public void testReadableProperty$asReadOnlyProperty_IsBound() {
        SimpleBoolProperty other1 = new SimpleBoolProperty(TestValues.BoolValue_L);
        ReadableBoolProperty property = other1.asReadOnlyProperty();
        assertEquals(other1.isBound(), property.isBound());

        SimpleBoolProperty other2 = new SimpleBoolProperty(TestValues.BoolValue_L);
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
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        assertFalse(property.isBound());
    }

    @Test
    public void test_ReadableProperty$isBound_BoundProperty() {
        SimpleBoolProperty other = new SimpleBoolProperty(TestValues.BoolValue_L);
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        assertFalse(property.isBound());

        property.bindTo(other);
        assertTrue(property.isBound());

        property.unbind();
        assertFalse(property.isBound());
    }

    @Test
    public void test_ReadableProperty$isBound_ReadOnlyProperty() {
        SimpleBoolProperty other = new SimpleBoolProperty(TestValues.BoolValue_L);
        ReadableBoolProperty property = other.asReadOnlyProperty();
        assertFalse(property.isWritable());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#isWritable                                                                                   *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ReadableProperty$isWritable_Initial() {
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        assertTrue(property.isWritable());
    }

    @Test
    public void test_ReadableProperty$isWritable_BoundProperty() {
        SimpleBoolProperty other = new SimpleBoolProperty(TestValues.BoolValue_L);
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        assertTrue(property.isWritable());

        property.bindTo(other);
        assertFalse(property.isWritable());

        property.unbind();
        assertTrue(property.isWritable());
    }

    @Test
    public void test_ReadableProperty$isWritable_ReadOnlyProperty() {
        SimpleBoolProperty other = new SimpleBoolProperty(TestValues.BoolValue_L);
        ReadableBoolProperty property = other.asReadOnlyProperty();
        assertFalse(property.isWritable());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ObservableValue#addBoxedListener                                                                              *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    // TODO come up with proper tests

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ObservableValue#get                                                                                      *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ObservableValue$get_Initial() {
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_H);
        assertEquals(TestValues.BoolValue_H, property.get());
    }

    @Test
    public void test_ObservableValue$get_SetGet() {
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        assertEquals(TestValues.BoolValue_L, property.get());

        property.set(TestValues.BoolValue_H);
        assertEquals(TestValues.BoolValue_H, property.get());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ObservableValue#getValue                                                                                      *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ObservableValue$getValue_Initial() {
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_H);
        assertEquals(TestValues.BoolValue_H, property.getValue());
    }

    @Test
    public void test_ObservableValue$getValue_SetGet() {
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        assertEquals(TestValues.BoolValue_L, property.getValue());

        property.set(TestValues.BoolValue_H);
        assertEquals(TestValues.BoolValue_H, property.getValue());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableProperty#bindTo                                                                                       *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableProperty$bindTo_ThrowForBound() {
        SimpleBoolProperty other1 = new SimpleBoolProperty(TestValues.BoolValue_L);
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.bindTo(other1);

        SimpleBoolProperty other2 = new SimpleBoolProperty(TestValues.BoolValue_L);
        assertThrows(IllegalStateException.class, () -> property.bindTo(other2));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableValue#set                                                                                             *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableValue$set_ThrowForBound() {
        SimpleBoolProperty other = new SimpleBoolProperty(TestValues.BoolValue_L);
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.bindTo(other);
        assertThrows(IllegalStateException.class, () -> property.set(TestValues.BoolValue_H));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableValue#setValue                                                                                        *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableValue$setValue_ThrowForBound() {
        SimpleBoolProperty other = new SimpleBoolProperty(TestValues.BoolValue_L);
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.bindTo(other);
        assertThrows(IllegalStateException.class, () -> property.setValue(TestValues.BoolValue_H));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * Other (Overloads, etc.)
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test__Overloads$Bool_bindTo() {
        SimpleBoolProperty other1 = new SimpleBoolProperty(TestValues.BoolValue_L);
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.bindTo(other1, ignore -> TestValues.BoolValue_H);

        SimpleBoolProperty other2 = new SimpleBoolProperty(TestValues.BoolValue_L);
        assertThrows(IllegalStateException.class, () -> property.bindTo(other2, ignore -> TestValues.BoolValue_H));

        property.unbind();
        assertFalse(property.isBound());
    }

    @Test
    public void test__Overloads$Byte_bindTo() {
        SimpleByteProperty other1 = new SimpleByteProperty(TestValues.ByteValue_L);
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.bindTo(other1, ignore -> TestValues.BoolValue_H);

        SimpleByteProperty other2 = new SimpleByteProperty(TestValues.ByteValue_L);
        assertThrows(IllegalStateException.class, () -> property.bindTo(other2, ignore -> TestValues.BoolValue_H));

        property.unbind();
        assertFalse(property.isBound());
    }

    @Test
    public void test__Overloads$Short_bindTo() {
        SimpleShortProperty other1 = new SimpleShortProperty(TestValues.ShortValue_L);
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.bindTo(other1, ignore -> TestValues.BoolValue_H);

        SimpleShortProperty other2 = new SimpleShortProperty(TestValues.ShortValue_L);
        assertThrows(IllegalStateException.class, () -> property.bindTo(other2, ignore -> TestValues.BoolValue_H));

        property.unbind();
        assertFalse(property.isBound());
    }

    @Test
    public void test__Overloads$Int_bindTo() {
        SimpleIntProperty other1 = new SimpleIntProperty(TestValues.IntValue_L);
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.bindTo(other1, ignore -> TestValues.BoolValue_H);

        SimpleIntProperty other2 = new SimpleIntProperty(TestValues.IntValue_L);
        assertThrows(IllegalStateException.class, () -> property.bindTo(other2, ignore -> TestValues.BoolValue_H));

        property.unbind();
        assertFalse(property.isBound());
    }

    @Test
    public void test__Overloads$Long_bindTo() {
        SimpleLongProperty other1 = new SimpleLongProperty(TestValues.LongValue_L);
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.bindTo(other1, ignore -> TestValues.BoolValue_H);

        SimpleLongProperty other2 = new SimpleLongProperty(TestValues.LongValue_L);
        assertThrows(IllegalStateException.class, () -> property.bindTo(other2, ignore -> TestValues.BoolValue_H));

        property.unbind();
        assertFalse(property.isBound());
    }

    @Test
    public void test__Overloads$Float_bindTo() {
        SimpleFloatProperty other1 = new SimpleFloatProperty(TestValues.FloatValue_L);
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.bindTo(other1, ignore -> TestValues.BoolValue_H);

        SimpleFloatProperty other2 = new SimpleFloatProperty(TestValues.FloatValue_L);
        assertThrows(IllegalStateException.class, () -> property.bindTo(other2, ignore -> TestValues.BoolValue_H));

        property.unbind();
        assertFalse(property.isBound());
    }

    @Test
    public void test__Overloads$Double_bindTo() {
        SimpleDoubleProperty other1 = new SimpleDoubleProperty(TestValues.DoubleValue_L);
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.bindTo(other1, ignore -> TestValues.BoolValue_H);

        SimpleDoubleProperty other2 = new SimpleDoubleProperty(TestValues.DoubleValue_L);
        assertThrows(IllegalStateException.class, () -> property.bindTo(other2, ignore -> TestValues.BoolValue_H));

        property.unbind();
        assertFalse(property.isBound());
    }

    @Test
    public void test__Overloads$Object_bindTo() {
        SimpleObjectProperty<Object> other1 = new SimpleObjectProperty<>(TestValues.ObjectValue_L);
        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.bindTo(other1, ignore -> TestValues.BoolValue_H);

        SimpleObjectProperty<Object> other2 = new SimpleObjectProperty<>(TestValues.ObjectValue_L);
        assertThrows(IllegalStateException.class, () -> property.bindTo(other2, ignore -> TestValues.BoolValue_H));

        property.unbind();
        assertFalse(property.isBound());
    }

    // TODO reconsider all tests below

    @Test
    public void testChangeListenerBoxAttachDetach() {
        var property = new SimpleBoolProperty(TestValues.BoolValue_H);
        ChangeListener<Boolean> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        property.addBoxedListener(changeListener);
        assertTrue(property.removeBoxedListener(changeListener));
    }

    @Test
    public void testChangeListenerDuplicateBoxAttachDetach() {
        var property = new SimpleBoolProperty(TestValues.BoolValue_H);
        ChangeListener<Boolean> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        assertTrue(property.addBoxedListener(changeListener));
        assertFalse(property.addBoxedListener(changeListener));
        assertTrue(property.removeBoxedListener(changeListener));
        assertTrue(property.addBoxedListener(changeListener));
    }

    @Test
    public void testChangeListenerSetGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.addListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.BoolValue_L, oldValue);
            assertEquals(TestValues.BoolValue_H, newValue);
            assertEquals(TestValues.BoolValue_H, property.get());
        });

        property.set(TestValues.BoolValue_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnSet() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.BoolValue_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSetGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.addListener((observable -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.BoolValue_H, property.get());
        }));

        property.set(TestValues.BoolValue_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnSet() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.addListener(observable -> callCounter.getAndIncrement());

        property.set(TestValues.BoolValue_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        SimpleBoolProperty wrapper = new SimpleBoolProperty(TestValues.BoolValue_H);
        wrapper.addListener((observable, oldValue, newValue) -> {
            switch (callCounter.getAndIncrement()) {
                case 0 -> {
                    assertEquals(TestValues.BoolValue_H, oldValue);
                    assertEquals(TestValues.BoolValue_L, newValue);
                    assertEquals(TestValues.BoolValue_L, property.get());
                }
                case 1 -> {
                    assertEquals(TestValues.BoolValue_L, oldValue);
                    assertEquals(TestValues.BoolValue_H, newValue);
                    assertEquals(TestValues.BoolValue_H, property.get());
                }
                default -> throw new IllegalStateException();
            }
        });

        wrapper.bindTo(property);

        property.set(TestValues.BoolValue_H);
        assertEquals(2, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        SimpleBoolProperty wrapper = new SimpleBoolProperty(TestValues.BoolValue_L);
        wrapper.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        wrapper.bindTo(property);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        SimpleBoolProperty wrapper = new SimpleBoolProperty(TestValues.BoolValue_H);
        wrapper.addListener(observable -> {
            switch (callCounter.getAndIncrement()) {
                case 0 -> assertEquals(TestValues.BoolValue_L, property.get());
                case 1 -> assertEquals(TestValues.BoolValue_H, property.get());
                default -> throw new IllegalStateException();
            }
        });

        wrapper.bindTo(property);

        property.set(TestValues.BoolValue_H);
        assertEquals(2, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        SimpleBoolProperty wrapper = new SimpleBoolProperty(TestValues.BoolValue_L);
        wrapper.addListener(observable -> callCounter.getAndIncrement());

        wrapper.bindTo(property);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerBindingUpdatedGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        SimpleBoolProperty wrapper = new SimpleBoolProperty(TestValues.BoolValue_L);
        wrapper.bindTo(property);

        wrapper.addListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.BoolValue_L, oldValue);
            assertEquals(TestValues.BoolValue_H, newValue);
            assertEquals(TestValues.BoolValue_H, property.get());
        });

        property.set(TestValues.BoolValue_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnBindingUpdated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        SimpleBoolProperty wrapper = new SimpleBoolProperty(TestValues.BoolValue_L);
        wrapper.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.BoolValue_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerBindingUpdatedGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        SimpleBoolProperty wrapper = new SimpleBoolProperty(TestValues.BoolValue_L);
        wrapper.bindTo(property);

        wrapper.addListener(observable -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.BoolValue_H, property.get());
        });

        property.set(TestValues.BoolValue_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnBindingUpdated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        SimpleBoolProperty wrapper = new SimpleBoolProperty(TestValues.BoolValue_L);
        wrapper.addListener(observable -> callCounter.getAndIncrement());

        property.set(TestValues.BoolValue_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedChangeListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.addListener(new BoolChangeListener() {

            @Override
            public void onChanged(ObservableBoolValue observable, boolean oldValue, boolean newValue) {
                callCounter.getAndIncrement();
            }

            @Override
            public boolean isInvalid() {
                return true;
            }

        });
        property.set(TestValues.BoolValue_H);
        property.set(TestValues.BoolValue_L);

        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedInvalidationListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleBoolProperty property = new SimpleBoolProperty(TestValues.BoolValue_L);
        property.addListener(new InvalidationListener() {

            @Override
            public void onInvalidation(Observable observable) {
                callCounter.getAndIncrement();
            }

            @Override
            public boolean isInvalid() {
                return true;
            }

        });
        property.set(TestValues.BoolValue_H);
        property.set(TestValues.BoolValue_L);

        assertEquals(0, callCounter.get());
    }

}