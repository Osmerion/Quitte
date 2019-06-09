package com.github.osmerion.quitte.internal.binding;

import java.util.function.Function;
import com.github.osmerion.quitte.property.WritableProperty;
import com.github.osmerion.quitte.value.ObservableValue;
import com.github.osmerion.quitte.value.change.ChangeListener;

public final class MutatingBinding<S, T> implements Binding {

    private final ObservableValue<S> source;
    private final ChangeListener<S> listener;

    public MutatingBinding(WritableProperty<T> target, ObservableValue<S> source, Function<S, T> transform) {
        this.source = source;
        target.setValue(transform.apply(source.getValue()));
        this.source.addBoxedListener(this.listener = ((observable, oldValue, newValue) -> target.setValue(transform.apply(newValue))));
    }

    @Override
    public void release() {
        this.source.removeBoxedListener(this.listener);
    }

}