buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
    }
}

plugins {
    id("com.android.application") version("7.2.2") apply(false)
    id("com.android.library") version("7.2.2") apply(false)
    id("org.jetbrains.kotlin.android") version("1.7.10") apply(false)
    id("org.jetbrains.kotlin.jvm") version("1.7.10") apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}