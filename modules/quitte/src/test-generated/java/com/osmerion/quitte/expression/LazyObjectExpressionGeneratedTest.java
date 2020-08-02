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
package com.osmerion.quitte.expression;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

import com.osmerion.quitte.*;
import com.osmerion.quitte.property.*;
import com.osmerion.quitte.value.*;
import com.osmerion.quitte.value.change.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
            
/**
 * Generated tests for {@link LazyObjectExpression}.
 *
 * @author  Leon Linhart
 */
public final class LazyObjectExpressionGeneratedTest {

    @Test
    public void testInitialGetConsistencyForInitializedSource() {
        var property = new LazyObjectProperty<>(TestValues.ObjectValue_H);
        var expression = LazyObjectExpression.of(property, it -> it);
        assertEquals(LazyValue.State.UNINITIALIZED, expression.getState());
        assertEquals(TestValues.ObjectValue_H, expression.get());
        assertEquals(LazyValue.State.INITIALIZED, expression.getState());
    }

    @Test
    public void testInitialGetConsistencyForUninitializedSource() {
        var property = new LazyObjectProperty<>(() ->TestValues.ObjectValue_H);
        var expression = LazyObjectExpression.of(property, it -> it);
        assertEquals(LazyValue.State.UNINITIALIZED, expression.getState());
        assertEquals(TestValues.ObjectValue_H, expression.get());
        assertEquals(LazyValue.State.INITIALIZED, expression.getState());
    }

    @Test
    public void testUpdateGetStateLifecycle() {
        var expressionInvalidatedCallCounter = new AtomicInteger(0);
        var stateChangedCallCounter = new AtomicInteger(0);
        var stateInvalidatedCallCounter = new AtomicInteger(0);

        var property = new LazyObjectProperty<>(TestValues.ObjectValue_H);
        var expression = LazyObjectExpression.of(property, it -> it);
        expression.addListener((observable -> expressionInvalidatedCallCounter.getAndIncrement()));

        var state = expression.stateProperty();
        state.addListener(((observable, oldValue, newValue) -> stateChangedCallCounter.getAndIncrement()));
        state.addListener(((observable) -> stateInvalidatedCallCounter.getAndIncrement()));

        assertEquals(LazyValue.State.UNINITIALIZED, expression.getState());

        property.set(TestValues.ObjectValue_L);
        assertEquals(LazyValue.State.UNINITIALIZED, expression.getState());
        assertEquals(0, expressionInvalidatedCallCounter.get());
        assertEquals(0, stateChangedCallCounter.get());
        assertEquals(0, stateInvalidatedCallCounter.get());

        expression.get();
        assertEquals(LazyValue.State.INITIALIZED, expression.getState());
        assertEquals(0, expressionInvalidatedCallCounter.get());
        assertEquals(1, stateChangedCallCounter.get());
        assertEquals(1, stateInvalidatedCallCounter.get());

        property.set(TestValues.ObjectValue_H);
        assertEquals(LazyValue.State.INVALID, expression.getState());
        assertEquals(1, expressionInvalidatedCallCounter.get());
        assertEquals(2, stateChangedCallCounter.get());
        assertEquals(2, stateInvalidatedCallCounter.get());

        property.set(TestValues.ObjectValue_H);
        assertEquals(LazyValue.State.INVALID, expression.getState());
        assertEquals(1, expressionInvalidatedCallCounter.get());
        assertEquals(2, stateChangedCallCounter.get());
        assertEquals(2, stateInvalidatedCallCounter.get());

        expression.get();
        assertEquals(LazyValue.State.VALID, expression.getState());
        assertEquals(1, expressionInvalidatedCallCounter.get());
        assertEquals(3, stateChangedCallCounter.get());
        assertEquals(3, stateInvalidatedCallCounter.get());
    }

    @Test
    public void testUpdateGetConsistency() {
        var property = new LazyObjectProperty<>(TestValues.ObjectValue_L);
        var expression = LazyObjectExpression.of(property, it -> it);
        assertEquals(TestValues.ObjectValue_L, expression.get());

        property.set(TestValues.ObjectValue_H);
        assertEquals(LazyValue.State.INVALID, expression.getState());
        assertEquals(TestValues.ObjectValue_H, expression.get());
        assertEquals(LazyValue.State.VALID, expression.getState());
    }

    @Test
    public void testChangeListenerBoxAttachDetach() {
        var property = new LazyObjectProperty<>(TestValues.ObjectValue_H);
        var expression = LazyObjectExpression.of(property, it -> it);
        ChangeListener<Object> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        expression.addBoxedListener(changeListener);
        assertTrue(expression.removeBoxedListener(changeListener));
    }

    @Test
    public void testChangeListenerDuplicateBoxAttachDetach() {
        var property = new LazyObjectProperty<>(TestValues.ObjectValue_H);
        var expression = LazyObjectExpression.of(property, it -> it);
        ChangeListener<Object> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        assertTrue(expression.addBoxedListener(changeListener));
        assertFalse(expression.addBoxedListener(changeListener));
        assertTrue(expression.removeBoxedListener(changeListener));
        assertTrue(expression.addBoxedListener(changeListener));
    }

    @Test
    public void testChangeListenerUpdateGetConsistency() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyObjectProperty<>(TestValues.ObjectValue_L);
        var expression = LazyObjectExpression.of(property, it -> it);
        expression.addListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(LazyValue.State.INITIALIZED, expression.getState());
            assertEquals(TestValues.ObjectValue_N, oldValue);
            assertEquals(TestValues.ObjectValue_H, newValue);
            assertEquals(TestValues.ObjectValue_H, expression.get());
        });

        property.set(TestValues.ObjectValue_H);
        assertEquals(0, callCounter.get());

        assertEquals(TestValues.ObjectValue_H, expression.get());
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnUpdate() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyObjectProperty<>(TestValues.ObjectValue_L);
        var expression = LazyObjectExpression.of(property, it -> it);
        expression.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.ObjectValue_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerUpdateGetConsistency() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyObjectProperty<>(TestValues.ObjectValue_L);
        var expression = LazyObjectExpression.of(property, it -> it);
        expression.addListener(observable -> {
            callCounter.getAndIncrement();
            assertEquals(LazyValue.State.INVALID, expression.getState());
            assertEquals(TestValues.ObjectValue_H, expression.get());
        });

        expression.get();
        assertEquals(0, callCounter.get());

        property.set(TestValues.ObjectValue_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidatedChangeListenerRemoval() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyObjectProperty<>(TestValues.ObjectValue_L);
        var expression = LazyObjectExpression.of(property, it -> it);
        expression.addListener(new ObjectChangeListener<>() {

            @Override
            public void onChanged(ObservableObjectValue<Object> observable, @Nullable Object oldValue, @Nullable Object newValue) {
                callCounter.getAndIncrement();
            }

            @Override
            public boolean isInvalid() {
                return true;
            }

        });
        property.set(TestValues.ObjectValue_H);
        assertEquals(TestValues.ObjectValue_H, expression.get());
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedInvalidationListenerRemoval() {
        var callCounter = new AtomicInteger(0);

        var property = new LazyObjectProperty<>(TestValues.ObjectValue_L);
        var expression = LazyObjectExpression.of(property, it -> it);
        expression.addListener(new InvalidationListener() {

            @Override
            public void onInvalidation(Observable observable) {
                callCounter.getAndIncrement();
            }

            @Override
            public boolean isInvalid() {
                return true;
            }

        });
        property.set(TestValues.ObjectValue_H);
        assertEquals(TestValues.ObjectValue_H, expression.get());
        assertEquals(0, callCounter.get());
    }

}