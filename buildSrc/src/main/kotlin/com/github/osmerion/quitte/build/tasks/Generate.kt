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
package com.github.osmerion.quitte.build.tasks

import com.github.osmerion.quitte.build.codegen.*
import org.gradle.api.*
import org.gradle.api.tasks.*
import java.io.*

open class Generate : DefaultTask() {

    @InputFile
    lateinit var input: File

    @InputFile
    lateinit var header: File

    @OutputFiles
    lateinit var outputs: List<File>

    @Internal
    var templates: List<Template>? = null
        set(value) {
            outputs = value!!.map { File(project.rootDir, "src/main-generated/java/${it.path}.java") }
            field = value
        }

    @TaskAction
    fun generate() {
        fun String.format(indent: String = "") =
            if (lines().size == 1)
                "$indent/* $this */"
            else
                "$indent/*\n${StringBuilder().apply {
                    this@format.lines().forEach {
                        appendln("$indent *${if (it.isNotEmpty()) " $it" else ""}")
                    }
                }}$indent */"

        val licenseHeader = header.readText(Charsets.UTF_8).format()

        templates!!.forEach {
            File(project.rootDir, "src/main-generated/java/${it.path}.java").apply {
                parentFile.mkdirs()
                writeText("$licenseHeader\n${it.content}", Charsets.UTF_8)
            }
        }

    }

}