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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class MockListChangeListener<E> implements ListChangeListener<E> {

    @Nullable
    private Context context;

    @SuppressWarnings("unchecked")
    @Override
    public void onChanged(ObservableList<? extends E> observable, ListChangeListener.Change<? extends E> change) {
        if (this.context == null) return;

        if (change instanceof ListChangeListener.Change.Permutation) {
            ListChangeListener.Change.Permutation<E> permutation = (ListChangeListener.Change.Permutation<E>) change;
            this.context.operations.add(new Permutation<>(permutation.indices()));
        } else if (change instanceof ListChangeListener.Change.Update) {
            ListChangeListener.Change.Update<E> update = (ListChangeListener.Change.Update<E>) change;
            update.localChanges().forEach(localChange -> {
                OpType type;
                List<E> oldElements, newElements;

                if (localChange instanceof ListChangeListener.LocalChange.Insertion<E> localInsertion) {
                    type = OpType.INSERTION;
                    oldElements = List.of();
                    newElements = localInsertion.elements();
                } else if (localChange instanceof ListChangeListener.LocalChange.Removal<E> localRemoval) {
                    type = OpType.REMOVAL;
                    oldElements = localRemoval.elements();
                    newElements = List.of();
                } else if (localChange instanceof ListChangeListener.LocalChange.Update<E> localUpdate) {
                    type = OpType.UPDATE;
                    oldElements = localUpdate.oldElements();
                    newElements = localUpdate.newElements();
                } else {
                    throw new IllegalStateException();
                }

                this.context.operations.add(new Update<>(type, localChange.index(), oldElements, newElements));
            });
        } else {
            throw new UnsupportedOperationException();
        }
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
            MockListChangeListener.this.context = null;
            this.assertEmpty();
        }

        @SafeVarargs
        public final void assertInsertion(int offset, E... elements) {
            assertInsertion(offset, List.of(elements));
        }

        public void assertInsertion(int offset, List<E> elements) {
            Operation<E> operation = this.operations.pollFirst();

            if (!(operation instanceof Update<E> update) || update.type != OpType.INSERTION) {
                fail();
                return;
            }

            assertEquals(offset, update.offset);
            assertEquals(elements, update.newElements());
        }

        @SafeVarargs
        public final void assertRemoval(int offset, E... elements) {
            assertRemoval(offset, List.of(elements));
        }

        public void assertRemoval(int offset, List<E> elements) {
            Operation<E> operation = this.operations.pollFirst();

            if (!(operation instanceof Update<E> update) || update.type != OpType.REMOVAL) {
                fail();
                return;
            }

            assertEquals(offset, update.offset);
            assertEquals(elements, update.oldElements());
        }

        public void assertPermutation(List<Integer> indices) {
            Operation<E> operation = this.operations.pollFirst();

            if (!(operation instanceof Permutation<E> permutation)) {
                fail();
                return;
            }

            assertEquals(indices, permutation.indices);
        }

        @SafeVarargs
        public final void assertUpdate(int offset, E... elements) {
            List<E> oldElements = new ArrayList<>();
            List<E> newElements = new ArrayList<>();

            for (int i = 0; i < elements.length / 2; i += 2) {
                oldElements.add(elements[i]);
                newElements.add(elements[i + 1]);
            }

            assertUpdate(offset, List.copyOf(oldElements), List.copyOf(newElements));
        }

        public void assertUpdate(int offset, List<E> oldElements, List<E> newElements) {
            Operation<E> operation = this.operations.pollFirst();

            if (!(operation instanceof Update<E> update) || update.type != OpType.UPDATE) {
                fail();
                return;
            }

            assertEquals(offset, update.offset());
            assertEquals(oldElements, update.oldElements());
            assertEquals(newElements, update.newElements());
        }

        /** Asserts that there are no unconsumed operations left in the current context. */
        public void assertEmpty() {
            assertTrue(this.operations.isEmpty());
        }

    }

    @SuppressWarnings("unused")
    private sealed interface Operation<E> {}

    private record Permutation<E>(List<Integer> indices) implements Operation<E> {}

    private record Update<E>(OpType type, int offset, List<E> oldElements, List<E> newElements) implements Operation<E> {}

    enum OpType {
        INSERTION,
        REMOVAL,
        UPDATE
    }

}