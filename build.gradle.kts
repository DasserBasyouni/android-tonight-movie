// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    // Kotlin Kapt is only used for XML Data Binding.
    // Since Google is phasing out dataBinding in favor of compose and KSP support
    // for Data Binding is not planned. Then, we can replace it with Kotlin KPS,
    // once this app is migrated to Jetpack Compose.
    // [References #1](https://kotlinlang.org/docs/kapt.html)
    // [References #2](https://stackoverflow.com/a/77191405/5873832)
    alias(libs.plugins.kotlin.kapt)

    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.kotlin.parcelize) apply false
}

apply("${project.rootDir}/buildscripts/toml-updater-config.gradle")