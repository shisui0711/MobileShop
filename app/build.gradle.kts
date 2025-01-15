plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.fruitshop"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.fruitshop"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("androidx.paging:paging-runtime:3.3.5")
    implementation("androidx.room:room-paging:2.6.1")
    implementation(libs.navigation.fragment)
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}