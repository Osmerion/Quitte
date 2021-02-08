/*
 * Copyright (c) 2018-2021 Leon Linhart,
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
package com.osmerion.quitte.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

import com.osmerion.quitte.collections.AbstractObservableList;
import com.osmerion.quitte.collections.ObservableList;
import com.osmerion.quitte.internal.binding.ListBinding;

/**
 * A {@link List} property.
 *
 * @param <E>   the type of the list's elements
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public class ListProperty<E> extends AbstractObservableList<E> implements WritableListProperty<E> {

    private final List<E> impl;

    @Nullable
    private transient ListBinding<?, E> binding;
    private transient boolean inBoundUpdate;

    /**
     * Creates a new {@code ListProperty}.
     *
     * @since   0.1.0
     */
    public ListProperty() {
        this(new ArrayList<>());
    }

    /**
     * Creates a new {@code ListProperty}.
     *
     * @param elements  the initial elements for the list
     *
     * @since   0.1.0
     */
    @SafeVarargs
    public ListProperty(E... elements) {
        this(Arrays.stream(elements).collect(Collectors.toCollection(() -> new ArrayList<>(elements.length))));
    }

    private ListProperty(List<E> impl) {
        super();
        this.impl = impl;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final void bindTo(ObservableList<E> observable) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new ListBinding<>(this::onBindingInvalidated, observable, Function.identity());

        try {
            this.inBoundUpdate = true;

            try (ChangeBuilder ignored = this.beginChange()) {
                this.setAll(observable);
            }
        } finally {
            this.inBoundUpdate = false;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final <S> void bindTo(ObservableList<S> observable, Function<S, E> transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new ListBinding<>(this::onBindingInvalidated, observable, transform);

        try {
            this.inBoundUpdate = true;

            try (ChangeBuilder ignored = this.beginChange()) {
                this.clear();
                observable.forEach(it -> this.add(transform.apply(it)));
            }
        } finally {
            this.inBoundUpdate = false;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized boolean isBound() {
        return (this.binding != null);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean isWritable() {
        return !this.isBound();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void unbind() {
        if (!this.isBound()) throw new IllegalStateException();

        this.binding.release();
        this.binding = null;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    protected final void addImpl(int index, @Nullable E element) {
        if (this.binding != null && !this.inBoundUpdate) throw new IllegalStateException("A bound property's value may not be set explicitly");
        this.impl.add(index, element);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Nullable
    @Override
    protected final E removeImpl(int index) {
        if (this.binding != null && !this.inBoundUpdate) throw new IllegalStateException("A bound property's value may not be set explicitly");
        return this.impl.remove(index);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Nullable
    @Override
    protected final E setImpl(int index, @Nullable E element) {
        if (this.binding != null && !this.inBoundUpdate) throw new IllegalStateException("A bound property's value may not be set explicitly");
        return this.impl.set(index, element);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final E get(int index) {
        return this.impl.get(index);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final int size() {
        return this.impl.size();
    }

    void onBindingInvalidated() {
        assert (this.binding != null);

        List<ObservableList.Change<E>> changes = this.binding.getChanges();

        try {
            this.inBoundUpdate = true;

            try (ChangeBuilder ignored = this.beginChange()) {
                for (var change : changes) {
                    if (change instanceof Change.Permutation) {
                        Change.Permutation<E> perm = (Change.Permutation<E>) change;
                        List<Integer> indices = perm.getIndices();

                        if (this.size() != indices.size()) throw new IndexOutOfBoundsException();

                        List<E> copy = List.copyOf(this);

                        for (int i = 0; i < this.size(); i++) {
                            this.set(indices.get(i), copy.get(i));
                        }
                    } else if (change instanceof Change.Update) {
                        Change.Update<E> update = (Change.Update<E>) change;

                        for (var localChange : update.getLocalChanges()) {
                            if (localChange instanceof LocalChange.Insertion) {
                                LocalChange.Insertion<E> insertion = (LocalChange.Insertion<E>) localChange;

                                if (this.size() < insertion.getIndex()) throw new IndexOutOfBoundsException();

                                List<E> elements = insertion.getElements();
                                int offset = insertion.getIndex();

                                for (int i = 0; i < elements.size(); i++) {
                                    this.addAll(offset + i, elements);
                                }
                            } else if (localChange instanceof LocalChange.Removal) {
                                LocalChange.Removal<E> removal = (LocalChange.Removal<E>) localChange;

                                if (this.size() < removal.getIndex() + removal.getElements().size()) throw new IndexOutOfBoundsException();

                                List<E> elements = removal.getElements();
                                int offset = removal.getIndex();

                                for (int i = 0; i < elements.size(); i++) {
                                    this.remove(offset);
                                }
                            } else if (localChange instanceof LocalChange.Update) {
                                LocalChange.Update<E> localUpdate = (LocalChange.Update<E>) localChange;

                                if (this.size() < localUpdate.getIndex() + localUpdate.getElements().size()) throw new IndexOutOfBoundsException();

                                List<E> elements = localUpdate.getElements();
                                int offset = localUpdate.getIndex();

                                for (int i = 0; i < elements.size(); i++) {
                                    this.set(offset + i, elements.get(i));
                                }
                            } else {
                                throw new IllegalStateException();
                            }
                        }
                    } else {
                        throw new IllegalStateException();
                    }
                }
            }
        } finally {
            this.inBoundUpdate = false;
        }
    }

}