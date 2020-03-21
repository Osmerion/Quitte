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

    @Test
    public void testInitialGetConsistency() {
        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_2);
        assertEquals(TestValues.${type.abbrevName}Value_2, property.get());
    }

    @Test
    public void testSetGetConsistency() {
        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
        assertEquals(TestValues.${type.abbrevName}Value_1, property.get());

        property.set(TestValues.${type.abbrevName}Value_2);
        assertEquals(TestValues.${type.abbrevName}Value_2, property.get());
    }

    @Test
    public void testSetReturn() {
        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
        assertEquals(TestValues.${type.abbrevName}Value_1, property.set(TestValues.${type.abbrevName}Value_2));
    }

    @Test
    public void testChangeListenerSetGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
        property.addListener((observable, oldValue, newValue) -> {
            callCounter.incrementAndGet();
            assertEquals(TestValues.${type.abbrevName}Value_1, oldValue);
            assertEquals(TestValues.${type.abbrevName}Value_2, newValue);
            assertEquals(TestValues.${type.abbrevName}Value_2, property.get());
        });

        property.set(TestValues.${type.abbrevName}Value_2);
        assertEquals(0, callCounter.get());
        
        assertEquals(TestValues.${type.abbrevName}Value_2, property.get());
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnSet() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
        property.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.${type.abbrevName}Value_1);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerSetGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
        property.addListener(observable -> {
            callCounter.getAndIncrement();
            assertEquals(TestValues.${type.abbrevName}Value_2, property.get());
        });

        property.set(TestValues.${type.abbrevName}Value_2);
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
        Lazy${type.abbrevName}Property$typeParams wrapper = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
        wrapper.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        wrapper.bindTo(property);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerOnBindingCreated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
        Lazy${type.abbrevName}Property$typeParams wrapper = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_2);
        wrapper.addListener(observable -> {
            switch (callCounter.getAndIncrement()) {
                case 0 -> assertEquals(TestValues.${type.abbrevName}Value_1, property.get());
                case 1 -> assertEquals(TestValues.${type.abbrevName}Value_2, property.get());
                default -> throw new IllegalStateException();
            }
        });

        wrapper.bindTo(property);
        assertEquals(1, callCounter.get());
        assertEquals(TestValues.${type.abbrevName}Value_1, wrapper.get());

        property.set(TestValues.${type.abbrevName}Value_2);
        assertEquals(2, callCounter.get());
    }

    @Test
    public void testChangeListenerBindingUpdatedGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
        Lazy${type.abbrevName}Property$typeParams wrapper = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
        wrapper.bindTo(property);

        wrapper.addListener((observable, oldValue, newValue) -> {
            callCounter.getAndIncrement();
            assertEquals(TestValues.${type.abbrevName}Value_1, oldValue);
            assertEquals(TestValues.${type.abbrevName}Value_2, newValue);
            assertEquals(TestValues.${type.abbrevName}Value_2, property.get());
        });

        property.set(TestValues.${type.abbrevName}Value_2);
        assertEquals(TestValues.${type.abbrevName}Value_2, wrapper.get());
        assertEquals(1, callCounter.get());
    }

    @Test
    public void testChangeListenerSkippedOnBindingUpdated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
        Lazy${type.abbrevName}Property$typeParams wrapper = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
        wrapper.addListener((observable, oldValue, newValue) -> callCounter.getAndIncrement());

        property.set(TestValues.${type.abbrevName}Value_1);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidationListenerBindingUpdatedGetConsistency() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(() -> TestValues.${type.abbrevName}Value_1);
        Lazy${type.abbrevName}Property$typeParams wrapper = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
        wrapper.bindTo(property);

        wrapper.addListener(observable -> callCounter.getAndIncrement());

        assertEquals(LazyValue.State.UNINITIALIZED, property.getState());
        assertEquals(LazyValue.State.INVALID, wrapper.getState());
        
        assertEquals(TestValues.${type.abbrevName}Value_1, property.get());
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(LazyValue.State.INVALID, wrapper.getState());

        assertEquals(TestValues.${type.abbrevName}Value_1, wrapper.get());
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(LazyValue.State.VALID, wrapper.getState());

        property.set(TestValues.${type.abbrevName}Value_2);
        assertEquals(1, callCounter.get());

        assertEquals(TestValues.${type.abbrevName}Value_2, property.get());
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(LazyValue.State.INVALID, wrapper.getState());

        assertEquals(TestValues.${type.abbrevName}Value_2, wrapper.get());
        assertEquals(LazyValue.State.VALID, property.getState());
        assertEquals(LazyValue.State.VALID, wrapper.getState());
    }

    @Test
    public void testInvalidationListenerSkippedOnBindingUpdated() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
        Lazy${type.abbrevName}Property$typeParams wrapper = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
        wrapper.addListener(observable -> callCounter.getAndIncrement());

        property.set(TestValues.${type.abbrevName}Value_1);
        assertEquals(0, callCounter.get());
    }

    @Test
    public void testInvalidatedChangeListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
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
        property.set(TestValues.${type.abbrevName}Value_2);
        assertEquals(TestValues.${type.abbrevName}Value_2, property.get());
        
        property.set(TestValues.${type.abbrevName}Value_1);
        assertEquals(TestValues.${type.abbrevName}Value_1, property.get());

        assertEquals(1, callCounter.get());
    }

    @Test
    public void testInvalidatedInvalidationListenerRemoval() {
        AtomicInteger callCounter = new AtomicInteger(0);

        Lazy${type.abbrevName}Property$typeParams property = new Lazy${type.abbrevName}Property$typeDiamond(TestValues.${type.abbrevName}Value_1);
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
        property.set(TestValues.${type.abbrevName}Value_2);
        assertEquals(TestValues.${type.abbrevName}Value_2, property.get());
        
        property.set(TestValues.${type.abbrevName}Value_1);
        assertEquals(TestValues.${type.abbrevName}Value_1, property.get());

        assertEquals(1, callCounter.get());
    }

}"""
    }
}