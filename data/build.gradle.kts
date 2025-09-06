import java.util.Properties
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.example.githubinterview"
    compileSdk = 36

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }



    buildFeatures {
        // Enables the generation of the BuildConfig class
        buildConfig = true
    }

    defaultConfig {
        minSdk = 24

        // Explicitly load properties from local.properties
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localPropertiesFile.inputStream().use {
                localProperties.load(it)
            }
        }

        // Securely reads the GitHub token from local.properties
        val githubToken = localProperties.getProperty("github.token","")
        buildConfigField("String", "github_token", "\"$githubToken\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }



}
dependencies {
    api(project(":domain"))

    // Networking with Retrofit and Moshi
    implementation(libs.retrofit)
    implementation(libs.moshi.kotlin)
    implementation(libs.retrofit.moshi.converter)
    implementation(libs.okhttp.logging.interceptor)

    // Room for local persistence
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Hilt for dependency injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Chucker Network Inspector
    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)
}
