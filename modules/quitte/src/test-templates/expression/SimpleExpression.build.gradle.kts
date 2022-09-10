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
val packageName = "com.osmerion.quitte.expression"

Type.values().forEach {
    val type = it
    val typeParams = if (type === Type.OBJECT) "<Object>" else ""
    val typeDiamond = if (type === Type.OBJECT) "<>" else ""
    val valAnno = if (type === Type.OBJECT) "@Nullable " else ""

    template("${packageName.replace('.', '/')}/Simple${type.abbrevName}ExpressionGeneratedTest", isTest = true) {
        """package $packageName;

import java.util.concurrent.atomic.AtomicInteger;
${if (type === Type.OBJECT) "\nimport javax.annotation.Nullable;\n" else ""}
import com.osmerion.quitte.*;
import com.osmerion.quitte.property.*;
import com.osmerion.quitte.value.*;
import com.osmerion.quitte.value.change.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
            
/**
 * Generated tests for {@link Simple${type.abbrevName}Expression}.
 *
 * @author  Leon Linhart
 */
public final class Simple${type.abbrevName}ExpressionGeneratedTest {

    @Test
    public void testInitialGetConsistency() {
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        Simple${type.abbrevName}Expression$typeParams expression = Simple${type.abbrevName}Expression.of(property, it -> it);

        assertEquals(TestValues.${type.abbrevName}Value_H, expression.get());
    }

    @Test
    public void testUpdateGetConsistency() {
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Expression$typeParams expression = Simple${type.abbrevName}Expression.of(property, it -> it);
        assertEquals(TestValues.${type.abbrevName}Value_L, expression.get());

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(TestValues.${type.abbrevName}Value_H, expression.get());
    }

    @Test
    public void testChangeListenerBoxAttachDetach() {
        var property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        var expression = Simple${type.abbrevName}Expression.of(property, it -> it);
        ChangeListener<${if (type !== Type.OBJECT) type.box else "Object"}> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        expression.addBoxedListener(changeListener);
        assertTrue(expression.removeBoxedListener(changeListener));
    }

    @Test
    public void testChangeListenerDuplicateBoxAttachDetach() {
        var property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        var expression = Simple${type.abbrevName}Expression.of(property, it -> it);
        ChangeListener<${if (type !== Type.OBJECT) type.box else "Object"}> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        assertTrue(expression.addBoxedListener(changeListener));
        assertFalse(expression.addBoxedListener(changeListener));
        assertTrue(expression.removeBoxedListener(changeListener));
        assertTrue(expression.addBoxedListener(changeListener));
    }

    @Test
    public void testChangeListenerUpdateGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Expression$typeParams expression = Simple${type.abbrevName}Expression.of(property, it -> it);
        expression.addListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.${type.abbrevName}Value_L, oldValue);
            assertEquals(TestValues.${type.abbrevName}Value_H, newValue);
            assertEquals(TestValues.${type.abbrevName}Value_H, expression.get());
        });

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnUpdate() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Expression$typeParams expression = Simple${type.abbrevName}Expression.of(property, it -> it);
        expression.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerUpdateGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Expression$typeParams expression = Simple${type.abbrevName}Expression.of(property, it -> it);
        expression.addInvalidationListener((observable -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.${type.abbrevName}Value_H, expression.get());
        }));

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnUpdate() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Expression$typeParams expression = Simple${type.abbrevName}Expression.of(property, it -> it);
        expression.addInvalidationListener(observable -> callCounter.getAndIncrement());

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedChangeListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Expression$typeParams expression = Simple${type.abbrevName}Expression.of(property, it -> it);
        expression.addListener(new ${type.abbrevName}ChangeListener$typeDiamond() {

            @Override
            public void onChanged(Observable${type.abbrevName}Value$typeParams observable, $valAnno${if (type === Type.OBJECT) "Object" else type.raw} oldValue, $valAnno${if (type === Type.OBJECT) "Object" else type.raw} newValue) {
                callCounter.getAndIncrement();
            }

            @Override
            public boolean isInvalid() {
                return true;
            }

        });
        property.set(TestValues.${type.abbrevName}Value_H);

        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedInvalidationListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Expression$typeParams expression = Simple${type.abbrevName}Expression.of(property, it -> it);
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
        property.set(TestValues.${type.abbrevName}Value_H);

        assertEquals(0, callCounter.get());
    }

}"""
    }
}