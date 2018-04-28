package com.github.osmerion.quitte.property;

import com.github.osmerion.quitte.value.ObservableValue;
import com.github.osmerion.quitte.value.WritableValue;

/**
 * TODO doc
 *
 * @param <T>
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface WritableProperty<T> extends ReadableProperty<T>, WritableValue<T> {

    /**
     * Binds this property to the given observable value.
     *
     * <p>While a property is bound, its value will be equal to the observable value. Any attempt to set the value of a
     * bound property explicitly will fail. A bound property may be unbound by calling {@link #unbind()}.</p>
     *
     * @param observable    the observable to bind this property to
     *
     * @since   0.1.0
     */
    void bind(ObservableValue<T> observable);

    /**
     * Unbinds this property.
     *
     * @since   0.1.0
     */
    void unbind();

}