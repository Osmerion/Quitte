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
package com.osmerion.quitte.build.generator.internal.templates.main.functional

import com.osmerion.quitte.build.generator.internal.Template
import com.osmerion.quitte.build.generator.internal.TemplateProvider
import com.osmerion.quitte.build.generator.internal.Type

object Consumer : TemplateProvider {

    override fun provideTemplates(): List<Template> = Type.values().map { sourceType ->
        val className = "${sourceType.abbrevName}Consumer"
        val sourceTypeName = if (sourceType === Type.OBJECT) "T" else sourceType.raw
        val typeParams = if (sourceType === Type.OBJECT) "<T extends @Nullable Object>" else ""

        Template(PACKAGE_NAME, "${sourceType.abbrevName}Consumer") {
            """
package $PACKAGE_NAME;
${if (sourceType === Type.OBJECT) "\nimport org.jspecify.annotations.*;;\n" else ""}
/**
 * Represents an operation that consumes a single argument and returns no result.
 *
 * <p>A {@code Consumer} is expected to operate via side-effects.</p>
 *${when {
                sourceType === Type.OBJECT -> """
 * @param <T>   the type of the input to the operation
 *"""
                else -> ""
            }}
 * @see java.util.function.Consumer
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
@FunctionalInterface
public interface $className$typeParams {

    /**
     * Performs the operation on the given argument.
     *
     * @param t the input argument
     *
     * @since   0.1.0
     */
    void accept(${if (sourceType === Type.OBJECT) "@Nullable " else ""}$sourceTypeName t);

}
            """
        }
    }

}