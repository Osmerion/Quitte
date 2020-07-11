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
package com.github.osmerion.quitte.property;

import java.util.concurrent.atomic.AtomicInteger;

import com.github.osmerion.quitte.*;
import com.github.osmerion.quitte.value.*;
import com.github.osmerion.quitte.value.change.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
            
/**
 * Generated tests for {@link SimpleFloatProperty}.
 *
 * @author  Leon Linhart
 */
public final class SimpleFloatPropertyGeneratedTest {

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#asReadOnlyProperty                                                                           *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void testReadableProperty$asReadOnlyProperty_Get() {
        SimpleFloatProperty other = new SimpleFloatProperty(TestValues.FloatValue_L);
        ReadableFloatProperty property = other.asReadOnlyProperty();
        assertEquals(TestValues.FloatValue_L, property.get());

        other.set(TestValues.FloatValue_H);
        assertEquals(TestValues.FloatValue_H, property.get());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#isBound                                                                                      *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ReadableProperty$isBound_Initial() {
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        assertFalse(property.isBound());
    }

    @Test
    public void test_ReadableProperty$isBound_BoundProperty() {
        SimpleFloatProperty other = new SimpleFloatProperty(TestValues.FloatValue_L);
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        assertFalse(property.isBound());

        property.bindTo(other);
        assertTrue(property.isBound());

        property.unbind();
        assertFalse(property.isBound());
    }

    @Test
    public void test_ReadableProperty$isBound_ReadOnlyProperty() {
        SimpleFloatProperty other = new SimpleFloatProperty(TestValues.FloatValue_L);
        ReadableFloatProperty property = other.asReadOnlyProperty();
        assertFalse(property.isWritable());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#isWritable                                                                                   *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ReadableProperty$isWritable_Initial() {
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        assertTrue(property.isWritable());
    }

    @Test
    public void test_ReadableProperty$isWritable_BoundProperty() {
        SimpleFloatProperty other = new SimpleFloatProperty(TestValues.FloatValue_L);
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        assertTrue(property.isWritable());

        property.bindTo(other);
        assertFalse(property.isWritable());

        property.unbind();
        assertTrue(property.isWritable());
    }

    @Test
    public void test_ReadableProperty$isWritable_ReadOnlyProperty() {
        SimpleFloatProperty other = new SimpleFloatProperty(TestValues.FloatValue_L);
        ReadableFloatProperty property = other.asReadOnlyProperty();
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
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_H);
        assertEquals(TestValues.FloatValue_H, property.get());
    }

    @Test
    public void test_ObservableValue$get_SetGet() {
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        assertEquals(TestValues.FloatValue_L, property.get());

        property.set(TestValues.FloatValue_H);
        assertEquals(TestValues.FloatValue_H, property.get());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ObservableValue#getValue                                                                                      *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ObservableValue$getValue_Initial() {
        SimpleIntProperty property = new SimpleIntProperty(TestValues.IntValue_H);
        assertEquals(TestValues.IntValue_H, property.getValue());
    }

    @Test
    public void test_ObservableValue$getValue_SetGet() {
        SimpleIntProperty property = new SimpleIntProperty(TestValues.IntValue_L);
        assertEquals(TestValues.IntValue_L, property.getValue());

        property.set(TestValues.IntValue_H);
        assertEquals(TestValues.IntValue_H, property.getValue());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableProperty#bindTo                                                                                       *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableProperty$bindTo_ThrowForBound() {
        SimpleFloatProperty other1 = new SimpleFloatProperty(TestValues.FloatValue_L);
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        property.bindTo(other1);

        SimpleFloatProperty other2 = new SimpleFloatProperty(TestValues.FloatValue_L);
        assertThrows(IllegalStateException.class, () -> property.bindTo(other2));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableValue#set                                                                                             *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableValue$set_ThrowForBound() {
        SimpleFloatProperty other = new SimpleFloatProperty(TestValues.FloatValue_L);
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        property.bindTo(other);
        assertThrows(IllegalStateException.class, () -> property.set(TestValues.FloatValue_H));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableValue#setValue                                                                                        *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableValue$setValue_ThrowForBound() {
        SimpleFloatProperty other = new SimpleFloatProperty(TestValues.FloatValue_L);
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        property.bindTo(other);
        assertThrows(IllegalStateException.class, () -> property.set(TestValues.FloatValue_H));
    }

    // TODO reconsider all tests below

    @Test
    public void testChangeListenerBoxAttachDetach() {
        var property = new SimpleFloatProperty(TestValues.FloatValue_H);
        ChangeListener<Float> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        property.addBoxedListener(changeListener);
        assertTrue(property.removeBoxedListener(changeListener));
    }

    @Test
    public void testChangeListenerDuplicateBoxAttachDetach() {
        var property = new SimpleFloatProperty(TestValues.FloatValue_H);
        ChangeListener<Float> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        assertTrue(property.addBoxedListener(changeListener));
        assertFalse(property.addBoxedListener(changeListener));
        assertTrue(property.removeBoxedListener(changeListener));
        assertTrue(property.addBoxedListener(changeListener));
    }

    @Test
    public void testChangeListenerSetGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        property.addListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.FloatValue_L, oldValue);
            assertEquals(TestValues.FloatValue_H, newValue);
            assertEquals(TestValues.FloatValue_H, property.get());
        });

        property.set(TestValues.FloatValue_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnSet() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        property.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.FloatValue_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSetGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        property.addListener((observable -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.FloatValue_H, property.get());
        }));

        property.set(TestValues.FloatValue_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnSet() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        property.addListener(observable -> callCounter.getAndIncrement());

        property.set(TestValues.FloatValue_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        SimpleFloatProperty wrapper = new SimpleFloatProperty(TestValues.FloatValue_H);
        wrapper.addListener((observable, oldValue, newValue) -> {
            switch (callCounter.getAndIncrement()) {
                case 0 -> {
                    assertEquals(TestValues.FloatValue_H, oldValue);
                    assertEquals(TestValues.FloatValue_L, newValue);
                    assertEquals(TestValues.FloatValue_L, property.get());
                }
                case 1 -> {
                    assertEquals(TestValues.FloatValue_L, oldValue);
                    assertEquals(TestValues.FloatValue_H, newValue);
                    assertEquals(TestValues.FloatValue_H, property.get());
                }
                default -> throw new IllegalStateException();
            }
        });

        wrapper.bindTo(property);

        property.set(TestValues.FloatValue_H);
        assertEquals(2, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        SimpleFloatProperty wrapper = new SimpleFloatProperty(TestValues.FloatValue_L);
        wrapper.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        wrapper.bindTo(property);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        SimpleFloatProperty wrapper = new SimpleFloatProperty(TestValues.FloatValue_H);
        wrapper.addListener(observable -> {
            switch (callCounter.getAndIncrement()) {
                case 0 -> assertEquals(TestValues.FloatValue_L, property.get());
                case 1 -> assertEquals(TestValues.FloatValue_H, property.get());
                default -> throw new IllegalStateException();
            }
        });

        wrapper.bindTo(property);

        property.set(TestValues.FloatValue_H);
        assertEquals(2, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        SimpleFloatProperty wrapper = new SimpleFloatProperty(TestValues.FloatValue_L);
        wrapper.addListener(observable -> callCounter.getAndIncrement());

        wrapper.bindTo(property);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerBindingUpdatedGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        SimpleFloatProperty wrapper = new SimpleFloatProperty(TestValues.FloatValue_L);
        wrapper.bindTo(property);

        wrapper.addListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.FloatValue_L, oldValue);
            assertEquals(TestValues.FloatValue_H, newValue);
            assertEquals(TestValues.FloatValue_H, property.get());
        });

        property.set(TestValues.FloatValue_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnBindingUpdated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        SimpleFloatProperty wrapper = new SimpleFloatProperty(TestValues.FloatValue_L);
        wrapper.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.FloatValue_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerBindingUpdatedGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        SimpleFloatProperty wrapper = new SimpleFloatProperty(TestValues.FloatValue_L);
        wrapper.bindTo(property);

        wrapper.addListener(observable -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.FloatValue_H, property.get());
        });

        property.set(TestValues.FloatValue_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnBindingUpdated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        SimpleFloatProperty wrapper = new SimpleFloatProperty(TestValues.FloatValue_L);
        wrapper.addListener(observable -> callCounter.getAndIncrement());

        property.set(TestValues.FloatValue_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedChangeListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
        property.addListener(new FloatChangeListener() {

            @Override
            public void onChanged(ObservableFloatValue observable, float oldValue, float newValue) {
                callCounter.getAndIncrement();
            }

            @Override
            public boolean isInvalid() {
                return true;
            }

        });
        property.set(TestValues.FloatValue_H);
        property.set(TestValues.FloatValue_L);

        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedInvalidationListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_L);
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
        property.set(TestValues.FloatValue_H);
        property.set(TestValues.FloatValue_L);

        assertEquals(0, callCounter.get());
    }

}