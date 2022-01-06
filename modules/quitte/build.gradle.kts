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
import com.osmerion.quitte.build.codegen.*
import com.osmerion.quitte.build.tasks.*
import java.io.*

plugins {
    `quitte-module`
}

java {
    val mainGen = mkdir(File(projectDir, "src/main-generated/java/"))
    val testGen = mkdir(File(projectDir, "src/test-generated/java/"))

    sourceSets["main"].java.srcDir(mainGen)
    sourceSets["test"].java.srcDir(testGen)
}

val generate = tasks.create("generate") {
    tasks.compileJava.get().dependsOn(this)
}

listOf("main", "test").forEach { templateCategory ->
    val templateDir = file("src/$templateCategory-templates")
    val templateDirPath = templateDir.toPath()

    fileTree(templateDir).forEach { templateSource ->
        val mangledName = templateDirPath.relativize(templateSource.toPath()).toString()
            .replace(File.separatorChar, '$')
            .removeSuffix(".build.gradle.kts")

        tasks.create("generate$${templateCategory.let { "${it.substring(0, 1).toUpperCase()}${it.substring(1)}" }}$$mangledName", Generate::class) {
            generate.dependsOn(this)

            templateCat = templateCategory

            project.extra["${templateCategory}Templates"] = mutableListOf<Template>()
            apply(from = templateSource)

            @Suppress("UNCHECKED_CAST")
            templates = project.extra["${templateCategory}Templates"] as List<Template>

            input = templateSource
            header = rootProject.file(".dev/resources/LICENSE_HEADER_GEN")
        }
    }
}

tasks {
    sourcesJar {
        dependsOn(generate)
    }
}

publishing {
    publications {
        getByName<MavenPublication>("mavenJava") {
            pom {
                description.set("Specialized, observable properties and observable collections for the JVM.")
            }
        }
    }
}

dependencies {
    compileOnlyApi(libs.jsr305)
}