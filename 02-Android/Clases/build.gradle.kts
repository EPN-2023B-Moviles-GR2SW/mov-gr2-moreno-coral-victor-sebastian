buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.0")
        classpath("com.google.gms:google-services:4.4.0")
        classpath("com.google.firebase:firebase-appdistribution-gradle:4.0.1")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}