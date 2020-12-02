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
package com.osmerion.quitte.property;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

import com.osmerion.quitte.collections.AbstractObservableSet;
import com.osmerion.quitte.collections.ObservableSet;
import com.osmerion.quitte.collections.SetChangeListener;
import com.osmerion.quitte.internal.binding.SetBinding;

/**
 * A {@link Set} property.
 *
 * @param <E>   the type of the set's elements
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public class SetProperty<E> extends AbstractObservableSet<E> implements WritableSetProperty<E> {

    private final Set<E> impl;

    @Nullable
    private transient SetBinding<?, E> binding;
    private transient boolean inBoundUpdate;

    /**
     * Creates a new {@code SetProperty}.
     *
     * @since   0.1.0
     */
    public SetProperty() {
        this(new HashSet<>());
    }

    /**
     * Creates a new {@code SetProperty}.
     *
     * @param elements  the initial elements for the set
     *
     * @since   0.1.0
     */
    @SafeVarargs
    public SetProperty(E... elements) {
        this(Arrays.stream(elements).collect(Collectors.toCollection(() -> new HashSet<>(elements.length))));
    }

    private SetProperty(Set<E> impl) {
        super();
        this.impl = impl;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final void bindTo(ObservableSet<E> observable) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new SetBinding<>(this::onBindingInvalidated, observable, Function.identity());

        try {
            this.inBoundUpdate = true;

            try (ChangeBuilder ignored = this.beginChange()) {
                this.clear();
                this.addAll(observable);
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
    public final <S> void bindTo(ObservableSet<S> observable, Function<S, E> transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new SetBinding<>(this::onBindingInvalidated, observable, transform);

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
    protected boolean addImpl(@Nullable E element) {
        if (this.binding != null && !this.inBoundUpdate) throw new IllegalStateException("A bound property's value may not be set explicitly");
        return this.impl.add(element);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    protected boolean removeImpl(@Nullable Object element) {
        if (this.binding != null && !this.inBoundUpdate) throw new IllegalStateException("A bound property's value may not be set explicitly");

        //noinspection SuspiciousMethodCalls
        return this.impl.remove(element);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            private final Iterator<E> impl = SetProperty.this.impl.iterator();
            @Nullable private E cursor;

            @Override
            public boolean hasNext() {
                return this.impl.hasNext();
            }

            @Override
            public E next() {
                return (this.cursor = this.impl.next());
            }

            @Override
            public void remove() {
                if (SetProperty.this.binding != null && !SetProperty.this.inBoundUpdate)
                    throw new IllegalStateException("A bound property's value may not be set explicitly");

                try (ChangeBuilder changeBuilder = SetProperty.this.beginChange()) {
                    this.impl.remove();
                    changeBuilder.logRemove(this.cursor);
                }
            }

        };
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

    @SuppressWarnings("deprecation")
    void onBindingInvalidated() {
        assert (this.binding != null);

        List<SetChangeListener.Change<E>> changes = this.binding.getChanges();

        try {
            this.inBoundUpdate = true;

            try (ChangeBuilder ignored = this.beginChange()) {
                changes.forEach(change -> change.applyTo(this));
            }
        } finally {
            this.inBoundUpdate = false;
        }
    }

}