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
package com.osmerion.quitte.build.generator.internal.templates.main.internal.binding

import com.osmerion.quitte.build.generator.internal.Template
import com.osmerion.quitte.build.generator.internal.TemplateProvider
import com.osmerion.quitte.build.generator.internal.Type

object Binding : TemplateProvider {

    override fun provideTemplates(): List<Template> = Type.values().map { type ->
        val typeParams = if (type === Type.OBJECT) "<T>" else ""

        Template(PACKAGE_NAME, "${type.abbrevName}Binding") {
            """
package $PACKAGE_NAME;
${if (type === Type.OBJECT) "\nimport javax.annotation.Nullable;\n" else ""}
import com.osmerion.quitte.*;
import com.osmerion.quitte.functional.*;
import com.osmerion.quitte.value.*;

/**
 * ${if (type === Type.OBJECT)
                "A generic binding."
            else
                "A specialized {@code ${type.raw}} binding."
            }
 *
 * @author  Leon Linhart
 */
public interface ${type.abbrevName}Binding$typeParams extends Binding {
${if (type === Type.OBJECT) "\n    @Nullable" else ""}
    ${type.raw} get();

    final class Generic${if (type === Type.OBJECT) "<T, R>" else "<T>" } implements ${type.abbrevName}Binding${if (type === Type.OBJECT) "<R>" else "" } {

        private final ObservableValue<T> source;
        private final InvalidationListener listener;
        private final ObjectTo${type.abbrevName}Function${if (type === Type.OBJECT) "<T, R>" else "<T>" } transform;

        public Generic(Runnable invalidator, ObservableValue<T> source, ObjectTo${type.abbrevName}Function${if (type === Type.OBJECT) "<T, R>" else "<T>" } transform) {
            this.source = source;
            this.transform = transform;

            this.source.addInvalidationListener(new WeakInvalidationListener(this.listener = (observable) -> invalidator.run()));
        }

        @Override${if (type === Type.OBJECT) "\n        @Nullable" else ""}
        public ${if (type === Type.OBJECT) "R" else type.raw } get() {
            return this.transform.apply(this.source.getValue());
        }

        @Override
        public void release() {
            this.source.removeInvalidationListener(this.listener);
        }

    }

}
            """
        }
    }

}