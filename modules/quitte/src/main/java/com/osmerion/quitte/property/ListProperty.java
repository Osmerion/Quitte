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
package com.osmerion.quitte.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.osmerion.quitte.collections.AbstractObservableList;
import com.osmerion.quitte.collections.ListChangeListener;
import com.osmerion.quitte.collections.ObservableList;
import com.osmerion.quitte.internal.binding.ListBinding;
import org.jspecify.annotations.Nullable;

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
    public final synchronized void bindTo(ObservableList<E> observable) {
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
    public final synchronized <S> void bindTo(ObservableList<S> observable, Function<S, E> transform) {
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

        List<ListChangeListener.Change<E>> changes = this.binding.getChanges();

        try {
            this.inBoundUpdate = true;

            try (ChangeBuilder ignored = this.beginChange()) {
                for (var change : changes) {
                    if (change instanceof ListChangeListener.Change.Permutation<E> perm) {
                        List<Integer> indices = perm.indices();

                        if (this.size() != indices.size()) throw new IndexOutOfBoundsException();

                        List<E> copy = List.copyOf(this);

                        for (int i = 0; i < this.size(); i++) {
                            this.set(indices.get(i), copy.get(i));
                        }
                    } else if (change instanceof ListChangeListener.Change.Update<E> update) {
                        for (var localChange : update.localChanges()) {
                            if (localChange instanceof ListChangeListener.LocalChange.Insertion<E> insertion) {
                                if (this.size() < insertion.index()) throw new IndexOutOfBoundsException();

                                List<E> elements = insertion.elements();
                                int offset = insertion.index();

                                for (int i = 0; i < elements.size(); i++) {
                                    this.addAll(offset + i, elements);
                                }
                            } else if (localChange instanceof ListChangeListener.LocalChange.Removal<E> removal) {
                                if (this.size() < removal.index() + removal.elements().size()) throw new IndexOutOfBoundsException();

                                List<E> elements = removal.elements();
                                int offset = removal.index();

                                for (int i = 0; i < elements.size(); i++) {
                                    this.remove(offset);
                                }
                            } else if (localChange instanceof ListChangeListener.LocalChange.Update<E> localUpdate) {
                                if (this.size() < localUpdate.index() + localUpdate.newElements().size()) throw new IndexOutOfBoundsException();

                                List<E> elements = localUpdate.newElements();
                                int offset = localUpdate.index();

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