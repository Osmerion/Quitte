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
package com.osmerion.quitte.build.generator.plugins

import com.osmerion.quitte.build.generator.internal.TemplateProvider
import com.osmerion.quitte.build.generator.internal.tasks.Generate
import com.osmerion.quitte.build.generator.internal.utils.applyTo
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.kotlin.dsl.*

open class QuitteGeneratorPlugin : Plugin<Project> {

    private val templates: List<TemplateProvider> = listOf(
        /* com.osmerion.quitte.expression */
        com.osmerion.quitte.build.generator.internal.templates.main.expression.AbstractExpression,
        com.osmerion.quitte.build.generator.internal.templates.main.expression.LazyExpression,
        com.osmerion.quitte.build.generator.internal.templates.main.expression.SimpleExpression,
        /* com.osmerion.quitte.functional */
        com.osmerion.quitte.build.generator.internal.templates.main.functional.Consumer,
        com.osmerion.quitte.build.generator.internal.templates.main.functional.Function,
        com.osmerion.quitte.build.generator.internal.templates.main.functional.Supplier,
        /* com.osmerion.quitte.internal.binding */
        com.osmerion.quitte.build.generator.internal.templates.main.internal.binding.Binding,
        com.osmerion.quitte.build.generator.internal.templates.main.internal.binding.TypeToTypeBinding,
        /* com.osmerion.quitte.internal.wrappers */
        com.osmerion.quitte.build.generator.internal.templates.main.internal.wrappers.ReadOnlyProperty,
        com.osmerion.quitte.build.generator.internal.templates.main.internal.wrappers.ReadOnlyWrapper,
        com.osmerion.quitte.build.generator.internal.templates.main.internal.wrappers.WrappingChangeListener,
        /* com.osmerion.quitte.property */
        com.osmerion.quitte.build.generator.internal.templates.main.property.AbstractProperty,
        com.osmerion.quitte.build.generator.internal.templates.main.property.LazyProperty,
        com.osmerion.quitte.build.generator.internal.templates.main.property.ReadableProperty,
        com.osmerion.quitte.build.generator.internal.templates.main.property.SimpleProperty,
        com.osmerion.quitte.build.generator.internal.templates.main.property.WritableProperty,
        /* com.osmerion.quitte.value */
        com.osmerion.quitte.build.generator.internal.templates.main.value.ObservableValue,
        com.osmerion.quitte.build.generator.internal.templates.main.value.WritableValue,
        /* com.osmerion.quitte.value.change */
        com.osmerion.quitte.build.generator.internal.templates.main.value.change.ChangeListener,
        com.osmerion.quitte.build.generator.internal.templates.main.value.change.WeakChangeListener
    )

    private val testTemplates: List<TemplateProvider> = listOf(
        /* com.osmerion.quitte.expression */
        com.osmerion.quitte.build.generator.internal.templates.test.expression.LazyExpression,
        com.osmerion.quitte.build.generator.internal.templates.test.expression.SimpleExpression,
        /* com.osmerion.quitte.property */
        com.osmerion.quitte.build.generator.internal.templates.test.property.LazyProperty,
        com.osmerion.quitte.build.generator.internal.templates.test.property.SimpleProperty
    )

    override fun apply(target: Project) = applyTo(target) {
        pluginManager.apply(JavaBasePlugin::class)

        val generateTask = tasks.create("generate")
        templates.forEach { templates -> createGenerateTemplateTask(templates, generateTask) }

        tasks.named("compileJava").configure {
            dependsOn(generateTask)
        }

        val generateTestTask = tasks.create("generateTest")
        testTemplates.forEach { templates -> createGenerateTestTemplateTask(templates, generateTestTask) }

        tasks.named("compileTestJava").configure {
            dependsOn(generateTestTask)
        }
    }

    private fun Project.createGenerateTemplateTask(templateProvider: TemplateProvider, aggregateTask: Task) {
        val generateTemplateTask = tasks.create<Generate>("generate${templateProvider::class.simpleName}") {
            header = rootProject.file(".dev/resources/LICENSE_HEADER_GEN")
            input = rootProject.file("generator/src/main/kotlin/${templateProvider::class.qualifiedName!!.replace('.', '/')}.kt")

            outputBasePath = projectDir.absolutePath

            templateCat = "main"
            templates = templateProvider.provideTemplates()
        }

        aggregateTask.dependsOn(generateTemplateTask)
    }

    private fun Project.createGenerateTestTemplateTask(templateProvider: TemplateProvider, aggregateTask: Task) {
        val generateTemplateTask = tasks.create<Generate>("generate${templateProvider::class.simpleName}Test") {
            header = rootProject.file(".dev/resources/LICENSE_HEADER_GEN")
            input = rootProject.file("generator/src/main/kotlin/${templateProvider::class.qualifiedName!!.replace('.', '/')}.kt")

            outputBasePath = projectDir.absolutePath

            templateCat = "test"
            templates = templateProvider.provideTemplates()
        }

        aggregateTask.dependsOn(generateTemplateTask)
    }

}