buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
        val nav_version = "2.7.6"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.android.library") version "8.1.2" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
    kotlin("kapt") version "1.9.20"
    id("com.google.gms.google-services") version "4.4.0" apply false



}