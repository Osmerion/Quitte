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
package com.github.osmerion.quitte.expression;

import java.util.concurrent.atomic.AtomicInteger;

import com.github.osmerion.quitte.*;
import com.github.osmerion.quitte.property.*;
import com.github.osmerion.quitte.value.*;
import com.github.osmerion.quitte.value.change.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
            
/**
 * Generated tests for {@link SimpleShortExpression}.
 *
 * @author  Leon Linhart
 */
public final class SimpleShortExpressionGeneratedTest {

    @Test
    public void testInitialGetConsistency() {
        SimpleShortProperty property = new SimpleShortProperty(TestValues.ShortValue_2);
        SimpleShortExpression expression = SimpleShortExpression.of(property, it -> it);
        
        assertEquals(TestValues.ShortValue_2, expression.get());
    }

    @Test
    public void testUpdateGetConsistency() {
        SimpleShortProperty property = new SimpleShortProperty(TestValues.ShortValue_1);
        SimpleShortExpression expression = SimpleShortExpression.of(property, it -> it);
        assertEquals(TestValues.ShortValue_1, expression.get());

        property.set(TestValues.ShortValue_2);
        assertEquals(TestValues.ShortValue_2, expression.get());
    }

    @Test
    public void testChangeListenerUpdateGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleShortProperty property = new SimpleShortProperty(TestValues.ShortValue_1);
        SimpleShortExpression expression = SimpleShortExpression.of(property, it -> it);
        expression.addListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.ShortValue_1, oldValue);
            assertEquals(TestValues.ShortValue_2, newValue);
            assertEquals(TestValues.ShortValue_2, expression.get());
        });

        property.set(TestValues.ShortValue_2);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnUpdate() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleShortProperty property = new SimpleShortProperty(TestValues.ShortValue_1);
        SimpleShortExpression expression = SimpleShortExpression.of(property, it -> it);
        expression.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.ShortValue_1);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerUpdateGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleShortProperty property = new SimpleShortProperty(TestValues.ShortValue_1);
        SimpleShortExpression expression = SimpleShortExpression.of(property, it -> it);
        expression.addListener((observable -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.ShortValue_2, expression.get());
        }));

        property.set(TestValues.ShortValue_2);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnUpdate() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleShortProperty property = new SimpleShortProperty(TestValues.ShortValue_1);
        SimpleShortExpression expression = SimpleShortExpression.of(property, it -> it);
        expression.addListener(observable -> callCounter.getAndIncrement());

        property.set(TestValues.ShortValue_1);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedChangeListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleShortProperty property = new SimpleShortProperty(TestValues.ShortValue_1);
        SimpleShortExpression expression = SimpleShortExpression.of(property, it -> it);
        expression.addListener(new ShortChangeListener() {

            @Override
            public void onChanged(ObservableShortValue observable, short oldValue, short newValue) {
                callCounter.getAndIncrement();
            }

            @Override
            public boolean isInvalid() {
                return true;
            }

        });
        property.set(TestValues.ShortValue_2);
        property.set(TestValues.ShortValue_1);

        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidatedInvalidationListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        SimpleShortProperty property = new SimpleShortProperty(TestValues.ShortValue_1);
        SimpleShortExpression expression = SimpleShortExpression.of(property, it -> it);
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
        property.set(TestValues.ShortValue_2);
        property.set(TestValues.ShortValue_1);

        assertEquals(1, callCounter.get());
    }

}