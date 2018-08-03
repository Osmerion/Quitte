/*
 * Copyright (c) 2018 Leon Linhart,
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
import osmerion.build.*
import java.io.*

plugins {
    java
    signing
    `maven-publish`
    id("com.zyxist.chainsaw")
}

val templates = mutableListOf<Template>()
project.extra["templates"] = templates

apply(from = "templates/AbstractProperty.build.gradle.kts")
apply(from = "templates/ChangeListener.build.gradle.kts")
apply(from = "templates/ChangeListenerWrapper.build.gradle.kts")
apply(from = "templates/ObservableValue.build.gradle.kts")
apply(from = "templates/ReadableProperty.build.gradle.kts")
apply(from = "templates/ReadOnlyWrapper.build.gradle.kts")
apply(from = "templates/SimpleProperty.build.gradle.kts")
apply(from = "templates/WritableProperty.build.gradle.kts")
apply(from = "templates/WritableValue.build.gradle.kts")

group = "com.github.osmerion"
val artifactName = "quitte"
val nextVersion = "0.1.0"
version = when (deployment.type) {
    osmerion.build.BuildType.SNAPSHOT -> "$nextVersion-SNAPSHOT"
    else -> nextVersion
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_9
    targetCompatibility = JavaVersion.VERSION_1_9

    val mainGen = mkdir(File(projectDir, "src/main-generated/java/"))
    val testGen = mkdir(File(projectDir, "src/test-generated/java/"))

    sourceSets["main"].java.srcDir(mainGen)
    sourceSets["test"].java.srcDir(testGen)
}

tasks {
    create("generate") {
        doLast {
            fun String.toComment(indent: String = "") =
                if (lines().size == 1)
                    "$indent/* $this */"
                else
                    "$indent/*\n${StringBuilder().apply {
                        this@toComment.lines().forEach { appendln("$indent * $it") }
                    }}$indent */"

            val licenseHeader = File(projectDir, ".dev/resources/LICENSE_HEADER_GEN").readText(Charsets.UTF_8).toComment()

            templates.forEach {
                File(projectDir, "src/main-generated/java/${it.path}.java").apply {
                    parentFile.mkdirs()
                    writeText("$licenseHeader\n${it.content}", Charsets.UTF_8)
                }
            }
        }
    }

    "test"(Test::class) {
        useTestNG()
    }

    "jar"(Jar::class) {
        baseName = artifactName

        manifest {
            attributes(mapOf(
                "Name" to project.name,
                "Specification-Version" to project.version,
                "Specification-Vendor" to "Leon Linhart <themrmilchmann@gmail.com>",
                "Implementation-Version" to project.version,
                "Implementation-Vendor" to "Leon Linhart <themrmilchmann@gmail.com>"
            ))
        }
    }

    create<Jar>("sourcesJar") {
        baseName = artifactName
        classifier = "sources"
        from(java.sourceSets["main"].allSource)
    }

    val javadoc = "javadoc"(Javadoc::class)

    create<Jar>("javadocJar") {
        dependsOn(javadoc)

        baseName = artifactName
        classifier = "javadoc"
        from(javadoc.get().outputs)
    }
}

publishing {
    repositories {
        maven {
            url = uri(deployment.repo)

            credentials {
                username = deployment.user
                password = deployment.password
            }
        }
    }
    (publications) {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            artifactId = artifactName

            pom {
                name.set(project.name)
                description.set("")
                packaging = "jar"
                url.set("https://github.com/Osmerion/Quitte")

                licenses {
                    license {
                        name.set("BSD3")
                        url.set("https://github.com/Osmerion/Quitte/blob/master/LICENSE")
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        id.set("TheMrMilchmann")
                        name.set("Leon Linhart")
                        email.set("themrmilchmann@gmail.com")
                        url.set("https://github.com/TheMrMilchmann")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/Osmerion/Quitte.git")
                    developerConnection.set("scm:git:git://github.com/Osmerion/Quitte.git")
                    url.set("https://github.com/Osmerion/Quitte.git")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications)
}

repositories {
	jcenter()
    mavenCentral()
}

dependencies {
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")

    testCompile("org.testng:testng:6.14.3")
    testCompile("com.google.code.findbugs:jsr305:3.0.2")
}