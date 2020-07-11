/*
 * Copyright (c) 2018-2020 Leon Linhart,
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
val packageName = "com.github.osmerion.quitte.property"

Type.values().forEach {
    val type = it
    val typeParams = if (type === Type.OBJECT) "<Object>" else ""
    val typeDiamond = if (type === Type.OBJECT) "<>" else ""
    val valAnno = if (type === Type.OBJECT) "@Nullable " else ""

    template("${packageName.replace('.', '/')}/Lazy${type.abbrevName}PropertyGeneratedTest", isTest = true) {
        """package $packageName;

import java.util.concurrent.atomic.AtomicInteger;
${if (type === Type.OBJECT) "\nimport javax.annotation.Nullable;\n" else ""}
import com.github.osmerion.quitte.*;
import com.github.osmerion.quitte.value.*;
import com.github.osmerion.quitte.value.change.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
            
/**
 * Generated tests for {@link Lazy${type.abbrevName}Property}.
 *
 * @author  Leon Linhart
 */
public final class Lazy${type.abbrevName}PropertyGeneratedTest {

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#asReadOnlyProperty                                                                           *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void testReadableProperty${'$'}asReadOnlyProperty_NewInstanceAvoidance() {
        Lazy${type.abbrevName}Property$typeParams other = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Readable${type.abbrevName}Property$typeParams property = other.asReadOnlyProperty();
        assertSame(property, property.asReadOnlyProperty());
    }

    @Test
    public void testReadableProperty${'$'}asReadOnlyProperty_Get() {
        Lazy${type.abbrevName}Property$typeParams other = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Readable${type.abbrevName}Property$typeParams property = other.asReadOnlyProperty();
        assertEquals(TestValues.${type.abbrevName}Value_L, property.get());

        other.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#isBound                                                                                      *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ReadableProperty${'$'}isBound_Initial() {
        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        assertFalse(property.isBound());
    }

    @Test
    public void test_ReadableProperty${'$'}isBound_BoundProperty() {
        Lazy${type.abbrevName}Property$typeParams other = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        assertFalse(property.isBound());

        property.bindTo(other);
        assertTrue(property.isBound());

        property.unbind();
        assertFalse(property.isBound());
    }

    @Test
    public void test_ReadableProperty${'$'}isBound_ReadOnlyProperty() {
        Lazy${type.abbrevName}Property$typeParams other = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Readable${type.abbrevName}Property$typeParams property = other.asReadOnlyProperty();
        assertFalse(property.isWritable());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ReadableProperty#isWritable                                                                                   *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ReadableProperty${'$'}isWritable_Initial() {
        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        assertTrue(property.isWritable());
    }

    @Test
    public void test_ReadableProperty${'$'}isWritable_BoundProperty() {
        Lazy${type.abbrevName}Property$typeParams other = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        assertTrue(property.isWritable());

        property.bindTo(other);
        assertFalse(property.isWritable());

        property.unbind();
        assertTrue(property.isWritable());
    }

    @Test
    public void test_ReadableProperty${'$'}isWritable_ReadOnlyProperty() {
        Lazy${type.abbrevName}Property$typeParams other = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Readable${type.abbrevName}Property$typeParams property = other.asReadOnlyProperty();
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
    public void test_ObservableValue${'$'}get_Initial() {
        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
    }

    @Test
    public void test_ObservableValue${'$'}get_SetGet() {
        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        assertEquals(TestValues.${type.abbrevName}Value_L, property.get());

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
    }

    @Test
    public void test_ObservableValue${'$'}get_SetDeferredGet() {
        Lazy${type.abbrevName}Property property = new Lazy${type.abbrevName}Property(TestValues.${type.abbrevName}Value_L);
        assertEquals(TestValues.${type.abbrevName}Value_L, property.getValue());

        property.set(() -> TestValues.${type.abbrevName}Value_H);
        assertEquals(TestValues.${type.abbrevName}Value_H, property.getValue());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * ObservableValue#getValue                                                                                      *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_ObservableValue${'$'}getValue_Initial() {
        Lazy${type.abbrevName}Property property = new Lazy${type.abbrevName}Property(TestValues.${type.abbrevName}Value_H);
        assertEquals(TestValues.${type.abbrevName}Value_H, property.getValue());
    }

    @Test
    public void test_ObservableValue${'$'}getValue_SetGet() {
        Lazy${type.abbrevName}Property property = new Lazy${type.abbrevName}Property(TestValues.${type.abbrevName}Value_L);
        assertEquals(TestValues.${type.abbrevName}Value_L, property.getValue());

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(TestValues.${type.abbrevName}Value_H, property.getValue());
    }

    @Test
    public void test_ObservableValue${'$'}getValue_SetDeferredGet() {
        Lazy${type.abbrevName}Property property = new Lazy${type.abbrevName}Property(TestValues.${type.abbrevName}Value_L);
        assertEquals(TestValues.${type.abbrevName}Value_L, property.getValue());

        property.set(() -> TestValues.${type.abbrevName}Value_H);
        assertEquals(TestValues.${type.abbrevName}Value_H, property.getValue());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableProperty#bindTo                                                                                       *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableProperty${'$'}bindTo_ThrowForBound() {
        Lazy${type.abbrevName}Property$typeParams other1 = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        property.bindTo(other1);

        Lazy${type.abbrevName}Property$typeParams other2 = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        assertThrows(IllegalStateException.class, () -> property.bindTo(other2));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableValue#set                                                                                             *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableValue${'$'}set_ThrowForBound() {
        Lazy${type.abbrevName}Property$typeParams other = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        property.bindTo(other);
        assertThrows(IllegalStateException.class, () -> property.set(TestValues.${type.abbrevName}Value_H));
    }

    @Test
    public void test_WritableValue${'$'}setDeferred_ThrowForBound() {
        Lazy${type.abbrevName}Property$typeParams other = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        property.bindTo(other);
        assertThrows(IllegalStateException.class, () -> property.set(() -> TestValues.${type.abbrevName}Value_H));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\
     * WritableValue#setValue                                                                                        *
    \*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Test
    public void test_WritableValue${'$'}setValue_ThrowForBound() {
        Lazy${type.abbrevName}Property$typeParams other = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        property.bindTo(other);
        assertThrows(IllegalStateException.class, () -> property.set(TestValues.${type.abbrevName}Value_H));
    }

    // TODO reconsider all tests below

    @Test
    public void testInitialGetConsistencyForPrimaryCtor() {
        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
        assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
    }

    @Test
    public void testInitialGetConsistencyForLazyInitialization() {
        var property = new Lazy${type.abbrevName}Property$typeDiamond(() -> TestValues.${type.abbrevName}Value_H);
        assertEquals(LazyValue.State.UNINITIALIZED, property.getState());
        assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
    }

    @Test
    public void testSetGetStateLifecycleForPrimaryCtor() {
        var propertyInvalidatedCallCounter = new AtomicInteger(0);
        var stateChangedCallCounter = new AtomicInteger(0);
        var stateInvalidatedCallCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        property.addListener((observable -> propertyInvalidatedCallCounter.getAndIncrement()));

        var state = property.stateProperty();
        state.addListener(((observable, oldValue, newValue) -> stateChangedCallCounter.getAndIncrement()));
        state.addListener(((observable) -> stateInvalidatedCallCounter.getAndIncrement()));

        assertEquals(LazyValue.State.INITIALIZED, property.getState());

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(LazyValue.State.INVALID, property.getState());
        assertEquals(1, propertyInvalidatedCallCounter.get());
        assertEquals(1, stateChangedCallCounter.get());
        assertEquals(1, stateInvalidatedCallCounter.get());

        property.get();
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(1, propertyInvalidatedCallCounter.get());
        assertEquals(2, stateChangedCallCounter.get());
        assertEquals(2, stateInvalidatedCallCounter.get());

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(LazyValue.State.INVALID, property.getState());
        assertEquals(2, propertyInvalidatedCallCounter.get());
        assertEquals(3, stateChangedCallCounter.get());
        assertEquals(3, stateInvalidatedCallCounter.get());

        property.set(TestValues.${type.abbrevName}Value_H);
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

        var property = new Lazy${type.abbrevName}Property$typeDiamond(() -> TestValues.${type.abbrevName}Value_H);
        property.addListener((observable -> propertyInvalidatedCallCounter.getAndIncrement()));

        var state = property.stateProperty();
        state.addListener(((observable, oldValue, newValue) -> stateChangedCallCounter.getAndIncrement()));
        state.addListener(((observable) -> stateInvalidatedCallCounter.getAndIncrement()));

        assertEquals(LazyValue.State.UNINITIALIZED, property.getState());

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(LazyValue.State.UNINITIALIZED, property.getState());
        assertEquals(0, propertyInvalidatedCallCounter.get());
        assertEquals(0, stateChangedCallCounter.get());
        assertEquals(0, stateInvalidatedCallCounter.get());

        property.get();
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
        assertEquals(0, propertyInvalidatedCallCounter.get());
        assertEquals(1, stateChangedCallCounter.get());
        assertEquals(1, stateInvalidatedCallCounter.get());

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(LazyValue.State.INVALID, property.getState());
        assertEquals(1, propertyInvalidatedCallCounter.get());
        assertEquals(2, stateChangedCallCounter.get());
        assertEquals(2, stateInvalidatedCallCounter.get());

        property.set(TestValues.${type.abbrevName}Value_H);
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
        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        ChangeListener<${if (type !== Type.OBJECT) type.box else "Object"}> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");
        
        property.addBoxedListener(changeListener);
        assertTrue(property.removeBoxedListener(changeListener));
    }

    @Test
    public void testChangeListenerBoxAttachDetach() {
        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        ChangeListener<${if (type !== Type.OBJECT) type.box else "Object"}> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        property.addBoxedListener(changeListener);
        assertTrue(property.removeBoxedListener(changeListener));
    }

    @Test
    public void testChangeListenerDuplicateBoxAttachDetach() {
        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        ChangeListener<${if (type !== Type.OBJECT) type.box else "Object"}> changeListener = (observable, oldValue, newValue) -> System.out.println("blub");

        assertTrue(property.addBoxedListener(changeListener));
        assertFalse(property.addBoxedListener(changeListener));
        assertTrue(property.removeBoxedListener(changeListener));
        assertTrue(property.addBoxedListener(changeListener));
    }

    @Test
    public void testChangeListenerSetGetConsistencyForPrimaryCtor() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        property.addListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(LazyValue.State.VALID, property.getState());
            assertEquals(TestValues.${type.abbrevName}Value_H, oldValue);
            assertEquals(TestValues.${type.abbrevName}Value_L, newValue);
            assertEquals(TestValues.${type.abbrevName}Value_L, property.get());
        });

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(0, callCounter.get());

        assertEquals(TestValues.${type.abbrevName}Value_L, property.get());
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSetGetConsistencyForLazyInitialization() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(() -> TestValues.${type.abbrevName}Value_H);
        property.addListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(LazyValue.State.INITIALIZED, property.getState());
            assertEquals(TestValues.${type.abbrevName}Value_N, oldValue);
            assertEquals(TestValues.${type.abbrevName}Value_L, newValue);
            assertEquals(TestValues.${type.abbrevName}Value_L, property.get());
        });

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(0, callCounter.get());

        assertEquals(TestValues.${type.abbrevName}Value_L, property.get());
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerDeferredOnSetForPrimaryCtor() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        property.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerDeferredOnSetForLazyInitialization() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(() -> TestValues.${type.abbrevName}Value_H);
        property.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnSetGetForPrimaryCtor() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        property.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());
        assertEquals(LazyValue.State.INITIALIZED, property.getState());

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerInitForLazyInitialization() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(() -> TestValues.${type.abbrevName}Value_H);
        property.addListener((observable, oldValue, newValue) -> {
            callCounter.getAndIncrement();
            assertEquals(1, callCounter.get());
            assertEquals(LazyValue.State.INITIALIZED, property.getState());
            assertEquals(TestValues.${type.abbrevName}Value_N, oldValue);
            assertEquals(TestValues.${type.abbrevName}Value_H, newValue);
        });

        assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerOnSetForPrimaryCtor() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        property.addListener(observable -> {
            callCounter.getAndIncrement();
            assertEquals(LazyValue.State.INVALID, property.getState());
            assertEquals(TestValues.${type.abbrevName}Value_L, property.get());
        });

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSkippedOnSetForLazyInitialization() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(() -> TestValues.${type.abbrevName}Value_H);
        property.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedChangeListenerRemoval() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        property.addListener(new ${type.abbrevName}ChangeListener$typeDiamond() {

            @Override
            public void onChanged(Observable${type.abbrevName}Value$typeParams observable, $valAnno${if (type === Type.OBJECT) "Object" else type.raw} oldValue, $valAnno${if (type === Type.OBJECT) "Object" else type.raw} newValue) {
                callCounter.getAndIncrement();
            }

            @Override
            public boolean isInvalid() {
                return true;
            }

        });

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(TestValues.${type.abbrevName}Value_L, property.get());
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedInvalidationListenerRemoval() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
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

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(TestValues.${type.abbrevName}Value_L, property.get());
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerDeferredOnBindingCreated() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        var wrapper = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        wrapper.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        wrapper.bindTo(property);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testChangeListenerOnGetForBindingWithSameValue() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        var wrapper = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        wrapper.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        wrapper.bindTo(property);
        assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerOnBindingCreated() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        var wrapper = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_L);
        wrapper.addListener(observable -> {
            switch (callCounter.getAndIncrement()) {
                case 0 -> assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
                case 1 -> assertEquals(TestValues.${type.abbrevName}Value_L, property.get());
                default -> throw new IllegalStateException();
            }
        });

        wrapper.bindTo(property);
        assertEquals(1, callCounter.get());
        assertEquals(TestValues.${type.abbrevName}Value_H, wrapper.get());

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(2, callCounter.get());
    }

    @Test
    public void testChangeListenerBindingUpdatedGetConsistency() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        var wrapper = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        wrapper.bindTo(property);

        wrapper.addListener((observable, oldValue, newValue) -> {
            callCounter.getAndIncrement();
            assertEquals(TestValues.${type.abbrevName}Value_H, oldValue);
            assertEquals(TestValues.${type.abbrevName}Value_L, newValue);
            assertEquals(TestValues.${type.abbrevName}Value_L, property.get());
        });

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(TestValues.${type.abbrevName}Value_L, wrapper.get());
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerDeferredOnBindingUpdated() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        var wrapper = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        wrapper.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerBindingUpdatedGetConsistency() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(() -> TestValues.${type.abbrevName}Value_H);
        var wrapper = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        wrapper.bindTo(property);

        wrapper.addListener(observable -> callCounter.getAndIncrement());

        assertEquals(LazyValue.State.UNINITIALIZED, property.getState());
        assertEquals(LazyValue.State.INVALID, wrapper.getState());
        
        assertEquals(TestValues.${type.abbrevName}Value_H, property.get());
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
        assertEquals(LazyValue.State.INVALID, wrapper.getState());

        assertEquals(TestValues.${type.abbrevName}Value_H, wrapper.get());
        assertEquals(LazyValue.State.INITIALIZED, property.getState());
        assertEquals(LazyValue.State.VALID, wrapper.getState());

        property.set(TestValues.${type.abbrevName}Value_L);
        assertEquals(1, callCounter.get());

        assertEquals(TestValues.${type.abbrevName}Value_L, property.get());
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(LazyValue.State.INVALID, wrapper.getState());

        assertEquals(TestValues.${type.abbrevName}Value_L, wrapper.get());
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(LazyValue.State.VALID, wrapper.getState());
    }

    @Test
    public void testInvalidationListenerDeferredOnBindingUpdated() {
        var callCounter = new AtomicInteger(0);

        var property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        var wrapper = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_H);
        wrapper.addListener(observable -> callCounter.getAndIncrement());

        property.set(TestValues.${type.abbrevName}Value_H);
        assertEquals(0, callCounter.get());
    }

}"""
    }
}