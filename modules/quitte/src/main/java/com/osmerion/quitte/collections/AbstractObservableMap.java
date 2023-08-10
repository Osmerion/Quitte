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

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import com.osmerion.quitte.InvalidationListener;
import org.jspecify.annotations.Nullable;

/**
 * A basic implementation for a modifiable {@link ObservableMap}.
 *
 * @param <K>   the type of the map's keys
 * @param <V>   the type of the map's values
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class AbstractObservableMap<K extends @Nullable Object, V extends @Nullable Object> extends AbstractMap<K, V> implements ObservableMap<K, V> {

    private transient final CopyOnWriteArraySet<MapChangeListener<? super K, ? super V>> changeListeners = new CopyOnWriteArraySet<>();
    private transient final CopyOnWriteArraySet<InvalidationListener> invalidationListeners = new CopyOnWriteArraySet<>();

    @Nullable
    private transient ChangeBuilder changeBuilder;

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addChangeListener(MapChangeListener<? super K, ? super V> listener) {
        return this.changeListeners.add(Objects.requireNonNull(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeChangeListener(MapChangeListener<? super K, ? super V> listener) {
        return this.changeListeners.remove(Objects.requireNonNull(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean addInvalidationListener(InvalidationListener listener) {
        return this.invalidationListeners.add(Objects.requireNonNull(listener));
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    public final boolean removeInvalidationListener(InvalidationListener listener) {
        return this.invalidationListeners.remove(Objects.requireNonNull(listener));
    }

    /**
     * Pushes a frame on the current {@link ChangeBuilder} instance or creates a new one.
     *
     * @return  the current {@code ChangeBuilder}
     *
     * @since   0.1.0
     */
    protected final ChangeBuilder beginChange() {
        if (this.changeBuilder == null) this.changeBuilder = new ChangeBuilder();
        this.changeBuilder.depth++;

        return this.changeBuilder;
    }

    @Nullable
    private transient ObservableSet<Map.Entry<K, V>> entrySet;

    /**
     * {@inheritDoc}
     *
     * @since   0.8.0
     */
    @Override
    public ObservableSet<Entry<K, V>> entrySet() {
        ObservableSet<Map.Entry<K, V>> entrySet = this.entrySet;

        if (entrySet == null) {
            entrySet = new WrappingObservableEntrySet(this.entrySetImpl()) {

                @SuppressWarnings({"FieldCanBeLocal", "unused"})
                private final MapChangeListener<K, V> changeListener;

                {
                    AbstractObservableMap.this.addChangeListener(new WeakMapChangeListener<>(this.changeListener = (observable, change) -> {
                        try (ChangeBuilder changeBuilder = this.beginChange()) {
                            change.addedElements().forEach((key, value) -> changeBuilder.logAdd(new SimpleEntry<>(key, value)));
                            change.removedElements().forEach((key, value) -> changeBuilder.logRemove(new SimpleEntry<>(key, value)));
                            change.updatedElements().forEach((key, update) -> {
                                changeBuilder.logRemove(new SimpleEntry<>(key, update.oldValue()));
                                changeBuilder.logAdd(new SimpleEntry<>(key, update.newValue()));
                            });
                        }
                    }));
                }

                @Override
                protected boolean addImpl(@Nullable Entry<K, V> element) {
                    return false;
                }

                @SuppressWarnings("unchecked")
                @Override
                protected boolean removeImpl(@Nullable Object element) {
                    Objects.requireNonNull(element);

                    if (AbstractObservableMap.this.entrySetImpl().remove((Map.Entry<K, V>) element)) {
                        try (AbstractObservableMap<K, V>.ChangeBuilder changeBuilder = AbstractObservableMap.this.beginChange()) {
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

            };

            this.entrySet = entrySet;
        }

        return entrySet;
    }

    @Nullable
    private transient ObservableSet<K> keySet;

    /**
     * {@inheritDoc}
     *
     * @since   0.8.0
     */
    @Override
    public ObservableSet<K> keySet() {
        ObservableSet<K> keySet = this.keySet;

        if (keySet == null) {
            keySet = new AbstractObservableSet<>() {

                @SuppressWarnings({"FieldCanBeLocal", "unused"})
                private final MapChangeListener<K, V> changeListener;

                {
                    AbstractObservableMap.this.addChangeListener(new WeakMapChangeListener<>(this.changeListener = (observable, change) -> {
                        try (ChangeBuilder changeBuilder = this.beginChange()) {
                            change.addedElements().keySet().forEach(changeBuilder::logAdd);
                            change.removedElements().keySet().forEach(changeBuilder::logRemove);
                        }
                    }));
                }

                @Override
                protected boolean addImpl(K element) {
                    throw new UnsupportedOperationException();
                }

                @Override
                protected boolean removeImpl(@Nullable Object element) {
                    int s = AbstractObservableMap.this.size();
                    AbstractObservableMap.this.remove(element);

                    return s != AbstractObservableMap.this.size();
                }

                @Override
                public Iterator<K> iterator() {
                    return new Iterator<>() {

                        private final Iterator<ObservableMap.Entry<K, V>> impl = AbstractObservableMap.this.entrySetImpl().iterator();

                        @Override
                        public boolean hasNext() {
                            return this.impl.hasNext();
                        }

                        @Override
                        public K next() {
                            return this.impl.next().getKey();
                        }

                        @Override
                        public void remove() {
                            this.impl.remove();
                        }

                    };
                }

                @Override
                public int size() {
                    return AbstractObservableMap.this.size();
                }

            };

            this.keySet = keySet;
        }

        return keySet;
    }

    protected abstract Set<Entry<K, V>> entrySetImpl();

    @Nullable
    protected abstract V putImpl(@Nullable K key, @Nullable V value);

    @Override
    public final V put(@Nullable K key, @Nullable V value) {
        V prevValue;

        if (this.containsKey(key)) {
            prevValue = this.putImpl(key, value);

            /* Only notify the listeners if the value has actually changed. */
            if ((prevValue == null && value != null) || (prevValue != null && !prevValue.equals(value))) {
                try (ChangeBuilder changeBuilder = this.beginChange()) {
                    changeBuilder.logUpdate(key, prevValue, value);
                }
            }
        } else {
            prevValue = this.putImpl(key, value);

            try (ChangeBuilder changeBuilder = this.beginChange()) {
                changeBuilder.logAdd(key, value);
            }
        }

        return prevValue;
    }

    /**
     * A builder to be used to register changes made to this set.
     *
     * @see #beginChange()
     * @see #close()
     *
     * @since   0.1.0
     */
    protected final class ChangeBuilder implements AutoCloseable {

        @Nullable
        private HashMap<K, V> added, removed;

        @Nullable
        private HashMap<K, MapChangeListener.Change.Update<V>> updated;

        private int depth = 0;

        private ChangeBuilder() {}

        /**
         * Pops a frame from this builder.
         *
         * <p>If the last frame has been popped, the changes are committed (i.e. post-processed and compressed) and sent
         * to this listeners of this observable.</p>
         *
         * @since   0.1.0
         */
        @Override
        public void close() {
            if (this.depth < 1) throw new IllegalStateException();

            this.depth--;

            if (this.depth == 0) {
                AbstractObservableMap.this.changeBuilder = null;
                if ((this.added == null || this.added.isEmpty()) &&
                    (this.removed == null || this.removed.isEmpty()) &&
                    (this.updated == null || this.updated.isEmpty())) return;

                var change = new MapChangeListener.Change<>(this.added, this.removed, this.updated);

                for (MapChangeListener<? super K, ? super V> listener : AbstractObservableMap.this.changeListeners) {
                    if (listener.isInvalid()) {
                        AbstractObservableMap.this.changeListeners.remove(listener);
                        continue;
                    }

                    listener.onChanged(AbstractObservableMap.this, change);
                    if (listener.isInvalid()) AbstractObservableMap.this.changeListeners.remove(listener);
                }

                for (var listener : AbstractObservableMap.this.invalidationListeners) {
                    if (listener.isInvalid()) {
                        AbstractObservableMap.this.invalidationListeners.remove(listener);
                        continue;
                    }

                    listener.onInvalidation(AbstractObservableMap.this);
                    if (listener.isInvalid()) AbstractObservableMap.this.invalidationListeners.remove(listener);
                }
            }
        }

        /**
         * Logs the addition of the given entry.
         *
         * @param key   the key that was added
         * @param value the value that was added
         *
         * @since   0.1.0
         */
        public void logAdd(@Nullable K key, @Nullable V value) {
            if (this.added != null && this.added.containsKey(key)) throw new IllegalArgumentException();
            if (this.updated != null && this.updated.containsKey(key)) throw new IllegalArgumentException();
            
            if (this.removed == null || !this.removed.containsKey(key)) {
                if (this.added == null) this.added = new HashMap<>();
                this.added.put(key, value);
            } else {
                this.removed.remove(key);
            }
        }

        /**
         * Logs the removal of the given entry.
         *
         * @param key   the key that was removed
         * @param value the value that was removed
         *
         * @since   0.1.0
         */
        public void logRemove(@Nullable K key, @Nullable V value) {
            if (this.updated != null) this.updated.remove(key);
            
            if (this.removed == null) this.removed = new HashMap<>();
            if (this.added == null || !this.added.remove(key, value)) this.removed.put(key, value);
        }

        /**
         * Logs the update of the given entry.
         *
         * @param key       the key of the entry that was updated
         * @param oldValue  the old value
         * @param newValue  the new value
         *
         * @since   0.1.0
         */
        public void logUpdate(@Nullable K key, @Nullable V oldValue, @Nullable V newValue) {
            if (this.removed != null && this.removed.containsKey(key)) throw new IllegalArgumentException();

            if (this.added != null && this.added.containsKey(key)) {
                this.added.put(key, newValue);
                return;
            }

            if (this.updated == null) this.updated = new HashMap<>();
            this.updated.put(key, new MapChangeListener.Change.Update<>(oldValue, newValue));
        }

    }

    /**
     * A basic implementation for a wrapper of a map's entry-set.
     *
     * @since   0.1.0
     */
    protected abstract class AbstractObservableEntrySet extends AbstractObservableSet<Entry<K, V>> implements ObservableSet<Entry<K, V>> {

        protected final Set<Entry<K, V>> impl;

        private AbstractObservableEntrySet(Set<Entry<K, V>> impl) {
            this.impl = impl;
        }

        public abstract Entry<K, V> wrap(Entry<K, V> entry);

        @Override
        public final Iterator<Entry<K, V>> iterator() {
            return new Iterator<>() {

                private final Iterator<Entry<K, V>> impl = AbstractObservableEntrySet.this.impl.iterator();
                @Nullable private Entry<K, V> cursor;

                @Override
                public boolean hasNext() {
                    return this.impl.hasNext();
                }

                @Override
                public Entry<K, V> next() {
                    return AbstractObservableEntrySet.this.wrap(this.cursor = this.impl.next());
                }

                @Override
                public void remove() {
                    this.impl.remove();

                    try (AbstractObservableMap<K, V>.ChangeBuilder changeBuilder = AbstractObservableMap.this.beginChange()) {
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
        public final Object[] toArray() {
            Object[] array = this.impl.toArray();
            for (int i = 0; i < array.length; i++) array[i] = this.wrap((Entry<K, V>) array[i]);

            return array;
        }

        @SuppressWarnings("unchecked")
        @Override
        public final <T> T[] toArray(T[] a) {
            T[] array = this.impl.toArray(a);
            for (int i = 0; i < array.length; i++) array[i] = (T) this.wrap((Entry<K, V>) array[i]);

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
     * An observable wrapper for a map's entry-set.
     *
     * @since   0.1.0
     */
    protected abstract class WrappingObservableEntrySet extends AbstractObservableEntrySet {

        public WrappingObservableEntrySet(Set<Entry<K, V>> impl) {
            super(impl);
        }

//        @SuppressWarnings("unchecked")
//        @Override
//        public boolean remove(Object element) {
//            if (this.impl.remove(element)) {
//                try (ChangeBuilder changeBuilder = AbstractObservableMap.this.beginChange()) {
//                    /*
//                     * Technically, this cast is wrong and there is a tiny chance that it could fail if the EntrySet of
//                     * the backing map implementation does not perform identity-checks in it's Set#remove(Object)
//                     * method.
//                     *
//                     * However, since the possibility of encountering such an edge-case is extremely tiny and arguably
//                     * a misuse of the API, we will not provide a workaround at this time. Should this ever become a
//                     * more serious problem, this implementation will need to be reconsidered.
//                     */
//                    Entry<K, V> entry = (Entry<K, V>) element;
//                    changeBuilder.logRemove(entry.getKey(), entry.getValue());
//                }
//
//                return true;
//            }
//
//            return false;
//        }

        @Override
        public Entry<K, V> wrap(Entry<K, V> entry) {
            return new WrappingObservableEntry(entry);
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
            V prevValue = this.impl.setValue(value);

            try (ChangeBuilder changeBuilder = AbstractObservableMap.this.beginChange()) {
                changeBuilder.logUpdate(this.getKey(), prevValue, value);
            }

            return prevValue;
        }

        @Override public boolean equals(Object obj) { return this.impl.equals(obj); }
        @Override public int hashCode() { return this.impl.hashCode(); }
        @Override public String toString() { return this.impl.toString(); }

    }

}