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
val packageName = "com.osmerion.quitte.expression"

Type.values().forEach {
    val type = it
    val typeParams = if (type === Type.OBJECT) "<T>" else ""

    template("${packageName.replace('.', '/')}/Simple${type.abbrevName}Expression") {
        """package $packageName;

import java.util.Objects;
import java.util.function.Function;
${if (type === Type.OBJECT) "\nimport javax.annotation.Nullable;\n" else ""}
import com.osmerion.quitte.*;
import com.osmerion.quitte.functional.*;
import com.osmerion.quitte.internal.binding.*;
import com.osmerion.quitte.value.*;
import com.osmerion.quitte.value.change.*;

/**
 * ${if (type === Type.OBJECT)
            "A basic implementation for a generic expression."
        else
            "A basic implementation for a specialized {@code ${type.raw}} expression."
        }
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public abstract class Simple${type.abbrevName}Expression$typeParams extends Abstract${type.abbrevName}Expression$typeParams {
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
     * Returns a new simple expression which applies a given transformation to a given observable.
     *${if (sourceType === Type.OBJECT) "\n     * @param <S>           the type of the source value" else ""}${if (type === Type.OBJECT) "\n     * @param <T>           the type of the target value" else ""}
     * @param observable    the observable
     * @param transform     the transformation to apply
     *
     * @return  a new simple expression which applies a given transformation to a given observable
     *
     * @since   0.1.0
     */
    public static $transformTypeParams${if (transformTypeParams.isNotEmpty()) " " else ""}Simple${type.abbrevName}Expression$typeParams of(Observable${sourceType.abbrevName}Value$sourceTypeParams observable, ${sourceType.abbrevName}To${type.abbrevName}Function$transformTypeParams transform) {
        return new Transform${if (type === Type.OBJECT) "<>" else ""}(ex -> new ${sourceType.abbrevName}To${type.abbrevName}Binding${if (sourceType === Type.OBJECT || type === Type.OBJECT) "<>" else ""}(ex::doInvalidate, observable, transform));
    }
"""}}
    /**
     * Returns a new simple expression which aliases a child property of an observable.
     *
     * <p>The parent observable must never evaluate to {@code null}.</p>
     *
     * @param observable    the parent observable
     * @param selector      the function that selects the child property
     * @param <S>           the parent type
     * ${if (type === Type.OBJECT) "@param <T>           the child type\n    *" else ""}
     * @return  a new simple expression which aliases a child property of an observable
     *
     * @since   0.1.0
     */
    public static <${if (type === Type.OBJECT) "S, T" else "S"}> Simple${type.abbrevName}Expression$typeParams ofNested(ObservableObjectValue<S> observable, Function<S, Observable${type.abbrevName}Value$typeParams> selector) {
        return new Simple${type.abbrevName}Expression${if (type === Type.OBJECT) "<>" else ""}() {

            final InvalidationListener nestedPropertyListener = ignored -> this.doInvalidate();

            {
                observable.addInvalidationListener(ignored -> this.doInvalidate());

                ObjectChangeListener<S> parentChangeListener = (ignored, oldValue, newValue) -> {
                    if (oldValue != null) {
                        var nestedProperty = selector.apply(oldValue);
                        nestedProperty.removeInvalidationListener(this.nestedPropertyListener);
                    }

                    var nestedProperty = selector.apply(Objects.requireNonNull(newValue));
                    nestedProperty.addInvalidationListener(this.nestedPropertyListener);
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
${if (type === Type.OBJECT) """
    /**
     * Returns a new simple expression which aliases a child property of an observable.
     *
     * <p>Returns {@code null} if the parent observable evaluates to {@code null}.</p>
     *
     * @param observable    the parent observable
     * @param selector      the function that selects the child property
     * @param <S>           the parent type
     * ${if (type === Type.OBJECT) "@param <T>           the child type\n    *" else ""}
     * @return  a new simple expression which aliases a child property of an observable
     *
     * @since   0.1.0
     */
    public static <${if (type === Type.OBJECT) "S, T" else "S"}> Simple${type.abbrevName}Expression$typeParams ofNestedOrNull(ObservableObjectValue<S> observable, Function<S, Observable${type.abbrevName}Value$typeParams> selector) {
        return new Simple${type.abbrevName}Expression${if (type === Type.OBJECT) "<>" else ""}() {

            final InvalidationListener nestedPropertyListener = ignored -> this.doInvalidate();

            {
                observable.addInvalidationListener(ignored -> this.doInvalidate());

                ObjectChangeListener<S> parentChangeListener = (ignored, oldValue, newValue) -> {
                    if (oldValue != null) {
                        var nestedProperty = selector.apply(oldValue);
                        nestedProperty.removeInvalidationListener(this.nestedPropertyListener);
                    }

                    if (newValue != null) {
                        var nestedProperty = selector.apply(newValue);
                        nestedProperty.addInvalidationListener(this.nestedPropertyListener);
                    }
                };
                observable.addListener(parentChangeListener);
                parentChangeListener.onChanged(observable, null, observable.get());
            }
${if (type === Type.OBJECT) "\n            @Nullable" else ""}
            @Override
            protected ${type.raw} recomputeValue() {
                var parent = observable.get();
                return (parent != null) ? selector.apply(parent).get() : null;
            }

        };
    }

    @Nullable""" else ""}
    protected ${type.raw} value;

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override${if (type === Type.OBJECT) "\n    @Nullable" else ""}
    protected final ${type.raw} getImpl() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    @Override
    protected final void setImpl(${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} value) {
        this.value = value;
    }

    /** A simple expression transforming a single value using the internal binding API. */
    private static final class Transform$typeParams extends Simple${type.abbrevName}Expression$typeParams {

        private final transient ${type.abbrevName}Binding$typeParams binding;

        private Transform(Function<Simple${type.abbrevName}Expression$typeParams, ${type.abbrevName}Binding$typeParams> factory) {
            this.binding = factory.apply(this);
            this.value = this.recomputeValue();
        }

        @Override${if (type === Type.OBJECT) "\n        @Nullable" else ""}
        protected final ${type.raw} recomputeValue() {
            return this.binding.get();
        }

    }

}"""
    }
}