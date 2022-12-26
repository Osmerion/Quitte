/*
 * Copyright (c) 2018-2022 Leon Linhart,
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
package com.osmerion.quitte.build.generator.internal.tasks

import com.osmerion.quitte.build.generator.internal.Template
import com.osmerion.quitte.build.generator.internal.TemplateProvider
import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.*
import java.io.File

@CacheableTask
internal open class Generate : DefaultTask() {

    @InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    lateinit var input: File

    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    lateinit var header: File

    @get:Input
    lateinit var outputBasePath: String

    @get:Input
    lateinit var templateCat: String

    @get:Internal
    var templates: List<Template> = listOf()
        set(value) {
            outputs = value.map { File(project.projectDir, "src/$templateCat-generated/java/${it.path}") }
            field = value
        }

    @get:OutputFiles
    lateinit var outputs: List<File>

    @TaskAction
    fun generate() {
        fun String.format(indent: String = "") =
            if (lines().size == 1)
                "$indent/* $this */"
            else
                "$indent/*\n${StringBuilder().apply {
                    this@format.lines().forEach {
                        appendLine("$indent *${if (it.isNotEmpty()) " $it" else ""}")
                    }
                }}$indent */"

        val licenseHeader = header.readText(Charsets.UTF_8).format()

        templates.forEach { template ->
            File(outputBasePath, "src/$templateCat-generated/java/${template.path}").apply {
                parentFile.mkdirs()
                writeText("$licenseHeader\n${template.content}", Charsets.UTF_8)
            }
        }
    }

}