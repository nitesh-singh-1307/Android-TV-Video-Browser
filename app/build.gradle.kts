plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.androidtvvideobrowser"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.androidtvvideobrowser"
        minSdk = 21
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled =  true

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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.tv.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)


    // ExoPlayer for video playback
    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")

    // Coil for image loading
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.compose.material3:material3:1.4.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.21")
    implementation("androidx.startup:startup-runtime:1.1.1")
    implementation("androidx.multidex:multidex:2.0.1")
   // implementation("androidx.tv:tv-foundation:1.0.0-alpha10")
  //  implementation("androidx.compose.foundation:foundation:1.6.8")
    implementation(platform("androidx.compose:compose-bom:2025.08.00")) // Use the latest BOM date!
    implementation(files("libs/tv-foundation-1.0.0-alpha10.aar"))

    // 2. Remove the version numbers from the individual dependencies!
//    implementation 'androidx.compose.ui:ui'
//    implementation 'androidx.compose.material3:material3'

// Or your current Compose BOM version
// Check for the latest version!
// Use the latest stable version
// Or the latest version
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}