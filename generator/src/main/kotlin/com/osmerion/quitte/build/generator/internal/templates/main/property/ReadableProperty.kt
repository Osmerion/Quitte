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
package com.osmerion.quitte.build.generator.internal.templates.main.property

import com.osmerion.quitte.build.generator.internal.Template
import com.osmerion.quitte.build.generator.internal.TemplateProvider
import com.osmerion.quitte.build.generator.internal.Type

object ReadableProperty : TemplateProvider {

    override fun provideTemplates(): List<Template> = Type.values().map { type ->
        val typeParams = if (type === Type.OBJECT) "<T>" else ""

        Template(PACKAGE_NAME, "Readable${type.abbrevName}Property") {
            """
package $PACKAGE_NAME;

import com.osmerion.quitte.internal.wrappers.*;
import com.osmerion.quitte.value.*;

/**
 * ${if (type === Type.OBJECT)
                "A generic readable property."
            else
                "A specialized readable {@code ${type.raw}} property."
            }
 *
 * @see Observable${type.abbrevName}Value
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public interface Readable${type.abbrevName}Property$typeParams extends ReadableValueProperty<${type.box}>, Observable${type.abbrevName}Value$typeParams {

    /**
     * {@inheritDoc}
     *
     * @since   0.1.0
     */
    default Readable${type.abbrevName}Property$typeParams asReadOnlyProperty() {
        return (!(this instanceof ReadOnly${type.abbrevName}Property) ? new ReadOnly${type.abbrevName}Property${if (type === Type.OBJECT) "<>" else ""}(this) : this);
    }

}
            """
        }
    }

}