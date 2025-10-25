import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
}
hilt {
    enableAggregatingTask = false
}

// Read the local.properties file
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

android {
    namespace = "com.jtautomation02.foodcravies"
    compileSdk {
        version = release(36)
    }
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        applicationId = "com.jtautomation02.foodcravies"
        minSdk = 25
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        // 3. Expose the key to your app's code via BuildConfig
        // It reads the value from local.properties
        val googleClientId = localProperties.getProperty("GOOGLE_CLIENT_ID") ?: ""

        // This creates a variable: BuildConfig.GOOGLE_CLIENT_ID
            buildConfigField("String", "GOOGLE_CLIENT_ID", "\"$googleClientId\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }
}
kapt {
    correctErrorTypes = true
}
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.tv.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences.core)
    implementation(libs.androidx.compose.foundation.layout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.androidx.core.splashscreen)
    // Jetpack Compose integration
    implementation(libs.androidx.navigation.compose)
    // JSON serialization library, works with the Kotlin serialization plugin
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    implementation(libs.androidxHiltNavigationCompose)
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.coroutines)
    implementation(libs.coil)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    // Core Moshi library
    implementation(libs.moshi)
    implementation(libs.okhttp)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)

    implementation("androidx.credentials:credentials:1.2.2")
// Or the latest version
    implementation("androidx.credentials:credentials-play-services-auth:1.2.2")
// Or the latest version
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")

    implementation("androidx.datastore:datastore-preferences:1.1.1")
}