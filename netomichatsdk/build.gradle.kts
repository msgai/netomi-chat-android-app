plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-android")
    id("jacoco")
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

    testOptions {
        unitTests.all {
            // Allow mocking final classes
            it.useJUnitPlatform()
            it.enabled = true
        }
    }

    buildFeatures {
        buildConfig = true
    }


    buildTypes {


        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
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
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.browser)
    //implementation(libs.core.ktx)

    // Unit Test cases
    //  JUnit 5 API (for writing tests)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")

    //  JUnit 5 Engine (to execute tests)
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    // JUnit Platform Launcher (fixes "Cannot create Launcher" issue)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.9.2")

    // Ensure JUnit 4 is compatible with JUnit 5
    testImplementation("org.junit.vintage:junit-vintage-engine:5.9.2") {
        exclude(group = "junit", module = "junit") // Prevents JUnit 4 conflicts
    }

    androidTestImplementation("org.mockito:mockito-android:2.24.5")
    testImplementation("junit:junit:4.13.2") // ⚠️ Needed if some tests still use JUnit 4
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    testImplementation("org.mockito:mockito-core:3.4.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("org.robolectric:robolectric:4.10.1")


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


    implementation ("com.mixpanel.android:mixpanel-android:7.+")


}
// JaCoCo Configuration
jacoco {
    toolVersion = "0.8.7"
}

// Exclusion List (Outside android { } block)
val exclusions = listOf(
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*",
    "android/**/*.*"
)

// Configure JaCoCo for Unit Tests & Android UI Tests
tasks.withType<Test>().configureEach {
    extensions.configure(JacocoTaskExtension::class) {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

// Generate JaCoCo report for both `test` & `androidTest`
tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest", "connectedDebugAndroidTest") // Run both tests before report

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val coverageSourceDirs = listOf("src/main/java", "src/main/kotlin")

    sourceDirectories.setFrom(files(coverageSourceDirs))

    classDirectories.setFrom(files(
        fileTree("${layout.buildDirectory}/intermediates/javac/debug") {
            exclude(exclusions)
        },
        fileTree("${layout.buildDirectory}/tmp/kotlin-classes/debug") {
            exclude(exclusions)
        }
    ))

    executionData.setFrom(
        fileTree(layout.buildDirectory) {
            include("**/*.exec", "**/*.ec")
        }
    )
}

