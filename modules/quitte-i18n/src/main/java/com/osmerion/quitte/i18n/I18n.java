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
package com.osmerion.quitte.i18n;

import java.text.MessageFormat;
import java.util.Arrays;
import com.osmerion.quitte.expression.LazyObjectExpression;
import com.osmerion.quitte.expression.SimpleObjectExpression;
import com.osmerion.quitte.value.ObservableValue;

/**
 * This class provides basic internationalization capabilities for seamless interoperability with Quitte observables.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public final class I18n {

    /**
     * Returns a {@link SimpleObjectExpression} that yields a formatted and localized message using
     * {@link MessageFormat#format(Object)}.
     *
     *  <p>The given {@code arguments} are passed to the formatting method as is with the exception of arguments of type
     * {@link I18nParameter} obtained via {@link I18nParameter#i18n(ObservableValue)} (or any other overload). These
     * arguments are treated as variable <em>parameters</em>.</p>
     *
     * <p>The {@link MessageFormat} is identified by the given localization key and provided by the {@link I18nContext}.
     * The returned expression is invalidated when the context or any of its parameters is invalidated.</p>
     *
     * @param context   the localization context from which to retrieve the format for the message
     * @param key       the localization key that will be used to retrieve the appropriate {@link MessageFormat} from
     *                  the given {@code context}
     * @param arguments the arguments to be passed to {@link MessageFormat#format(Object)}
     *
     * @return  a {@code SimpleObjectExpression} that yields a formatted and localized message
     *
     * @since   0.1.0
     */
    public static SimpleObjectExpression<String> format(I18nContext context, String key, Object... arguments) {
        //noinspection NotNullFieldNotInitialized
        return new SimpleObjectExpression<>() {

            private final I18nParameter[] i18nParameters;
            private MessageFormat format;

            {
                Runnable contextUpdateFun = () -> this.format = context.getMessageFormat(key);
                this.addDependency(context, contextUpdateFun);
                contextUpdateFun.run();

                this.i18nParameters = Arrays.stream(arguments).map(it -> {
                    if (it instanceof I18nParameter.Dynamic) {
                        I18nParameter.Dynamic dynamic = (I18nParameter.Dynamic) it;
                        this.addDependency(dynamic.observable);

                        return dynamic;
                    } else {
                        return new I18nParameter.Static(it);
                    }
                }).toArray(I18nParameter[]::new);

                this.invalidate();
            }

            @Override
            protected String recomputeValue() {
                Object arguments = Arrays.stream(this.i18nParameters)
                    .map(I18nParameter::get)
                    .toArray();

                return this.format.format(arguments, new StringBuffer(), null).toString();
            }

        };
    }

    /**
     * Returns a {@link LazyObjectExpression} that yields a formatted and localized message using
     * {@link MessageFormat#format(Object)}.
     *
     *  <p>The given {@code arguments} are passed to the formatting method as is with the exception of arguments of type
     * {@link I18nParameter} obtained via {@link I18nParameter#i18n(ObservableValue)} (or any other overload). These
     * arguments are treated as variable <em>parameters</em>.</p>
     *
     * <p>The {@link MessageFormat} is identified by the given localization key and provided by the {@link I18nContext}.
     * The returned expression is invalidated when the context or any of its parameters is invalidated.</p>
     *
     * @param context   the localization context from which to retrieve the format for the message
     * @param key       the localization key that will be used to retrieve the appropriate {@link MessageFormat} from
     *                  the given {@code context}
     * @param arguments the arguments to be passed to {@link MessageFormat#format(Object)}
     *
     * @return  a {@code LazyObjectExpression} that yields a formatted and localized message
     *
     * @since   0.1.0
     */
    public LazyObjectExpression<String> formatLazily(I18nContext context, String key, Object... arguments) {
        //noinspection NotNullFieldNotInitialized
        return new LazyObjectExpression<>() {

            private final I18nParameter[] i18nParameters;
            private MessageFormat format;

            {
                Runnable contextUpdateFun = () -> this.format = context.getMessageFormat(key);
                this.addDependency(context, contextUpdateFun);
                contextUpdateFun.run();

                this.i18nParameters = Arrays.stream(arguments).map(it -> {
                    if (it instanceof I18nParameter.Dynamic) {
                        I18nParameter.Dynamic dynamic = (I18nParameter.Dynamic) it;
                        this.addDependency(dynamic.observable);

                        return dynamic;
                    } else {
                        return new I18nParameter.Static(it);
                    }
                }).toArray(I18nParameter[]::new);

                this.invalidate();
            }

            @Override
            protected String recomputeValue() {
                Object arguments = Arrays.stream(this.i18nParameters)
                    .map(I18nParameter::get)
                    .toArray();

                return this.format.format(arguments, new StringBuffer(), null).toString();
            }

        };
    }

    // Private constructor for utility class
    @Deprecated
    private I18n() {}

}