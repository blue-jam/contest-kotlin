plugins {
    // Apply the common convention plugin for shared build configuration between library and application projects.
    id("contest.kotlin.kotlin-common-conventions")

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

dependencies {
    testImplementation(project(":testlib"))
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
