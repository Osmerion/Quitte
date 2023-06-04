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
import java.util.List;
import javax.annotation.Nullable;

import static org.junit.jupiter.api.Assertions.*;

final class MockDequeChangeListener<E> implements DequeChangeListener<E> {

    @Nullable
    private Context context;

    @SuppressWarnings("unchecked")
    @Override
    public void onChanged(ObservableDeque<? extends E> observable, DequeChangeListener.Change<? extends E> change) {
        if (this.context == null) return;

        change.localChanges().stream()
            .map(it -> {
                if (it instanceof DequeChangeListener.LocalChange.Insertion) {
                    DequeChangeListener.LocalChange.Insertion<E> localChange = ((DequeChangeListener.LocalChange.Insertion<E>) it);
                    return new Operation(OpType.INSERTION, localChange.site(), localChange.elements());
                } else if (it instanceof DequeChangeListener.LocalChange.Removal) {
                    DequeChangeListener.LocalChange.Removal<E> localChange = ((DequeChangeListener.LocalChange.Removal<E>) it);
                    return new Operation(OpType.REMOVAL, localChange.site(), localChange.elements());
                } else {
                    throw new IllegalStateException();
                }
            })
            .forEach(this.context.operations::add);
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
            MockDequeChangeListener.this.context = null;
            this.assertEmpty();
        }

        public void assertInsertion(DequeChangeListener.Site site, E element) {
            this.assertInsertion(site, List.of(element));
        }

        public void assertInsertion(DequeChangeListener.Site site, List<? extends E> elements) {
            Operation operation = this.operations.pollFirst();

            assertNotNull(operation);
            assertEquals(OpType.INSERTION, operation.type);
            assertEquals(site, operation.site);
            assertEquals(elements, operation.elements);
        }

        public void assertRemoval(DequeChangeListener.Site site, E element) {
            this.assertRemoval(site, List.of(element));
        }

        public void assertRemoval(DequeChangeListener.Site site, List<? extends E> elements) {
            Operation operation = this.operations.pollFirst();

            assertNotNull(operation);
            assertEquals(OpType.REMOVAL, operation.type);
            assertEquals(site, operation.site);
            assertEquals(elements, operation.elements);
        }

        /** Asserts that there are no unconsumed operations left in the current context. */
        public void assertEmpty() {
            assertTrue(this.operations.isEmpty());
        }

    }

    private final class Operation {

        private final OpType type;
        @Nullable private final DequeChangeListener.Site site;
        private final List<? extends E> elements;

        Operation(OpType type, @Nullable DequeChangeListener.Site site, List<? extends E> elements) {
            this.type = type;
            this.site = site;
            this.elements = elements;
        }

    }

    enum OpType {
        INSERTION,
        REMOVAL
    }

}