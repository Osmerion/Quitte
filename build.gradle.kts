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
import java.io.*

plugins {
    java
    maven
    signing
}

val templates = mutableListOf<Template>()
project.extra["templates"] = templates

apply(from = "templates/AbstractProperty.build.gradle.kts")
apply(from = "templates/ChangeListener.build.gradle.kts")
apply(from = "templates/ChangeListenerWrapper.build.gradle.kts")
apply(from = "templates/ObservableValue.build.gradle.kts")
apply(from = "templates/ReadableProperty.build.gradle.kts")
apply(from = "templates/ReadOnlyWrapper.build.gradle.kts")
apply(from = "templates/WritableProperty.build.gradle.kts")
apply(from = "templates/WritableValue.build.gradle.kts")

group = "com.github.osmerion"
val artifactName = "quitte"
val nextVersion = "0.1.0"
version = when (deployment.type) {
    BuildType.SNAPSHOT -> "$nextVersion-SNAPSHOT"
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
    "generate" {
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

    val sourcesJar = "sourcesJar"(Jar::class) {
        baseName = artifactName
        classifier = "sources"
        from(java.sourceSets["main"].allSource)
    }

    val javadoc = "javadoc"(Javadoc::class)

    val javadocJar = "javadocJar"(Jar::class) {
        dependsOn(javadoc)

        baseName = artifactName
        classifier = "javadoc"
        from(javadoc.outputs)
    }

    val signArchives = "signArchives" {
        dependsOn(sourcesJar, javadocJar)
    }

    "uploadArchives"(Upload::class) {
        dependsOn(signArchives)

        repositories {
            withConvention(MavenRepositoryHandlerConvention::class) {
                mavenDeployer {
                    withGroovyBuilder {
                        "repository"("url" to deployment.repo) {
                            "authentication"(
                                "userName" to deployment.user,
                                "password" to deployment.password
                            )
                        }
                    }

                    if (deployment.type === BuildType.RELEASE) beforeDeployment { signing.signPom(this) }

                    pom.project {
                        withGroovyBuilder {
                            "artifactId"(artifactName)

                            "name"(project.name)
                            "description"("A minimal Java library which provides an efficient and modular EventBus solution for Java 9 and above.")
                            "packaging"("jar")
                            "url"("https://github.com/Osmerion/Quitte")

                            "licenses" {
                                "license" {
                                    "name"("BSD3")
                                    "url"("https://github.com/Osmerion/Quitte/blob/master/LICENSE")
                                    "distribution"("repo")
                                }
                            }

                            "developers" {
                                "developer" {
                                    "id"("TheMrMilchmann")
                                    "name"("Leon Linhart")
                                    "email"("themrmilchmann@gmail.com")
                                    "url"("https://github.com/TheMrMilchmann")
                                }
                            }

                            "scm" {
                                "connection"("scm:git:git://github.com/Osmerion/Quitte.git")
                                "developerConnection"("scm:git:git://github.com/Osmerion/Quitte.git")
                                "url"("https://github.com/Osmerion/Quitte.git")
                            }
                        }
                    }
                }
            }
        }
    }
}

val Project.deployment: Deployment
    get() = when {
        hasProperty("release") -> Deployment(
            BuildType.RELEASE,
            "https://oss.sonatype.org/service/local/staging/deploy/maven2/",
            getProperty("sonatypeUsername"),
            getProperty("sonatypePassword")
        )
        hasProperty("snapshot") -> Deployment(
            BuildType.SNAPSHOT,
            "https://oss.sonatype.org/content/repositories/snapshots/",
            getProperty("sonatypeUsername"),
            getProperty("sonatypePassword")
        )
        else -> Deployment(BuildType.LOCAL, repositories.mavenLocal().url.toString())
    }

fun Project.getProperty(k: String) =
    if (extra.has(k))
        extra[k] as String
    else
        System.getenv(k)

enum class BuildType {
    LOCAL,
    SNAPSHOT,
    RELEASE
}

data class Deployment(
    val type: BuildType,
    val repo: String,
    val user: String? = null,
    val password: String? = null
)

repositories {
	jcenter()
    mavenCentral()
}

dependencies {
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")

    testCompile("org.testng:testng:6.14.3")
}