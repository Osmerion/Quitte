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
package com.github.osmerion.quitte.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.function.Function;
import com.github.osmerion.quitte.expression.SimpleObjectExpression;
import com.github.osmerion.quitte.property.SimpleDoubleProperty;
import com.github.osmerion.quitte.property.SimpleObjectProperty;
import org.junit.jupiter.api.Test;

import static com.github.osmerion.quitte.i18n.I18nParameter.*;
import static org.junit.jupiter.api.Assertions.*;

public class I18nTest {

    @Test
    public void testI18n() {
        Locale locale$de_DE = new Locale("de", "DE");
        Locale locale$en_US = new Locale("en", "US");
        Function<Locale, MessageFormat> message$DE = locale -> new MessageFormat("Hallo, {0}! Für den Döner bekomm ich {1}€.", locale);
        Function<Locale, MessageFormat> message$EN = locale -> new MessageFormat("Hello, {0}! the hot dog costs {1}$.", locale);

        SimpleObjectProperty<MessageFormat> messageFormat = new SimpleObjectProperty<>(message$DE.apply(locale$de_DE));

        I18nContext ctx = new I18nContext() {

            final I18nContext i18nCtx = this;
            final SimpleObjectProperty<MessageFormat> format = new SimpleObjectProperty<>(null) {
                @Override protected void onInvalidated() { i18nCtx.notifyListeners(); }
            };

            {
                this.format.bindTo(messageFormat);
            }

            @Override
            protected MessageFormat getMessageFormat(String key) {
                //noinspection ConstantConditions
                return this.format.get();
            }

        };


        SimpleDoubleProperty value = new SimpleDoubleProperty(10.5D);
        SimpleObjectExpression<String> message = I18n.format(ctx, "", "Willi", i18n(value));
        //noinspection ConstantConditions
        assertEquals(messageFormat.get().format(new Object[]{ "Willi", value.get() }, new StringBuffer(), null).toString(), message.get());

        value.set(21.9);
        assertEquals(messageFormat.get().format(new Object[]{ "Willi", value.get() }, new StringBuffer(), null).toString(), message.get());

        messageFormat.set(message$EN.apply(locale$en_US));
        assertEquals(messageFormat.get().format(new Object[]{ "Willi", value.get() }, new StringBuffer(), null).toString(), message.get());

        messageFormat.set(message$EN.apply(locale$de_DE));
        assertEquals(messageFormat.get().format(new Object[]{ "Willi", value.get() }, new StringBuffer(), null).toString(), message.get());

        value.set(10.5);
        assertEquals(messageFormat.get().format(new Object[]{ "Willi", value.get() }, new StringBuffer(), null).toString(), message.get());
    }

}