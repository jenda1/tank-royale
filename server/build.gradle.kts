import proguard.gradle.ProGuardTask

val title = "Robocode Tank Royale Server"
group = "dev.robocode.tankroyale"
version = libs.versions.tankroyale.get()
description = "Server for running Robocode Tank Royale"

val jarManifestMainClass = "dev.robocode.tankroyale.server.ServerKt"

val artifactBaseName = "robocode-tankroyale-server"
val artifactBaseFilename = "${buildDir}/libs/${artifactBaseName}-${project.version}"

buildscript {
    dependencies {
        classpath(libs.proguard.gradle)
    }
}

@Suppress("DSL_SCOPE_VIOLATION") // remove later when IntelliJ supports the `libs.` DSL
plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    alias(libs.plugins.shadow.jar)
    `maven-publish`
    signing
}

dependencies {
    implementation(libs.tankroyale.schema)
    implementation(libs.java.websocket)
    implementation(libs.slf4j.simple)
    implementation(libs.picocli)
    implementation(libs.jansi)

    testImplementation(libs.kotest.junit5)
    testImplementation(libs.mockk)
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = jarManifestMainClass
            attributes["Implementation-Title"] = title
            attributes["Implementation-Version"] = archiveVersion
            attributes["Implementation-Vendor"] = "robocode.dev"
            attributes["Package"] = project.group
        }
    }

    shadowJar.configure {
        dependsOn(jar)
        archiveBaseName.set(artifactBaseName)
        archiveClassifier.set(null as String?) // get rid of "-all" classifier
    }

    val proguard by registering(ProGuardTask::class) { // used for compacting and code-shaking
        dependsOn(shadowJar)
        injars("${artifactBaseFilename}.jar")
        outjars("${artifactBaseFilename}-proguard.jar")
        configuration("proguard-rules.pro")
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                artifact(proguard.get().outJarFiles[0]) {
                    builtBy(proguard)
                }
                groupId = group as String?
                artifactId = artifactBaseName
                version
                pom {
                    name.set(title)
                    description
                    url.set("https://github.com/robocode-dev/tank-royale")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("fnl")
                            name.set("Flemming Nørnberg Larsen")
                            organization.set("flemming-n-larsen")
                            organizationUrl.set("https://github.com/flemming-n-larsen")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/robocode-dev/tank-royale.git")
                        developerConnection.set("scm:git:ssh://github.com:robocode-dev/tank-royale.git")
                        url.set("https://github.com/robocode-dev/tank-royale/tree/master")
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}