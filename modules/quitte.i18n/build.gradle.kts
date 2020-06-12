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
import com.github.osmerion.quitte.build.*
import com.github.osmerion.quitte.build.codegen.*
import com.github.osmerion.quitte.build.tasks.*
import java.io.*

plugins {
    `java-library`
    signing
    `maven-publish`
    id("de.jjohannes.extra-java-module-info") version "0.1"
}

val artifactName = "quitte-i18n"

java {
    modularity.inferModulePath.set(true)

    sourceCompatibility = JavaVersion.VERSION_14
    targetCompatibility = JavaVersion.VERSION_14

    val mainGen = mkdir(File(projectDir, "src/main-generated/java/"))
    val testGen = mkdir(File(projectDir, "src/test-generated/java/"))

    sourceSets["main"].java.srcDir(mainGen)
    sourceSets["test"].java.srcDir(testGen)
}

tasks {
    jar {
        archiveBaseName.set(artifactName)

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
        archiveBaseName.set(artifactName)
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    val javadoc = "javadoc"(Javadoc::class)

    create<Jar>("javadocJar") {
        dependsOn(javadoc)

        archiveBaseName.set(artifactName)
        archiveClassifier.set("javadoc")
        from(javadoc.get().outputs)
    }

    test {
        useJUnitPlatform()

        testLogging {
            events("passed", "skipped", "failed")
        }
    }
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
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            artifactId = artifactName

            pom {
                name.set(project.name)
                description.set("Internationalization capabilities for seamless interoperability with Quitte observables.")
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
    isRequired = (deployment.type === com.github.osmerion.quitte.build.BuildType.RELEASE)
    sign(publishing.publications)
}

repositories {
    mavenCentral()
}

extraJavaModuleInfo {
    automaticModule("jsr305-3.0.2.jar", "jsr305")
}

dependencies {
    api(project(":quitte"))

    compileOnly(group = "com.google.code.findbugs", name = "jsr305", version = "3.0.2")
    testCompileOnly(group = "com.google.code.findbugs", name = "jsr305", version = "3.0.2")

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter", version = "5.6.0")
}