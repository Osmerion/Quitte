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
package com.osmerion.quitte.collections;

import java.util.ArrayDeque;
import java.util.List;
import javax.annotation.Nullable;

import static org.junit.jupiter.api.Assertions.*;

final class MockListChangeListener<E> implements CollectionChangeListener<ObservableList.Change<? extends E>> {

    @Nullable
    private Context context;

    @SuppressWarnings("unchecked")
    @Override
    public void onChanged(ObservableList.Change<? extends E> change) {
        if (this.context == null) return;

        if (change instanceof ObservableList.Change.Permutation) {
            ObservableList.Change.Permutation<E> permutation = (ObservableList.Change.Permutation<E>) change;
            this.context.operations.add(new Permutation<>(permutation.getIndices()));
        } else if (change instanceof ObservableList.Change.Update) {
            ObservableList.Change.Update<E> update = (ObservableList.Change.Update<E>) change;
            update.getLocalChanges().forEach(localChange -> {
                OpType type;

                if (localChange instanceof ObservableList.LocalChange.Insertion) {
                    type = OpType.INSERTION;
                } else if (localChange instanceof ObservableList.LocalChange.Removal) {
                    type = OpType.REMOVAL;
                } else if (localChange instanceof ObservableList.LocalChange.Update) {
                    type = OpType.UPDATE;
                } else {
                    throw new IllegalStateException();
                }

                this.context.operations.add(new Update<>(type, localChange.getIndex(), localChange.getElements()));
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
            assertEquals(elements, update.elements);
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
            assertEquals(elements, update.elements);
        }

        public void assertPermutation(List<Integer> indices) {
            Operation<E> operation = this.operations.pollFirst();

            if (!(operation instanceof Permutation<E> permutation)) {
                fail();
                return;
            }

            assertEquals(indices, permutation.indices);
        }

        /** Asserts that there are no unconsumed operations left in the current context. */
        public void assertEmpty() {
            assertTrue(this.operations.isEmpty());
        }
        
    }

    private interface Operation<E> {}

    private static record Permutation<E>(List<Integer> indices) implements Operation<E> {}

    private record Update<E>(OpType type, int offset, List<E> elements) implements Operation<E> {}

    enum OpType {
        INSERTION,
        REMOVAL,
        UPDATE
    }

}