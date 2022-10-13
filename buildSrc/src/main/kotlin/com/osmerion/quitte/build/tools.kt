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
package com.osmerion.quitte.build

import org.gradle.api.*
import org.gradle.api.publish.maven.*
import org.gradle.kotlin.dsl.*

private const val DEPLOYMENT_KEY = "com.osmerion.quitte.build.Deployment"

val Project.deployment: Deployment
    get() =
        if (extra.has(DEPLOYMENT_KEY)) {
            extra[DEPLOYMENT_KEY] as Deployment
        } else
            (when {
                hasProperty("release") -> Deployment(
                    BuildType.RELEASE,
                    "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/",
                    getProperty("osmerionSonatypeUsername"),
                    getProperty("osmerionSonatypePassword")
                )
                hasProperty("snapshot") -> Deployment(
                    BuildType.SNAPSHOT,
                    "https://s01.oss.sonatype.org/content/repositories/snapshots/",
                    getProperty("osmerionSonatypeUsername"),
                    getProperty("osmerionSonatypePassword")
                )
                else -> Deployment(BuildType.LOCAL, repositories.mavenLocal().url.toString())
            }).also { extra[DEPLOYMENT_KEY] = it }

fun Project.getProperty(k: String): String =
    if (extra.has(k))
        extra[k] as String
    else
        System.getenv(k) ?: ""

fun MavenPublication.decorateMavenPom(projectName: String, packaging: String = "jar") {
    pom {
        name.set(projectName)
        this@pom.packaging = packaging
        url.set("https://github.com/Osmerion/Quitte")

        licenses {
            license {
                name.set("BSD-3-Clause")
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