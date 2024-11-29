plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}


android {
    namespace = "com.netomi.chat"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        externalNativeBuild {
            cmake {
                cppFlags("")
            }
        }
    }

    buildFeatures {
        buildConfig = true
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
    externalNativeBuild {
        cmake {
            path("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }


}

dependencies {
    implementation ("androidx.core:core-ktx:1.13.1")
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("com.google.android.material:material:1.12.0")
    // Unit Test cases
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1")
    testImplementation ("org.mockito:mockito-core:2.28.2")
    androidTestImplementation ("org.mockito:mockito-android:2.24.5")

    // ViewModel and LiveData
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.activity:activity-ktx:1.7.2")

    // Retrofit of Network communication
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // Core Glide library for image loading and caching
    implementation("com.github.bumptech.glide:glide:4.15.1")
    // Annotation processor for Glide
    kapt("com.github.bumptech.glide:compiler:4.15.1")

    //aws IOT
    implementation ("com.amazonaws:aws-android-sdk-iot:2.77.0")
    implementation ("com.amazonaws:aws-android-sdk-core:2.77.0")
    //implementation ("com.amazonaws:aws-android-sdk-auth-core:2.66.0")

    //Data Store
    implementation ("androidx.datastore:datastore-preferences:1.1.1")
    implementation ("androidx.security:security-crypto:1.1.0-alpha06")

    implementation ("com.airbnb.android:lottie:6.6.0")




}
