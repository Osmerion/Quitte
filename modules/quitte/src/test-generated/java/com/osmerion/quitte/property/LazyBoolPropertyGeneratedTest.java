/*
 * Copyright (c) 2018-2023 Leon Linhart,
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
 * Generated tests for {@link LazyBoolProperty}.
 *
 * @author  Leon Linhart
 */
public final class LazyBoolPropertyGeneratedTest {

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#asReadOnlyProperty                                                                           *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void testReadableProperty$asReadOnlyProperty_NewInstanceAvoidance() {
        LazyBoolProperty other = new LazyBoolProperty(TestValues.BoolValue_L);
        ReadableBoolProperty property = other.asReadOnlyProperty();
        assertSame(property, property.asReadOnlyProperty());
    }

    @Test
    public void testReadableProperty$asReadOnlyProperty_Get() {
        LazyBoolProperty other = new LazyBoolProperty(TestValues.BoolValue_L);
        ReadableBoolProperty property = other.asReadOnlyProperty();
        assertEquals(TestValues.BoolValue_L, property.get());

        other.set(TestValues.BoolValue_H);
        assertEquals(TestValues.BoolValue_H, property.get());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#isBound                                                                                      *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ReadableProperty$isBound_Initial() {
        LazyBoolProperty property = new LazyBoolProperty(TestValues.BoolValue_L);
        assertFalse(property.isBound());
    }

    @Test
    public void test_ReadableProperty$isBound_BoundProperty() {
        LazyBoolProperty other = new LazyBoolProperty(TestValues.BoolValue_L);
        LazyBoolProperty property = new LazyBoolProperty(TestValues.BoolValue_L);
        assertFalse(property.isBound());

        property.bindTo(other);
        assertTrue(property.isBound());

        property.unbind();
        assertFalse(property.isBound());
    }

    @Test
    public void test_ReadableProperty$isBound_ReadOnlyProperty() {
        LazyBoolProperty other = new LazyBoolProperty(TestValues.BoolValue_L);
        ReadableBoolProperty property = other.asReadOnlyProperty();
        assertFalse(property.isWritable());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#isWritable                                                                                   *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ReadableProperty$isWritable_Initial() {
        LazyBoolProperty property = new LazyBoolProperty(TestValues.BoolValue_L);
        assertTrue(property.isWritable());
    }

    @Test
    public void test_ReadableProperty$isWritable_BoundProperty() {
        LazyBoolProperty other = new LazyBoolProperty(TestValues.BoolValue_L);
        LazyBoolProperty property = new LazyBoolProperty(TestValues.BoolValue_L);
        assertTrue(property.isWritable());

        property.bindTo(other);
        assertFalse(property.isWritable());

        property.unbind();
        assertTrue(property.isWritable());
    }

    @Test
    public void test_ReadableProperty$isWritable_ReadOnlyProperty() {
        LazyBoolProperty other = new LazyBoolProperty(TestValues.BoolValue_L);
        ReadableBoolProperty property = other.asReadOnlyProperty();
        assertFalse(property.isWritable());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ObservableValue#addBoxedChangeListener                                                                              *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    // TODO come up with proper tests

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ObservableValue#get                                                                                      *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ObservableValue$get_Initial() {
        LazyBoolProperty property = new LazyBoolProperty(TestValues.BoolValue_H);
        assertEquals(TestValues.BoolValue_H, property.get());
    }

    @Test
    public void test_ObservableValue$get_SetGet() {
        LazyBoolProperty property = new LazyBoolProperty(TestValues.BoolValue_L);
        assertEquals(TestValues.BoolValue_L, property.get());

        property.set(TestValues.BoolValue_H);
        assertEquals(TestValues.BoolValue_H, property.get());
    }

    @Test
    public void test_ObservableValue$get_SetDeferredGet() {
        LazyBoolProperty property = new LazyBoolProperty(TestValues.BoolValue_L);
        assertEquals(TestValues.BoolValue_L, property.getValue());

        property.set(() -> TestValues.BoolValue_H);
        assertEquals(TestValues.BoolValue_H, property.getValue());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ObservableValue#getValue                                                                                      *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ObservableValue$getValue_Initial() {
        LazyBoolProperty property = new LazyBoolProperty(TestValues.BoolValue_H);
        assertEquals(TestValues.BoolValue_H, property.getValue());
    }

    @Test
    public void test_ObservableValue$getValue_SetGet() {
        LazyBoolProperty property = new LazyBoolProperty(TestValues.BoolValue_L);
        assertEquals(TestValues.BoolValue_L, property.getValue());

        property.set(TestValues.BoolValue_H);
        assertEquals(TestValues.BoolValue_H, property.getValue());
    }

    @Test
    public void test_ObservableValue$getValue_SetDeferredGet() {
        LazyBoolProperty property = new LazyBoolProperty(TestValues.BoolValue_L);
        assertEquals(TestValues.BoolValue_L, property.getValue());

        property.set(() -> TestValues.BoolValue_H);
        assertEquals(TestValues.BoolValue_H, property.getValue());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableProperty#bindTo                                                                                       *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableProperty$bindTo_ThrowForBound() {
        LazyBoolProperty other1 = new LazyBoolProperty(TestValues.BoolValue_L);
        LazyBoolProperty property = new LazyBoolProperty(TestValues.BoolValue_L);
        property.bindTo(other1);

        LazyBoolProperty other2 = new LazyBoolProperty(TestValues.BoolValue_L);
        assertThrows(IllegalStateException.class, () -> property.bindTo(other2));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableValue#set                                                                                             *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableValue$set_ThrowForBound() {
        LazyBoolProperty other = new LazyBoolProperty(TestValues.BoolValue_L);
        LazyBoolProperty property = new LazyBoolProperty(TestValues.BoolValue_L);
        property.bindTo(other);
        assertThrows(IllegalStateException.class, () -> property.set(TestValues.BoolValue_H));
    }

    @Test
    public void test_WritableValue$setDeferred_ThrowForBound() {
        LazyBoolProperty other = new LazyBoolProperty(TestValues.BoolValue_L);
        LazyBoolProperty property = new LazyBoolProperty(TestValues.BoolValue_L);
        property.bindTo(other);
        assertThrows(IllegalStateException.class, () -> property.set(() -> TestValues.BoolValue_H));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableValue#setValue                                                                                        *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableValue$setValue_ThrowForBound() {
        LazyBoolProperty other = new LazyBoolProperty(TestValues.BoolValue_L);
        LazyBoolProperty property = new LazyBoolProperty(TestValues.BoolValue_L);
        property.bindTo(other);
        assertThrows(IllegalStateException.class, () -> property.set(TestValues.BoolValue_H));
    }

    // TODO reconsider all tests below

    @Test
    public void testInitialGetConsistencyForPrimaryCtor() {
        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
        assertEquals(TestValues.BoolValue_H, property.get());
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
    }

    @Test
    public void testInitialGetConsistencyForLazyInitialization() {
        var property = new LazyBoolProperty(() -> TestValues.BoolValue_H);
        assertEquals(LazyValue.State.UNINITIALIZED, property.getState());
        assertEquals(TestValues.BoolValue_H, property.get());
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
    }

    @Test
    public void testSetGetStateLifecycleForPrimaryCtor() {
        var propertyInvalidatedCallCounter = new AtomicInteger(0);
        var stateChangedCallCounter = new AtomicInteger(0);
        var stateInvalidatedCallCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        property.addInvalidationListener(observable -> propertyInvalidatedCallCounter.getAndIncrement());

        var state = property.stateProperty();
        state.addChangeListener((observable, oldValue, newValue) -> stateChangedCallCounter.getAndIncrement());
        state.addInvalidationListener(observable -> stateInvalidatedCallCounter.getAndIncrement());

        assertEquals(LazyValue.State.INITIALIZED, property.getState());

        property.set(TestValues.BoolValue_L);
        assertEquals(LazyValue.State.INVALID, property.getState());
        assertEquals(1, propertyInvalidatedCallCounter.get());
        assertEquals(1, stateChangedCallCounter.get());
        assertEquals(1, stateInvalidatedCallCounter.get());

        property.get();
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(1, propertyInvalidatedCallCounter.get());
        assertEquals(2, stateChangedCallCounter.get());
        assertEquals(2, stateInvalidatedCallCounter.get());

        property.set(TestValues.BoolValue_H);
        assertEquals(LazyValue.State.INVALID, property.getState());
        assertEquals(2, propertyInvalidatedCallCounter.get());
        assertEquals(3, stateChangedCallCounter.get());
        assertEquals(3, stateInvalidatedCallCounter.get());

        property.set(TestValues.BoolValue_H);
        assertEquals(LazyValue.State.INVALID, property.getState());
        assertEquals(2, propertyInvalidatedCallCounter.get());
        assertEquals(3, stateChangedCallCounter.get());
        assertEquals(3, stateInvalidatedCallCounter.get());

        property.get();
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(2, propertyInvalidatedCallCounter.get());
        assertEquals(4, stateChangedCallCounter.get());
        assertEquals(4, stateInvalidatedCallCounter.get());
    }

    @Test
    public void testSetGetStateLifecycleForLazyInitialization() {
        var propertyInvalidatedCallCounter = new AtomicInteger(0);
        var stateChangedCallCounter = new AtomicInteger(0);
        var stateInvalidatedCallCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(() -> TestValues.BoolValue_H);
        property.addInvalidationListener(observable -> propertyInvalidatedCallCounter.getAndIncrement());

        var state = property.stateProperty();
        state.addChangeListener((observable, oldValue, newValue) -> stateChangedCallCounter.getAndIncrement());
        state.addInvalidationListener(observable -> stateInvalidatedCallCounter.getAndIncrement());

        assertEquals(LazyValue.State.UNINITIALIZED, property.getState());

        property.set(TestValues.BoolValue_L);
        assertEquals(LazyValue.State.UNINITIALIZED, property.getState());
        assertEquals(0, propertyInvalidatedCallCounter.get());
        assertEquals(0, stateChangedCallCounter.get());
        assertEquals(0, stateInvalidatedCallCounter.get());

        property.get();
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
        assertEquals(0, propertyInvalidatedCallCounter.get());
        assertEquals(1, stateChangedCallCounter.get());
        assertEquals(1, stateInvalidatedCallCounter.get());

        property.set(TestValues.BoolValue_H);
        assertEquals(LazyValue.State.INVALID, property.getState());
        assertEquals(1, propertyInvalidatedCallCounter.get());
        assertEquals(2, stateChangedCallCounter.get());
        assertEquals(2, stateInvalidatedCallCounter.get());

        property.set(TestValues.BoolValue_H);
        assertEquals(LazyValue.State.INVALID, property.getState());
        assertEquals(1, propertyInvalidatedCallCounter.get());
        assertEquals(2, stateChangedCallCounter.get());
        assertEquals(2, stateInvalidatedCallCounter.get());

        property.get();
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(1, propertyInvalidatedCallCounter.get());
        assertEquals(3, stateChangedCallCounter.get());
        assertEquals(3, stateInvalidatedCallCounter.get());
    }

    @Test
    public void testChangeListenerAddRemovedWithBox() {
        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        ChangeListener<Boolean> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");
        
        property.addBoxedChangeListener(changeListener);
        assertTrue(property.removeBoxedChangeListener(changeListener));
    }

    @Test
    public void testChangeListenerBoxAttachDetach() {
        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        ChangeListener<Boolean> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        property.addBoxedChangeListener(changeListener);
        assertTrue(property.removeBoxedChangeListener(changeListener));
    }

    @Test
    public void testChangeListenerDuplicateBoxAttachDetach() {
        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        ChangeListener<Boolean> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        assertTrue(property.addBoxedChangeListener(changeListener));
        assertFalse(property.addBoxedChangeListener(changeListener));
        assertTrue(property.removeBoxedChangeListener(changeListener));
        assertTrue(property.addBoxedChangeListener(changeListener));
    }

    @Test
    public void testChangeListenerSetGetConsistencyForPrimaryCtor() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        property.addChangeListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(LazyValue.State.VALID, property.getState());
            assertEquals(TestValues.BoolValue_H, oldValue);
            assertEquals(TestValues.BoolValue_L, newValue);
            assertEquals(TestValues.BoolValue_L, property.get());
        });

        property.set(TestValues.BoolValue_L);
        assertEquals(0, callCounter.get());

        assertEquals(TestValues.BoolValue_L, property.get());
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSetGetConsistencyForLazyInitialization() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(() -> TestValues.BoolValue_H);
        property.addChangeListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(LazyValue.State.INITIALIZED, property.getState());
            assertEquals(TestValues.BoolValue_N, oldValue);
            assertEquals(TestValues.BoolValue_L, newValue);
            assertEquals(TestValues.BoolValue_L, property.get());
        });

        property.set(TestValues.BoolValue_L);
        assertEquals(0, callCounter.get());

        assertEquals(TestValues.BoolValue_L, property.get());
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerDeferredOnSetForPrimaryCtor() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        property.addChangeListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.BoolValue_H);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerDeferredOnSetForLazyInitialization() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(() -> TestValues.BoolValue_H);
        property.addChangeListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.BoolValue_H);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnSetGetForPrimaryCtor() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        property.addChangeListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());
        assertEquals(LazyValue.State.INITIALIZED, property.getState());

        property.set(TestValues.BoolValue_H);
        assertEquals(TestValues.BoolValue_H, property.get());
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerInitForLazyInitialization() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(() -> TestValues.BoolValue_H);
        property.addChangeListener((observable, oldValue, newValue) -> {
            callCounter.getAndIncrement();
            assertEquals(1, callCounter.get());
            assertEquals(LazyValue.State.INITIALIZED, property.getState());
            assertEquals(TestValues.BoolValue_N, oldValue);
            assertEquals(TestValues.BoolValue_H, newValue);
        });

        assertEquals(TestValues.BoolValue_H, property.get());
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerOnSetForPrimaryCtor() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        property.addInvalidationListener(observable -> {
            callCounter.getAndIncrement();
            assertEquals(LazyValue.State.INVALID, property.getState());
            assertEquals(TestValues.BoolValue_L, property.get());
        });

        property.set(TestValues.BoolValue_L);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnSetForLazyInitialization() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(() -> TestValues.BoolValue_H);
        property.addChangeListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.BoolValue_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedChangeListenerRemoval() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        property.addChangeListener(new BoolChangeListener() {

            @Override
            public void onChanged(ObservableBoolValue observable, boolean oldValue, boolean newValue) {
                callCounter.getAndIncrement();
            }

            @Override
            public boolean isInvalid() {
                return true;
            }

        });

        property.set(TestValues.BoolValue_L);
        assertEquals(TestValues.BoolValue_L, property.get());
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedInvalidationListenerRemoval() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        property.addInvalidationListener(new InvalidationListener() {

            @Override
            public void onInvalidation(Observable observable) {
                callCounter.getAndIncrement();
            }

            @Override
            public boolean isInvalid() {
                return true;
            }

        });

        property.set(TestValues.BoolValue_L);
        assertEquals(TestValues.BoolValue_L, property.get());
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerDeferredOnBindingCreated() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        var wrapper = new LazyBoolProperty(TestValues.BoolValue_H);
        wrapper.addChangeListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        wrapper.bindTo(property);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerOnGetForBindingWithSameValue() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        var wrapper = new LazyBoolProperty(TestValues.BoolValue_H);
        wrapper.addChangeListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        wrapper.bindTo(property);
        assertEquals(TestValues.BoolValue_H, property.get());
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerOnBindingCreated() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        var wrapper = new LazyBoolProperty(TestValues.BoolValue_L);
        wrapper.addInvalidationListener(observable -> {
            switch (callCounter.getAndIncrement()) {
                case 0 -> assertEquals(TestValues.BoolValue_H, property.get());
                case 1 -> assertEquals(TestValues.BoolValue_L, property.get());
                default -> throw new IllegalStateException();
            }
        });

        wrapper.bindTo(property);
        assertEquals(1, callCounter.get());
        assertEquals(TestValues.BoolValue_H, wrapper.get());

        property.set(TestValues.BoolValue_L);
        assertEquals(2, callCounter.get());
    }

    @Test
    public void testChangeListenerBindingUpdatedGetConsistency() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        var wrapper = new LazyBoolProperty(TestValues.BoolValue_H);
        wrapper.bindTo(property);

        wrapper.addChangeListener((observable, oldValue, newValue) -> {
            callCounter.getAndIncrement();
            assertEquals(TestValues.BoolValue_H, oldValue);
            assertEquals(TestValues.BoolValue_L, newValue);
            assertEquals(TestValues.BoolValue_L, property.get());
        });

        property.set(TestValues.BoolValue_L);
        assertEquals(TestValues.BoolValue_L, wrapper.get());
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerDeferredOnBindingUpdated() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        var wrapper = new LazyBoolProperty(TestValues.BoolValue_H);
        wrapper.addChangeListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.BoolValue_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerBindingUpdatedGetConsistency() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(() -> TestValues.BoolValue_H);
        var wrapper = new LazyBoolProperty(TestValues.BoolValue_H);
        wrapper.bindTo(property);

        wrapper.addInvalidationListener(observable -> callCounter.getAndIncrement());

        assertEquals(LazyValue.State.UNINITIALIZED, property.getState());
        assertEquals(LazyValue.State.INVALID, wrapper.getState());
        
        assertEquals(TestValues.BoolValue_H, property.get());
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
        assertEquals(LazyValue.State.INVALID, wrapper.getState());

        assertEquals(TestValues.BoolValue_H, wrapper.get());
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
        assertEquals(LazyValue.State.VALID, wrapper.getState());

        property.set(TestValues.BoolValue_L);
        assertEquals(1, callCounter.get());

        assertEquals(TestValues.BoolValue_L, property.get());
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(LazyValue.State.INVALID, wrapper.getState());

        assertEquals(TestValues.BoolValue_L, wrapper.get());
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(LazyValue.State.VALID, wrapper.getState());
    }

    @Test
    public void testInvalidationListenerDeferredOnBindingUpdated() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyBoolProperty(TestValues.BoolValue_H);
        var wrapper = new LazyBoolProperty(TestValues.BoolValue_H);
        wrapper.addInvalidationListener(observable -> callCounter.getAndIncrement());

        property.set(TestValues.BoolValue_H);
        assertEquals(0, callCounter.get());
    }

}