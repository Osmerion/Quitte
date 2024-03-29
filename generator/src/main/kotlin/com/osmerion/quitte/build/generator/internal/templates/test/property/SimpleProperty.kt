/*
 * Copyright (c) 2018-2023 Leon Linhart,
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
package com.osmerion.quitte.build.generator.internal.templates.test.property

import com.osmerion.quitte.build.generator.internal.Template
import com.osmerion.quitte.build.generator.internal.TemplateProvider
import com.osmerion.quitte.build.generator.internal.Type

object SimpleProperty : TemplateProvider {

    override fun provideTemplates(): List<Template> = Type.values().map { type ->
        val typeParams = if (type === Type.OBJECT) "<Object>" else ""
        val typeDiamond = if (type === Type.OBJECT) "<>" else ""
        val valAnno = if (type === Type.OBJECT) "@Nullable " else ""

        Template(PACKAGE_NAME, "Simple${type.abbrevName}PropertyGeneratedTest") {
            """
package $PACKAGE_NAME;

import java.util.concurrent.atomic.AtomicInteger;
${if (type === Type.OBJECT) "\nimport javax.annotation.Nullable;\n" else ""}
import com.osmerion.quitte.*;
import com.osmerion.quitte.value.*;
import com.osmerion.quitte.value.change.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
            
/**
 * Generated tests for {@link Simple${type.abbrevName}Property}.
 *
 * @author  Leon Linhart
 */
public final class Simple${type.abbrevName}PropertyGeneratedTest {

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#asReadOnlyProperty                                                                           *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void testReadableProperty${'$'}asReadOnlyProperty_NewInstanceAvoidance() {
        Simple${type.abbrevName}Property$typeParams other = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Readable${type.abbrevName}Property$typeParams property = other.asReadOnlyProperty();
        assertSame(property, property.asReadOnlyProperty());
    }

    @Test
    public void testReadableProperty${'$'}asReadOnlyProperty_Get() {
        Simple${type.abbrevName}Property$typeParams other = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Readable${type.abbrevName}Property$typeParams property = other.asReadOnlyProperty();
        assertEquals(other.get(), property.get());

        other.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(other.get(), property.get());
    }

    @Test
    public void testReadableProperty${'$'}asReadOnlyProperty_GetValue() {
        Simple${type.abbrevName}Property$typeParams other = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Readable${type.abbrevName}Property$typeParams property = other.asReadOnlyProperty();
        assertEquals(other.getValue(), property.getValue());

        other.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(other.getValue(), property.getValue());
    }

    @Test
    public void testReadableProperty${'$'}asReadOnlyProperty_IsBound() {
        Simple${type.abbrevName}Property$typeParams other1 = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Readable${type.abbrevName}Property$typeParams property = other1.asReadOnlyProperty();
        assertEquals(other1.isBound(), property.isBound());

        Simple${type.abbrevName}Property$typeParams other2 = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        other1.bindTo(other2);
        assertEquals(other1.isBound(), property.isBound());

        other1.unbind();
        assertEquals(other1.isBound(), property.isBound());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#isBound                                                                                      *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ReadableProperty${'$'}isBound_Initial() {
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        assertFalse(property.isBound());
    }

    @Test
    public void test_ReadableProperty${'$'}isBound_BoundProperty() {
        Simple${type.abbrevName}Property$typeParams other = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        assertFalse(property.isBound());

        property.bindTo(other);
        assertTrue(property.isBound());

        property.unbind();
        assertFalse(property.isBound());
    }

    @Test
    public void test_ReadableProperty${'$'}isBound_ReadOnlyProperty() {
        Simple${type.abbrevName}Property$typeParams other = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Readable${type.abbrevName}Property$typeParams property = other.asReadOnlyProperty();
        assertFalse(property.isWritable());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#isWritable                                                                                   *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ReadableProperty${'$'}isWritable_Initial() {
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        assertTrue(property.isWritable());
    }

    @Test
    public void test_ReadableProperty${'$'}isWritable_BoundProperty() {
        Simple${type.abbrevName}Property$typeParams other = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        assertTrue(property.isWritable());

        property.bindTo(other);
        assertFalse(property.isWritable());

        property.unbind();
        assertTrue(property.isWritable());
    }

    @Test
    public void test_ReadableProperty${'$'}isWritable_ReadOnlyProperty() {
        Simple${type.abbrevName}Property$typeParams other = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Readable${type.abbrevName}Property$typeParams property = other.asReadOnlyProperty();
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
    public void test_ObservableValue${'$'}get_Initial() {
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
    }

    @Test
    public void test_ObservableValue${'$'}get_SetGet() {
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        assertEquals(TestValues.${type.abbrevName}Value_L, property.get());

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ObservableValue#getValue                                                                                      *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ObservableValue${'$'}getValue_Initial() {
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        assertEquals(TestValues.${type.abbrevName}Value_H, property.getValue());
    }

    @Test
    public void test_ObservableValue${'$'}getValue_SetGet() {
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        assertEquals(TestValues.${type.abbrevName}Value_L, property.getValue());

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(TestValues.${type.abbrevName}Value_H, property.getValue());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableProperty#bindTo                                                                                       *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableProperty${'$'}bindTo_ThrowForBound() {
        Simple${type.abbrevName}Property$typeParams other1 = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        property.bindTo(other1);

        Simple${type.abbrevName}Property$typeParams other2 = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        assertThrows(IllegalStateException.class, () -> property.bindTo(other2));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableValue#set                                                                                             *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableValue${'$'}set_ThrowForBound() {
        Simple${type.abbrevName}Property$typeParams other = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        property.bindTo(other);
        assertThrows(IllegalStateException.class, () -> property.set(TestValues.${type.abbrevName}Value_H));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableValue#setValue                                                                                        *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableValue${'$'}setValue_NullConversion() {
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        property.setValue(null);
        assertEquals(TestValues.${type.abbrevName}Value_N, property.getValue());
    }

    @Test
    public void test_WritableValue${'$'}setValue_ThrowForBound() {
        Simple${type.abbrevName}Property$typeParams other = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        property.bindTo(other);
        assertThrows(IllegalStateException.class, () -> property.setValue(TestValues.${type.abbrevName}Value_H));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * Other (Overloads, etc.)
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
${Type.values().joinToString(separator = "") { sourceType ->
                val sourceTypeParams = if (sourceType === Type.OBJECT) "<Object>" else ""
                val sourceTypeDiamond = if (sourceType === Type.OBJECT) "<>" else ""

                """
    @Test
    public void test__Overloads$${sourceType.abbrevName}_bindTo() {
        Simple${sourceType.abbrevName}Property$sourceTypeParams other1 = new Simple${sourceType.abbrevName}Property$sourceTypeDiamond(TestValues.${sourceType.abbrevName}Value_L);
        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        property.bindTo(other1, ignore -> TestValues.${type.abbrevName}Value_H);

        Simple${sourceType.abbrevName}Property$sourceTypeParams other2 = new Simple${sourceType.abbrevName}Property$sourceTypeDiamond(TestValues.${sourceType.abbrevName}Value_L);
        assertThrows(IllegalStateException.class, () -> property.bindTo(other2, ignore -> TestValues.${type.abbrevName}Value_H));

        property.unbind();
        assertFalse(property.isBound());
    }
"""}}
    // TODO reconsider all tests below

    @Test
    public void testChangeListenerBoxAttachDetach() {
        var property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        ChangeListener<${if (type !== Type.OBJECT) type.box else "Object"}> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        property.addBoxedChangeListener(changeListener);
        assertTrue(property.removeBoxedChangeListener(changeListener));
    }

    @Test
    public void testChangeListenerDuplicateBoxAttachDetach() {
        var property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        ChangeListener<${if (type !== Type.OBJECT) type.box else "Object"}> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        assertTrue(property.addBoxedChangeListener(changeListener));
        assertFalse(property.addBoxedChangeListener(changeListener));
        assertTrue(property.removeBoxedChangeListener(changeListener));
        assertTrue(property.addBoxedChangeListener(changeListener));
    }

    @Test
    public void testChangeListenerSetGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        property.addChangeListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.${type.abbrevName}Value_L, oldValue);
            assertEquals(TestValues.${type.abbrevName}Value_H, newValue);
            assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
        });

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnSet() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        property.addChangeListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSetGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        property.addInvalidationListener((observable -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
        }));

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnSet() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        property.addInvalidationListener(observable -> callCounter.getAndIncrement());

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Property$typeParams wrapper = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        wrapper.addChangeListener((observable, oldValue, newValue) -> {
            switch (callCounter.getAndIncrement()) {
                case 0 -> {
                    assertEquals(TestValues.${type.abbrevName}Value_H, oldValue);
                    assertEquals(TestValues.${type.abbrevName}Value_L, newValue);
                    assertEquals(TestValues.${type.abbrevName}Value_L, property.get());
                }
                case 1 -> {
                    assertEquals(TestValues.${type.abbrevName}Value_L, oldValue);
                    assertEquals(TestValues.${type.abbrevName}Value_H, newValue);
                    assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
                }
                default -> throw new IllegalStateException();
            }
        });

        wrapper.bindTo(property);

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(2, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Property$typeParams wrapper = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        wrapper.addChangeListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        wrapper.bindTo(property);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Property$typeParams wrapper = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        wrapper.addInvalidationListener(observable -> {
            switch (callCounter.getAndIncrement()) {
                case 0 -> assertEquals(TestValues.${type.abbrevName}Value_L, property.get());
                case 1 -> assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
                default -> throw new IllegalStateException();
            }
        });

        wrapper.bindTo(property);

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(2, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Property$typeParams wrapper = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        wrapper.addInvalidationListener(observable -> callCounter.getAndIncrement());

        wrapper.bindTo(property);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerBindingUpdatedGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Property$typeParams wrapper = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        wrapper.bindTo(property);

        wrapper.addChangeListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.${type.abbrevName}Value_L, oldValue);
            assertEquals(TestValues.${type.abbrevName}Value_H, newValue);
            assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
        });

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnBindingUpdated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Property$typeParams wrapper = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        wrapper.addChangeListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerBindingUpdatedGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Property$typeParams wrapper = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        wrapper.bindTo(property);

        wrapper.addInvalidationListener(observable -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
        });

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnBindingUpdated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Simple${type.abbrevName}Property$typeParams wrapper = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        wrapper.addInvalidationListener(observable -> callCounter.getAndIncrement());

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedChangeListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        property.addChangeListener(new ${type.abbrevName}ChangeListener$typeDiamond() {

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
        property.set(TestValues.${type.abbrevName}Value_L);

        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedInvalidationListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Simple${type.abbrevName}Property$typeParams property = new Simple${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
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
        property.set(TestValues.${type.abbrevName}Value_H);
        property.set(TestValues.${type.abbrevName}Value_L);

        assertEquals(0, callCounter.get());
    }

}
            """
        }
    }

}