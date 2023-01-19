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
package com.osmerion.quitte.build.generator.internal.templates.main.value

import com.osmerion.quitte.build.generator.internal.Template
import com.osmerion.quitte.build.generator.internal.TemplateProvider
import com.osmerion.quitte.build.generator.internal.Type

object WritableValue : TemplateProvider {

    override fun provideTemplates(): List<Template> = Type.values().map { type ->
        val typeParams = if (type === Type.OBJECT) "<T>" else ""

        Template(PACKAGE_NAME, "Writable${type.abbrevName}Value") {
            """
package $PACKAGE_NAME;

import javax.annotation.Nullable;

/**
 * ${if (type === Type.OBJECT)
                "A generic writable value."
            else
                "A specialized writable {@code ${type.raw}} value."
            }
 *
 * @see WritableValue
 * @see Observable${type.abbrevName}Value
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface Writable${type.abbrevName}Value$typeParams extends WritableValue<${type.box}>, Observable${type.abbrevName}Value$typeParams {

    /**
     * Updates the value represented by this object.
     *
     * @param value the new value
     *
     * @since   0.1.0
     */
    void set(${if (type === Type.OBJECT) "@Nullable " else ""}${type.raw} value);

    /**
     * {@inheritDoc}
     * ${if (type === Type.OBJECT) "" else "\n     * <p>Defaults to {@code ${type.default}} if the given value is {@code null}.</p>\n     *"}
     * @since   0.1.0
     */
    @Override
    default void setValue(@Nullable ${type.box} value) {
        this.set(${if (type === Type.OBJECT) "value" else "value != null ? value : ${type.default}"});
    }

}
            """
        }
    }

}