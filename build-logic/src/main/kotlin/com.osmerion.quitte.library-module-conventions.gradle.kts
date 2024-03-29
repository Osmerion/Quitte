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
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("com.osmerion.quitte.java-library-conventions")
    id("com.osmerion.quitte.maven-publish-conventions")
    id("org.gradlex.extra-java-module-info")
}


val artifactName = project.name

// https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
val libs = the<LibrariesForLibs>()

tasks {
    compileJava {
        options.compilerArgs.add("--module-version")
        options.compilerArgs.add("$version")
    }

    jar {
        archiveBaseName.set(artifactName)

        manifest {
            attributes(mapOf(
                "Name" to artifactName,
                "Specification-Version" to project.version,
                "Specification-Vendor" to "Leon Linhart <themrmilchmann@gmail.com>",
                "Implementation-Version" to project.version,
                "Implementation-Vendor" to "Leon Linhart <themrmilchmann@gmail.com>"
            ))
        }
    }

    "javadoc"(Javadoc::class) {
        with (options as StandardJavadocDocletOptions) {
            tags = listOf(
                "apiNote:a:API Note:",
                "implSpec:a:Implementation Requirements:",
                "implNote:a:Implementation Note:"
            )

            addStringOption("-release", "17")
        }
    }

    test {
        useJUnitPlatform()

        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])

            artifactId = artifactName
        }
    }
}

extraJavaModuleInfo {
    automaticModule("com.google.code.findbugs:jsr305", "jsr305")
}

dependencies {
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
}