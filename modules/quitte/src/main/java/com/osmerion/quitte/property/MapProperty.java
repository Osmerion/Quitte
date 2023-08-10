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

import java.util.*;
import java.util.function.BiFunction;

import com.osmerion.quitte.collections.*;
import com.osmerion.quitte.internal.binding.MapBinding;
import org.jspecify.annotations.Nullable;

/**
 * A {@link Map} property.
 *
 * @param <K>   the type of the map's elements
 * @param <V>   the type of the map's elements
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public class MapProperty<K extends @Nullable Object, V extends @Nullable Object> extends AbstractObservableMap<K, V> implements WritableMapProperty<K, V> {

    private final Map<K, V> impl;

    @Nullable
    private transient MapBinding<?, ?, K, V> binding;
    private transient boolean inBoundUpdate;

    /**
     * Creates a new {@code MapProperty}.
     *
     * @since   0.1.0
     */
    public MapProperty() {
        super();
        this.impl = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final synchronized void bindTo(ObservableMap<K, V> observable) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new MapBinding<>(this::onBindingInvalidated, observable, Map::entry);

        try {
            this.inBoundUpdate = true;

            try (ChangeBuilder ignored = this.beginChange()) {
                this.clear();
                this.putAll(observable);
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
    public final synchronized <S, T> void bindTo(ObservableMap<S, T> observable, BiFunction<S, T, Entry<K, V>> transform) {
        if (this.isBound()) throw new IllegalStateException();
        this.binding = new MapBinding<>(this::onBindingInvalidated, observable, transform);

        try {
            this.inBoundUpdate = true;

            try (ChangeBuilder ignored = this.beginChange()) {
                this.clear();
                observable.forEach((k, v) -> {
                    Map.Entry<K, V> it = transform.apply(k, v);
                    this.put(it.getKey(), it.getValue());
                });
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

    @Nullable
    @Override
    protected V putImpl(@Nullable K key, @Nullable V value) {
        if (this.binding != null && !this.inBoundUpdate) throw new IllegalStateException("A bound property's value may not be set explicitly");
        return this.impl.put(key, value);
    }

    @Nullable
    private transient Set<Entry<K, V>> entrySet;

    @Override
    protected Set<Entry<K, V>> entrySetImpl() {
        if (this.entrySet == null) this.entrySet = new WrappingObservableEntrySet(this.impl.entrySet());
        return this.entrySet;
    }

    void onBindingInvalidated() {
        assert (this.binding != null);

        List<? extends MapChangeListener.Change<K, V>> changes = this.binding.getChanges();

        try {
            this.inBoundUpdate = true;

            try (ChangeBuilder ignored = this.beginChange()) {
                for (var change : changes) {
                    this.putAll(change.addedElements());
                    change.removedElements().forEach(this::remove);
                    change.updatedElements().forEach((k, u) -> this.put(k, u.newValue()));
                }
            }
        } finally {
            this.inBoundUpdate = false;
        }
    }

    /**
     * An observable wrapper for a map's entry-set.
     *
     * @since   0.1.0
     */
    protected final class WrappingObservableEntrySet extends AbstractObservableSet<Entry<K, V>> {

        private final Set<Entry<K, V>> impl;

        public WrappingObservableEntrySet(Set<Entry<K, V>> impl) {
            super();
            this.impl = impl;
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new Iterator<>() {

                private final Iterator<Entry<K, V>> impl = WrappingObservableEntrySet.this.impl.iterator();
                @Nullable private Entry<K, V> cursor;

                @Override
                public boolean hasNext() {
                    return this.impl.hasNext();
                }

                @Override
                public Entry<K, V> next() {
                    return new WrappingObservableEntry(this.cursor = this.impl.next());
                }

                @Override
                public void remove() {
                    this.impl.remove();

                    try (AbstractObservableMap<K, V>.ChangeBuilder changeBuilder = MapProperty.this.beginChange()) {
                        //noinspection ConstantConditions
                        changeBuilder.logRemove(this.cursor.getKey(), this.cursor.getValue());
                    }
                }

            };
        }

        @Override
        protected boolean addImpl(@Nullable Entry<K, V> element) {
            throw new UnsupportedOperationException();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected boolean removeImpl(@Nullable Object element) {
            if (MapProperty.this.binding != null && !MapProperty.this.inBoundUpdate)
                throw new IllegalStateException("A bound property's value may not be set explicitly");

            Objects.requireNonNull(element);

            if (this.impl.remove((Map.Entry<K, V>) element)) {
                try (AbstractObservableMap<K, V>.ChangeBuilder changeBuilder = MapProperty.this.beginChange()) {
                    /*
                     * Technically, this cast is wrong and there is a tiny chance that it could fail if the EntrySet of
                     * the backing map implementation does not perform identity-checks in it's Set#remove(Object)
                     * method.
                     *
                     * However, since the possibility of encountering such an edge-case is extremely tiny and arguably
                     * a misuse of the API, we will not provide a workaround at this time. Should this ever become a
                     * more serious problem, this implementation will need to be reconsidered.
                     */
                    Entry<K, V> entry = (Entry<K, V>) element;
                    changeBuilder.logRemove(entry.getKey(), entry.getValue());
                }

                return true;
            }

            return false;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Object[] toArray() {
            Object[] array = this.impl.toArray();
            for (int i = 0; i < array.length; i++) array[i] = new WrappingObservableEntry((Entry<K, V>) array[i]);

            return array;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] a) {
            T[] array = this.impl.toArray(a);
            for (int i = 0; i < array.length; i++) array[i] = (T) new WrappingObservableEntry((Entry<K, V>) array[i]);

            return array;
        }

        @Override public boolean contains(Object o) { return this.impl.contains(o); }
        @Override public boolean containsAll(Collection<?> c) { return this.impl.containsAll(c); }
        @Override public boolean isEmpty() { return this.impl.isEmpty(); }
        @Override public boolean retainAll(Collection<?> c) { return this.impl.retainAll(c); }
        @Override public int size() {
            return this.impl.size();
        }

    }

    /**
     * A simple observable wrapper for an {@link java.util.Map.Entry}.
     *
     * @since   0.1.0
     */
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    protected final class WrappingObservableEntry implements Entry<K, V> {

        private final Entry<K, V> impl;

        /**
         * Creates a new wrapper for the given entry.
         *
         * @param impl  the entry to be wrapped
         */
        private WrappingObservableEntry(Entry<K, V> impl) {
            this.impl = impl;
        }

        @Override
        public K getKey() {
            return this.impl.getKey();
        }

        @Override
        public V getValue() {
            return this.impl.getValue();
        }

        /**
         * {@inheritDoc}
         *
         * @since   0.1.0
         */
        @Override
        public V setValue(V value) {
            if (MapProperty.this.binding != null && !MapProperty.this.inBoundUpdate)
                throw new IllegalStateException("A bound property's value may not be set explicitly");

            V prevValue = this.impl.setValue(value);

            try (ChangeBuilder changeBuilder = MapProperty.this.beginChange()) {
                changeBuilder.logUpdate(this.getKey(), prevValue, value);
            }

            return prevValue;
        }

        @Override public boolean equals(Object obj) { return this.impl.equals(obj); }
        @Override public int hashCode() { return this.impl.hashCode(); }
        @Override public String toString() { return this.impl.toString(); }

    }

}