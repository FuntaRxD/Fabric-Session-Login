pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    // Multi-version project maintenance: https://stonecutter.kikugie.dev
    id("dev.kikugie.stonecutter") version "0.9.8"

    // Applies the correct Loom variant for each Minecraft era (obfuscated <=1.21.11 vs unobfuscated >=26.1)
    // and provides `loomx.applyMojangMappings()`: https://codeberg.org/KikuGie/loom-back-compat
    id("dev.kikugie.loom-back-compat") version "0.4.2"

    // Java toolchain provisioning
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

stonecutter {
    create(rootProject) {
        // Checkpoints where the client GUI/login-relevant API actually changes.
        // Each node ships a jar whose `mod.mc_compat` claims the widest range that still works.
        versions("1.21.1", "1.21.4", "1.21.8", "1.21.11", "26.1", "26.2", "26.3")
        vcsVersion = "1.21.1"
    }
}

rootProject.name = "SessionIDLogin"
