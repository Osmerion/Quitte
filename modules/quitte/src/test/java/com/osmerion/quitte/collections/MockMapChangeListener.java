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
package com.osmerion.quitte.collections;

import java.util.ArrayDeque;
import java.util.Objects;
import javax.annotation.Nullable;

import static org.junit.jupiter.api.Assertions.*;

final class MockMapChangeListener<K, V> implements CollectionChangeListener<ObservableMap.Change<? extends K, ? extends V>> {

    @Nullable
    private Context context;

    @Override
    public void onChanged(ObservableMap.Change<? extends K, ? extends V> change) {
        if (this.context == null) return;

        change.addedElements().forEach((k, v) -> this.context.operations.add(new Addition(k, v)));
        change.removedElements().forEach((k, v) -> this.context.operations.add(new Removal(k, v)));
        change.updatedElements().forEach((k, update) -> this.context.operations.add(new Update(k, update.getOldValue(), update.getNewValue())));
    }

    public Context push() {
        if (this.context != null) throw new IllegalStateException();
        return (this.context = new Context());
    }

    public final class Context implements AutoCloseable {

        private final ArrayDeque<Operation> operations = new ArrayDeque<>();

        private Context() {}

        /** Closes this context and asserts that all operations have been consumed. */
        @Override
        public void close() {
            MockMapChangeListener.this.context = null;
            this.assertEmpty();
        }

        public void assertAddition(K key, V value) {
            assertTrue(this.operations.removeFirstOccurrence(new Addition(key, value)), "No unconsumed addition of element recorded");
        }

        public void assertRemoval(K key, V value) {
            assertTrue(this.operations.removeFirstOccurrence(new Removal(key, value)), "No unconsumed removal of element recorded");
        }

        public void assertUpdate(K key, V oldValue, V newValue) {
            assertTrue(this.operations.removeFirstOccurrence(new Update(key, oldValue, newValue)), "No unconsumed update of element recorded");
        }

        /** Asserts that there are no unconsumed operations left in the current context. */
        public void assertEmpty() {
            assertTrue(this.operations.isEmpty());
        }

    }

    private abstract class Operation {

        @Nullable final K key;
        @Nullable final V value;

        Operation(@Nullable K key, @Nullable V value) {
            this.key = key;
            this.value = value;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;

            if (this.getClass() == obj.getClass()) {
                Operation other = (Operation) obj;
                return Objects.equals(this.key, other.key) && Objects.equals(this.value, other.value);
            }

            return false;
        }

    }

    private class Addition extends Operation {
        Addition(@Nullable K key, @Nullable V value) { super(key, value); }
    }

    private class Removal extends Operation {
        Removal(@Nullable K key, @Nullable V value) { super(key, value); }
    }

    private class Update extends Operation {

        @Nullable
        private final V oldValue;
        
        Update(@Nullable K key, @Nullable V oldValue, @Nullable V newValue) {
            super(key, newValue);
            this.oldValue = oldValue;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;

            if (this.getClass() == obj.getClass()) {
                Update other = (Update) obj;
                return Objects.equals(this.key, other.key)
                    && Objects.equals(this.oldValue, other.oldValue)
                    && Objects.equals(this.value, other.value);
            }

            return false;
        }
        
    }

}