// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    // Hilt için gereken plugin
    id("com.google.dagger.hilt.android") version "2.57" apply false

    // Kotlin Annotation Processing Tool (Kapt)
    // Kotlin versiyonunuza uygun en son versiyonu kullanın.
    id("org.jetbrains.kotlin.kapt") version "2.2.0" apply false
}