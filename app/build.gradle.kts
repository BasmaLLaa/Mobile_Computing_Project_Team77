plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // If you ever use KSP again, uncomment this:
    // alias(libs.plugins.google.ksp)
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.salon"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.salon"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        // Needed for Espresso / instrumented tests
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // --- App / Compose ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")

    // --- Room (Persistence) ---
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // --- Unit tests (JUnit + Mockito + Coroutines) ---
    testImplementation(libs.junit)
    testImplementation("org.mockito:mockito-core:5.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")

    // --- Instrumented UI tests (Espresso / Compose UI Test) ---
    androidTestImplementation("junit:junit:4.13.2")            // ***ADD THIS LINE***
    androidTestImplementation(libs.androidx.junit)           // androidx.test.ext:junit
    androidTestImplementation(libs.androidx.espresso.core)   // Espresso core
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Debug tools
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
