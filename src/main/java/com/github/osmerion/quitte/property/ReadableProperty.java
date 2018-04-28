package com.github.osmerion.quitte.property;

import com.github.osmerion.quitte.value.ObservableValue;

/**
 * A basic readable property.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface ReadableProperty<T> extends ObservableValue<T> {

    /**
     * TODO doc
     *
     * @return
     *
     * @since   0.1.0
     */
    ReadableProperty<T> asReadOnlyProperty();

    /**
     * Returns {@code true} if this property is bound to an observable value, or {@code false otherwise}.
     *
     * @return  {@code true} if this property is bound to an observable value, or {@code false otherwise}
     *
     * @since   0.1.0
     */
    boolean isBound();

    /**
     * Returns {@code true} if this property is writable, or {@code false} otherwise.
     *
     * @return {@code true} if this property is writable, or {@code false} otherwise
     *
     * @since   0.1.0
     */
    boolean isWritable();

}