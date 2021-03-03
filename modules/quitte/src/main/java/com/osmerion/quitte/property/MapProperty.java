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

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import javax.annotation.Nullable;

import com.osmerion.quitte.collections.AbstractObservableMap;
import com.osmerion.quitte.collections.ObservableMap;
import com.osmerion.quitte.internal.binding.MapBinding;

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
public class MapProperty<K, V> extends AbstractObservableMap<K, V> implements WritableMapProperty<K, V> {

    private final Map<K, V> impl;

    @Nullable
    private transient MapBinding<?, ?, K, V> binding;
    private transient boolean inBoundUpdate;

    @Nullable
    private transient Set<Entry<K, V>> entrySet;

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

    @Override
    public Set<Entry<K, V>> entrySet() {
        if (this.entrySet == null) this.entrySet = new WrappingObservableEntrySet(this.impl.entrySet());
        return this.entrySet;
    }

    void onBindingInvalidated() {
        assert (this.binding != null);

        List<? extends ObservableMap.Change<K, V>> changes = this.binding.getChanges();

        try {
            this.inBoundUpdate = true;

            try (ChangeBuilder ignored = this.beginChange()) {
                for (var change : changes) {
                    this.putAll(change.getAddedElements());
                    change.getRemovedElements().forEach(this::remove);
                    change.getUpdatedElements().forEach((k, u) -> this.put(k, u.getNewValue()));
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
    protected final class WrappingObservableEntrySet extends AbstractSet<Entry<K, V>> {

        protected final Set<Entry<K, V>> impl;

        public WrappingObservableEntrySet(Set<Entry<K, V>> impl) {
            super();
            this.impl = impl;
        }

        @Override
        public final Iterator<Entry<K, V>> iterator() {
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

        @SuppressWarnings("unchecked")
        @Override
        public boolean remove(Object element) {
            if (MapProperty.this.binding != null && !MapProperty.this.inBoundUpdate)
                throw new IllegalStateException("A bound property's value may not be set explicitly");

            if (this.impl.remove(element)) {
                try (ChangeBuilder changeBuilder = MapProperty.this.beginChange()) {
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
        public final Object[] toArray() {
            Object[] array = this.impl.toArray();
            for (int i = 0; i < array.length; i++) array[i] = new WrappingObservableEntry((Entry<K, V>) array[i]);

            return array;
        }

        @SuppressWarnings({ "unchecked", "SuspiciousToArrayCall" })
        @Override
        public final <T> T[] toArray(T[] a) {
            T[] array = this.impl.toArray(a);
            for (int i = 0; i < array.length; i++) array[i] = (T) new WrappingObservableEntry((Entry<K, V>) array[i]);

            return array;
        }

        @Override public final boolean contains(Object o) { return this.impl.contains(o); }
        @Override public final boolean containsAll(Collection<?> c) { return this.impl.containsAll(c); }
        @Override public final boolean isEmpty() { return this.impl.isEmpty(); }
        @Override public final boolean retainAll(Collection<?> c) { return this.impl.retainAll(c); }
        @Override public final int size() {
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