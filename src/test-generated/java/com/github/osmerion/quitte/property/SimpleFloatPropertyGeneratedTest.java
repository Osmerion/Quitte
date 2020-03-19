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

import java.util.concurrent.atomic.AtomicBoolean;
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

    @Test
    public void testChangeListener() {
        AtomicBoolean flag = new AtomicBoolean(false);
    
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_1);
        property.addListener((observable, oldValue, newValue) -> {
            flag.set(true);
            assertEquals(TestValues.FloatValue_1, oldValue);
            assertEquals(TestValues.FloatValue_2, newValue);
        });
        
        property.set(TestValues.FloatValue_2);
        assertTrue(flag.get());
    }

    @Test
    public void testChangeListenerInvalidation() {
        AtomicInteger counter = new AtomicInteger(0);
    
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_1);
        property.addListener(new FloatChangeListener() {

            @Override
            public void onChanged(ObservableFloatValue observable, float oldValue, float newValue) {
                counter.incrementAndGet();
            }
            
            @Override
            public boolean isInvalid() {
                return true;
            }

        });
        property.set(TestValues.FloatValue_2);
        property.set(TestValues.FloatValue_1);

        assertEquals(1, counter.get());
    }

    @Test
    @DisplayName("ChangeListener on set invocation with previous value")
    public void testChangeListenerSkipped() {
        AtomicBoolean flag = new AtomicBoolean(false);
    
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_1);
        property.addListener((observable, oldValue, newValue) -> flag.set(true));
        
        property.set(TestValues.FloatValue_1);
        assertFalse(flag.get());
    }

    @Test
    public void testInvalidationListener() {
        AtomicBoolean flag = new AtomicBoolean(false);
    
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_1);
        property.addListener(observable -> flag.set(true));
        
        property.set(TestValues.FloatValue_2);
        assertTrue(flag.get());
    }

    @Test
    public void testInvalidationListenerInvalidation() {
        AtomicInteger counter = new AtomicInteger(0);
    
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_1);
        property.addListener(new InvalidationListener() {

            @Override
            public void onInvalidation(Observable observable) {
                counter.incrementAndGet();
            }
            
            @Override
            public boolean isInvalid() {
                return true;
            }

        });
        property.set(TestValues.FloatValue_2);
        property.set(TestValues.FloatValue_1);

        assertEquals(1, counter.get());
    }

    @Test
    @DisplayName("InvalidationListener on set invocation with previous value")
    public void testInvalidationListenerSkipped() {
        AtomicBoolean flag = new AtomicBoolean(false);
    
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_1);
        property.addListener(observable -> flag.set(true));
        
        property.set(TestValues.FloatValue_1);
        assertFalse(flag.get());
    }

    @Test
    @DisplayName("set-get consistency")
    public void testSetGetConsistency() {
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_1);
        assertEquals(TestValues.FloatValue_1, property.get());
        
        property.set(TestValues.FloatValue_2);
        assertEquals(TestValues.FloatValue_2, property.get());
    }

    @Test
    @DisplayName("SimpleFloatProperty#set() return value")
    public void testSetReturn() {
        SimpleFloatProperty property = new SimpleFloatProperty(TestValues.FloatValue_1);
        assertEquals(TestValues.FloatValue_1, property.set(TestValues.FloatValue_2));
    }

}