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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;

import com.osmerion.quitte.collections.AbstractObservableDeque;
import com.osmerion.quitte.collections.DequeChangeListener;
import com.osmerion.quitte.collections.ObservableDeque;
import com.osmerion.quitte.internal.binding.DequeBinding;

/**
 * A {@link Deque} property.
 *
 * @param <E>   the type of the deque's elements
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public class DequeProperty<E> extends AbstractObservableDeque<E> implements WritableDequeProperty<E> {

    private final Deque<E> impl;

    @Nullable
    private transient DequeBinding<?, E> binding;

    /**
     * Creates a new {@code DequeProperty}.
     *
     * @since   0.1.0
     */
    public DequeProperty() {
        super();
        this.impl = new ArrayDeque<>();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final void bindTo(ObservableDeque<E> observable) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new DequeBinding<>(this::onBindingInvalidated, observable, Function.identity());
        this.onBindingInvalidated();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final <S> void bindTo(ObservableDeque<S> observable, Function<S, E> transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new DequeBinding<>(this::onBindingInvalidated, observable, transform);
        this.onBindingInvalidated();
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
    protected final void addFirstImpl(@Nullable E element) {
        if (this.binding != null) throw new IllegalStateException("A bound property's value may not be set explicitly");
        this.impl.addFirst(element);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    protected final void addLastImpl(@Nullable E element) {
        if (this.binding != null) throw new IllegalStateException("A bound property's value may not be set explicitly");
        this.impl.addLast(element);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    protected final boolean offerFirstImpl(@Nullable E element) {
        if (this.binding != null) throw new IllegalStateException("A bound property's value may not be set explicitly");
        return this.impl.offerFirst(element);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    protected final boolean offerLastImpl(@Nullable E element) {
        if (this.binding != null) throw new IllegalStateException("A bound property's value may not be set explicitly");
        return this.impl.offerLast(element);
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    protected final E removeFirstImpl() {
        if (this.binding != null) throw new IllegalStateException("A bound property's value may not be set explicitly");
        return this.impl.removeFirst();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    protected final E removeLastImpl() {
        if (this.binding != null) throw new IllegalStateException("A bound property's value may not be set explicitly");
        return this.impl.removeLast();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    protected final E pollFirstImpl() {
        if (this.binding != null) throw new IllegalStateException("A bound property's value may not be set explicitly");
        return this.impl.pollFirst();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    protected final E pollLastImpl() {
        if (this.binding != null) throw new IllegalStateException("A bound property's value may not be set explicitly");
        return this.impl.pollLast();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final E element() {
        return this.impl.element();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final Iterator<E> descendingIterator() {
        return new Iterator<>() {

            private final Iterator<E> impl = DequeProperty.this.impl.descendingIterator();
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
                if (DequeProperty.this.binding != null) throw new IllegalStateException("A bound property's value may not be set explicitly");

                try (ChangeBuilder changeBuilder = DequeProperty.this.beginChange()) {
                    this.impl.remove();
                    changeBuilder.logRemove(DequeChangeListener.Site.OPAQUE, this.cursor);
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
    public final E getFirst() {
        return this.impl.getFirst();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final E getLast() {
        return this.impl.getLast();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final Iterator<E> iterator() {
        return new Iterator<>() {

            private final Iterator<E> impl = DequeProperty.this.impl.iterator();
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
                if (DequeProperty.this.binding != null) throw new IllegalStateException("A bound property's value may not be set explicitly");

                try (ChangeBuilder changeBuilder = DequeProperty.this.beginChange()) {
                    this.impl.remove();
                    changeBuilder.logRemove(DequeChangeListener.Site.OPAQUE, this.cursor);
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
    public final E peek() {
        return this.impl.peek();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final E peekFirst() {
        return this.impl.peekFirst();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final E peekLast() {
        return this.impl.peekLast();
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

        List<DequeChangeListener.Change<E>> changes = this.binding.getChanges();

        try (ChangeBuilder ignored = this.beginChange()) {
            changes.forEach(change -> change.applyTo(this));
        }
    }

}