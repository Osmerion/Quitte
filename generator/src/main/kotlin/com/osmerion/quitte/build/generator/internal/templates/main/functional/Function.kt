/*
 * Copyright (c) 2022 Leon Linhart,
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

object Function : TemplateProvider {

    override fun provideTemplates(): List<Template> = Type.values().flatMap { sourceType ->
        Type.values().map { targetType ->
            val className = "${sourceType.abbrevName}To${targetType.abbrevName}Function"
            val sourceTypeName = if (sourceType === Type.OBJECT) "T" else sourceType.raw
            val targetTypeName = if (targetType === Type.OBJECT) "R" else targetType.raw
            val typeParams = when {
                sourceType === Type.OBJECT && targetType === Type.OBJECT -> "<T, R>"
                sourceType === Type.OBJECT -> "<T>"
                targetType === Type.OBJECT -> "<R>"
                else -> ""
            }

            Template(PACKAGE_NAME, className) {
                """
package $PACKAGE_NAME;
${if (sourceType === Type.OBJECT || targetType === Type.OBJECT) "\nimport javax.annotation.Nullable;\n" else ""}
/**
 * Represents a function that accepts one argument and produces a result.
 *${when {
                    sourceType === Type.OBJECT && targetType === Type.OBJECT -> """
 * @param <T>   the type of the input to the function
 * @param <R>   the type of the result of the function
 *
 *"""
                    sourceType === Type.OBJECT -> """
 * @param <T>   the type of the input to the function
 *"""
                    targetType === Type.OBJECT -> """
 * @param <R>   the type of the result of the function
 *"""
                    else -> ""
                }}
 * @see java.util.function.Function
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
@FunctionalInterface
public interface $className$typeParams {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     *
     * @return  the function result
     *
     * @since   0.1.0
     */${if (targetType === Type.OBJECT) "\n    @Nullable" else ""}
    $targetTypeName apply(${if (sourceType === Type.OBJECT) "@Nullable " else ""}$sourceTypeName t);

}
                """
            }
        }
    }

}