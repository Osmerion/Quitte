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
val packageName = "com.github.osmerion.quitte.expression"

Type.values().forEach {
    val type = it
    val typeParams = if (type === Type.OBJECT) "<T>" else ""

    template("${packageName.replace('.', '/')}/Lazy${type.abbrevName}Expression") {
        """package $packageName;

import java.util.Objects;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.github.osmerion.quitte.*;
import com.github.osmerion.quitte.functional.*;
import com.github.osmerion.quitte.internal.binding.*;
import com.github.osmerion.quitte.property.*;
import com.github.osmerion.quitte.value.*;
import com.github.osmerion.quitte.value.change.*;

/**
 * ${if (type === Type.OBJECT)
            "A generic lazy expression."
        else
            "A specialized lazy {@code ${type.raw}} expression."
        }
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class Lazy${type.abbrevName}Expression$typeParams extends Abstract${type.abbrevName}Expression$typeParams implements LazyValue {
${Type.values().joinToString(separator = "") { sourceType ->
            val sourceTypeParams = if (sourceType === Type.OBJECT) "<S>" else ""
            val transformTypeParams = when {
                sourceType === Type.OBJECT && type === Type.OBJECT -> "<S, T>"
                sourceType === Type.OBJECT -> "<S>"
                type === Type.OBJECT -> "<T>"
                else -> ""
            }

            """
    /**
     * Returns a new lazy expression which applies a given transformation to a given observable.
     *${if (sourceType === Type.OBJECT) "\n     * @param <S>           the type of the source value" else ""}${if (type === Type.OBJECT) "\n     * @param <T>           the type of the target value" else ""}
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new lazy expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static $transformTypeParams${if (transformTypeParams.isNotEmpty()) " " else ""}Lazy${type.abbrevName}Expression$typeParams of(Observable${sourceType.abbrevName}Value$sourceTypeParams observable, ${sourceType.abbrevName}2${type.abbrevName}Function$transformTypeParams transform) {
        return new Transform${if (type === Type.OBJECT) "<>" else ""}(ex -> new ${sourceType.abbrevName}2${type.abbrevName}Binding${if (sourceType === Type.OBJECT || type === Type.OBJECT) "<>" else ""}(ex::onDependencyInvalidated, observable, transform));
    }
"""}}
    /**
     * Returns a new lazy expression which aliases a child property of an observable.
     *
     * <p>The parent observable must never evaluate to {@code null}.</p>
     *
     * @param observable    the parent observable
     * @param selector      the function that selects the child property
     * @param <S>           the parent type
     * ${if (type === Type.OBJECT) "@param <T>           the child type\n    *" else ""}
     * @return  a new lazy expression which aliases a child property of an observable
     *
     * @since   0.1.0
     */
    public static <${if (type === Type.OBJECT) "S, T" else "S"}> Lazy${type.abbrevName}Expression$typeParams ofNested(ObservableObjectValue<S> observable, Function<S, Observable${type.abbrevName}Value$typeParams> selector) {
        return new Lazy${type.abbrevName}Expression${if (type === Type.OBJECT) "<>" else ""}() {

            final InvalidationListener nestedPropertyListener = ignored -> this.onDependencyInvalidated();

            {
                observable.addListener(ignored -> this.onDependencyInvalidated());

                ObjectChangeListener<S> parentChangeListener = (ignored, oldValue, newValue) -> {
                    if (oldValue != null) {
                        var nestedProperty = selector.apply(oldValue);
                        nestedProperty.removeListener(this.nestedPropertyListener);
                    }

                    var nestedProperty = selector.apply(Objects.requireNonNull(newValue));
                    nestedProperty.addListener(this.nestedPropertyListener);
                };
                observable.addListener(parentChangeListener);
                parentChangeListener.onChanged(observable, null, observable.get());
            }

${if (type === Type.OBJECT) "\n            @Nullable" else ""}
            @Override
            protected ${type.raw} recomputeValue() {
                var parent = observable.get();
                return selector.apply(Objects.requireNonNull(parent)).get();
            }

        };
    }

    private final SimpleObjectProperty<State> state = new SimpleObjectProperty<>(State.UNINITIALIZED) {

        @Override
        public void onChanged(@Nullable State prevValue, @Nullable State value) {
            //noinspection ConstantConditions
            if (!value.isValid()) Lazy${type.abbrevName}Expression.this.notifyInvalidationListeners();
        }

    };

    @Nullable
    private ${type.abbrevName}Supplier$typeParams provider = this::recomputeValue;
${if (type === Type.OBJECT) "\n    @Nullable" else ""}
    protected ${type.raw} value;

    /**
     * Returns a read-only view of this expression's state.
     *
     * @return  a read-only view of this expression's state
     *
     * @implNote    The view is not cached since calling this method is expected to be a rare operation.
     *
     * @since   0.1.0
     */
    public final ReadableObjectProperty<State> stateProperty() {
        return this.state.asReadOnlyProperty();
    }

    /**
     * Returns the current state of this expression.
     *
     * @return  the current state of this expression
     *
     * @since   0.1.0
     */
    public final State getState() {
        //noinspection ConstantConditions
        return this.state.get();
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override${if (type === Type.OBJECT) "\n    @Nullable" else ""}
    public final ${type.raw} get() {
        //noinspection ConstantConditions
        if (!this.state.get().isValid()) { 
            var provider = Objects.requireNonNull(this.provider);
            this.updateValue(provider.get(), !this.state.get().isValid());

            this.provider = null;
        }

        return this.value;
    }

    @Override${if (type === Type.OBJECT) "\n    @Nullable" else ""}
    final ${type.raw} getImpl() {
        return this.value;
    }

    @Override
    final void setImpl(${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} value) {
        this.value = value;
    }

    @Override
    final void onDependencyInvalidated() {
        this.provider = this::recomputeValue;

        //noinspection ConstantConditions
        if (this.state.get().isValid()) this.state.set(State.INVALID);
    }

    @Override
    final boolean onChangedInternal(${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} oldValue, ${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} newValue) {
        if (this.state.get() != State.UNINITIALIZED) {
            this.state.set(State.VALID);
            return true;
        } else {
            this.state.set(State.INITIALIZED);
            return false;
        }
    }

    /** A simple expression transforming a single value using the internal binding API. */
    private static final class Transform$typeParams extends Lazy${type.abbrevName}Expression$typeParams {

        private final transient ${type.abbrevName}Binding$typeParams binding;

        private Transform(Function<Lazy${type.abbrevName}Expression$typeParams, ${type.abbrevName}Binding$typeParams> factory) {
            this.binding = factory.apply(this);
        }

        @Override${if (type === Type.OBJECT) "\n        @Nullable" else ""}
        protected final ${type.raw} recomputeValue() {
            return this.binding.get();
        }

    }

}"""
    }
}