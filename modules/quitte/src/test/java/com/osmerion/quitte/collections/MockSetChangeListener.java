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

import org.jspecify.annotations.Nullable;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.*;

final class MockSetChangeListener<E> implements SetChangeListener<E> {

    @Nullable
    private Context context;

    @Override
    public void onChanged(ObservableSet<? extends E> observable, SetChangeListener.Change<? extends E> change) {
        if (this.context == null) return;

        change.addedElements().forEach(e -> this.context.operations.add(new Addition<>(e)));
        change.removedElements().forEach(e -> this.context.operations.add(new Removal<>(e)));
    }

    public Context push() {
        if (this.context != null) throw new IllegalStateException();
        return (this.context = new Context());
    }

    public final class Context implements AutoCloseable {

        private final ArrayDeque<Operation<E>> operations = new ArrayDeque<>();

        private Context() {}

        /** Closes this context and asserts that all operations have been consumed. */
        @Override
        public void close() {
            MockSetChangeListener.this.context = null;
            this.assertEmpty();
        }

        public void assertAddition(E element) {
            assertTrue(this.operations.removeFirstOccurrence(new Addition<>(element)), "No unconsumed addition of element recorded");
        }

        public void assertRemoval(E element) {
            assertTrue(this.operations.removeFirstOccurrence(new Removal<>(element)), "No unconsumed removal of element recorded");
        }

        /** Asserts that there are no unconsumed operations left in the current context. */
        public void assertEmpty() {
            assertTrue(this.operations.isEmpty());
        }

    }

    private sealed interface Operation<E> {}

    private record Addition<E>(E element) implements Operation<E> {}
    private record Removal<E>(E element) implements Operation<E> {}

}