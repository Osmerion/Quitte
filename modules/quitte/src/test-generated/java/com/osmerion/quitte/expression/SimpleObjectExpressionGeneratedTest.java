/*
 * Copyright (c) 2018-2022 Leon Linhart,
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
 * Generated tests for {@link SimpleObjectExpression}.
 *
 * @author  Leon Linhart
 */
public final class SimpleObjectExpressionGeneratedTest {

    @Test
    public void testInitialGetConsistency() {
        SimpleObjectProperty<Object> property = new SimpleObjectProperty<>(TestValues.ObjectValue_H);
        SimpleObjectExpression<Object> expression = SimpleObjectExpression.of(property, it -> it);

        assertEquals(TestValues.ObjectValue_H, expression.get());
    }

    @Test
    public void testUpdateGetConsistency() {
        SimpleObjectProperty<Object> property = new SimpleObjectProperty<>(TestValues.ObjectValue_L);
        SimpleObjectExpression<Object> expression = SimpleObjectExpression.of(property, it -> it);
        assertEquals(TestValues.ObjectValue_L, expression.get());

        property.set(TestValues.ObjectValue_H);
        assertEquals(TestValues.ObjectValue_H, expression.get());
    }

    @Test
    public void testChangeListenerBoxAttachDetach() {
        var property = new SimpleObjectProperty<>(TestValues.ObjectValue_H);
        var expression = SimpleObjectExpression.of(property, it -> it);
        ChangeListener<Object> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        expression.addBoxedChangeListener(changeListener);
        assertTrue(expression.removeBoxedChangeListener(changeListener));
    }

    @Test
    public void testChangeListenerDuplicateBoxAttachDetach() {
        var property = new SimpleObjectProperty<>(TestValues.ObjectValue_H);
        var expression = SimpleObjectExpression.of(property, it -> it);
        ChangeListener<Object> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        assertTrue(expression.addBoxedChangeListener(changeListener));
        assertFalse(expression.addBoxedChangeListener(changeListener));
        assertTrue(expression.removeBoxedChangeListener(changeListener));
        assertTrue(expression.addBoxedChangeListener(changeListener));
    }

    @Test
    public void testChangeListenerUpdateGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleObjectProperty<Object> property = new SimpleObjectProperty<>(TestValues.ObjectValue_L);
        SimpleObjectExpression<Object> expression = SimpleObjectExpression.of(property, it -> it);
        expression.addChangeListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.ObjectValue_L, oldValue);
            assertEquals(TestValues.ObjectValue_H, newValue);
            assertEquals(TestValues.ObjectValue_H, expression.get());
        });

        property.set(TestValues.ObjectValue_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnUpdate() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleObjectProperty<Object> property = new SimpleObjectProperty<>(TestValues.ObjectValue_L);
        SimpleObjectExpression<Object> expression = SimpleObjectExpression.of(property, it -> it);
        expression.addChangeListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.ObjectValue_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerUpdateGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleObjectProperty<Object> property = new SimpleObjectProperty<>(TestValues.ObjectValue_L);
        SimpleObjectExpression<Object> expression = SimpleObjectExpression.of(property, it -> it);
        expression.addInvalidationListener((observable -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.ObjectValue_H, expression.get());
        }));

        property.set(TestValues.ObjectValue_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnUpdate() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleObjectProperty<Object> property = new SimpleObjectProperty<>(TestValues.ObjectValue_L);
        SimpleObjectExpression<Object> expression = SimpleObjectExpression.of(property, it -> it);
        expression.addInvalidationListener(observable -> callCounter.getAndIncrement());

        property.set(TestValues.ObjectValue_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedChangeListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleObjectProperty<Object> property = new SimpleObjectProperty<>(TestValues.ObjectValue_L);
        SimpleObjectExpression<Object> expression = SimpleObjectExpression.of(property, it -> it);
        expression.addChangeListener(new ObjectChangeListener<>() {

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

        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedInvalidationListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleObjectProperty<Object> property = new SimpleObjectProperty<>(TestValues.ObjectValue_L);
        SimpleObjectExpression<Object> expression = SimpleObjectExpression.of(property, it -> it);
        expression.addInvalidationListener(new InvalidationListener() {

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

        assertEquals(0, callCounter.get());
    }

}