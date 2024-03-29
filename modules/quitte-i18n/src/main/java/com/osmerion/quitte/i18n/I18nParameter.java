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
package com.osmerion.quitte.i18n;

import java.util.function.Function;
import java.util.function.Supplier;
import com.osmerion.quitte.Observable;
import com.osmerion.quitte.value.ObservableValue;

/**
 * A parameter with special treatment in {@link I18n#format(I18nContext, String, Object...)} and related methods.
 *
 * @see I18n#format(I18nContext, String, Object...)
 * @see I18n#formatLazily(I18nContext, String, Object...)
 *
 * @since   0.1.0
 *
 * @apiNote Only the existence of this type and the type itself is public API. Any behavior or property of the type is
 *          implementation detail and subject to change.
 *
 * @author  Leon Linhart
 */
public abstract sealed class I18nParameter {

    /**
     * Wraps a given {@link ObservableValue} in an instance of {@link I18nParameter} for special treatment in formatting
     * methods.
     *
     * @param observable    the observable to wrap
     *
     * @return  a {@code I18nParameter} wrapping the given observable value
     *
     * @see I18n#format(I18nContext, String, Object...)
     * @see I18n#formatLazily(I18nContext, String, Object...)
     *
     * @since   0.1.0
     */
    public static I18nParameter i18n(ObservableValue<?> observable) {
        /*
         * Calling the boxing getter of ObservableValue is fine here, since the returned objects are eventually passed
         * to MessageFormat#format as Object[] anyway. Thus, there is no need to provide specialized overloads here.
         */
        return i18n(observable, ObservableValue::getValue);
    }

    /**
     * Wraps a given {@link Observable} in an instance of {@link I18nParameter} for special treatment in formatting
     * methods.
     *
     * @param <O>           the type of the observable
     * @param observable    the observable to wrap
     * @param mapper        the function used to map the observable to a value
     *
     * @return  a {@code I18nParameter} wrapping the given observable
     *
     * @see I18n#format(I18nContext, String, Object...)
     * @see I18n#formatLazily(I18nContext, String, Object...)
     *
     * @since   0.1.0
     */
    public static <O extends Observable> I18nParameter i18n(O observable, Function<O, ?> mapper) {
        return new Variable(observable, () -> mapper.apply(observable));
    }

    /*
     * The JavaDoc of I18nParameter is not technically correct since instances of I18nParameter.Constant do not receive
     * any special treatment. However, there is no way to obtain those from the (public) API, and they are entirely
     * transparent with no special behavior. Hence, we can get away with technically incorrect JavaDoc here.
     */

    private I18nParameter() {}

    abstract Object get();

    static final class Variable extends I18nParameter {

        final Observable observable;
        final Supplier<?> mapper;

        Variable(Observable observable, Supplier<?> mapper) {
            this.observable = observable;
            this.mapper = mapper;
        }

        @Override
        Object get() {
            return this.mapper.get();
        }

    }

    static final class Constant extends I18nParameter {

        private final Object value;

        Constant(Object value) {
            this.value = value;
        }

        @Override
        Object get() {
            return this.value;
        }

    }

}