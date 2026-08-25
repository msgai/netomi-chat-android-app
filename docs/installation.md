# Installation

[← Back to documentation index](../README.md)

> **What this guide covers:** how to add the SDK to your project, the supported installation method, and the third-party dependencies managed by the SDK.
>
> **Read this when:** you are integrating the SDK for the first time, or upgrading to a new version.

---

## Prerequisites

- Android Studio 2022.2 (Flamingo) or newer
- `minSdkVersion` 26 or higher
- `compileSdk` / `targetSdk` 36 recommended
- Gradle 8.6 or higher, JDK 17
- Kotlin (the SDK is written in Kotlin; Java host apps are also supported)
- Your Bot Credentials from Netomi (`botRefId`, `environment`)

> **Important:** Do not add AWS IoT or Lottie manually.
> The `chat-widget-android` dependency manages those for you and they resolve
> transitively. **Mixpanel and voice input (Speech-to-Text) are the
> exceptions** — both are **optional, opt-in** artifacts. See
> [Optional: Mixpanel analytics](#optional-analytics) and
> [Optional: Voice input (Speech-to-Text)](#optional-voice-stt) below.

---

## Add the dependency (Maven Central)

1. Make sure `mavenCentral()` is in your repositories. With the modern Gradle
   setup this is in `settings.gradle(.kts)`:

   ```kotlin
   dependencyResolutionManagement {
       repositories {
           google()
           mavenCentral()
       }
   }
   ```

2. Add the SDK to your **app module** `build.gradle(.kts)` (version `1.29.0`):

   ```kotlin
   dependencies {
       implementation("com.netomi.chat:chat-widget-android:1.29.0")
   }
   ```

3. **Enable Core Library Desugaring** — the SDK requires this for Java 8+ API
   support. Add to your app module `build.gradle(.kts)`:

   ```kotlin
   android {
       compileOptions {
           sourceCompatibility = JavaVersion.VERSION_1_8
           targetCompatibility = JavaVersion.VERSION_1_8
           isCoreLibraryDesugaringEnabled = true
       }
   }

   dependencies {
       implementation("com.netomi.chat:chat-widget-android:1.29.0")
       coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
   }
   ```

   > **What is desugaring?**
   > The SDK uses modern Java 8+ APIs (like `java.time`, `Optional`, streams, etc.)
   > that aren't natively available on older Android versions. Desugaring is a
   > build-time process that converts these modern APIs into equivalent code that
   > works on Android 8.0+. The `desugar_jdk_libs` library provides these backported
   > APIs at runtime. Without this, the app would crash with `NoClassDefFoundError`
   > or `MethodNotFoundException` on older devices.

4. Click **Sync Project** in Android Studio to download the SDK and its
   dependencies.

> ⚠️ **Deprecation Notice**
> The old package `com.netomi.chat:-android` has been **deprecated**.
> Use `com.netomi.chat:chat-widget-android` instead.

### Migration example

```kotlin
// ❌ Deprecated
implementation("com.netomi.chat:-android:1.1.x")

// ✅ Use this instead
implementation("com.netomi.chat:chat-widget-android:1.29.0")
```

---

## Managed Dependency Versions

`chat-widget-android` bundles and manages these third-party dependencies. They
resolve transitively — do **not** add separate versions unless Netomi support
asks you to.

| Dependency | Used for |
| --- | --- |
| AWS IoT Device SDK for Android (v2, AWS CRT) | Real-time messaging (MQTT) |
| Retrofit / OkHttp | REST networking |
| Glide | Image loading / caching |
| Lottie | Animations |
| AndroidX DataStore / Security-Crypto | Local persistence |

> Mixpanel and the Microsoft Cognitive Services Speech SDK are **not** in
> this list — both are optional add-ons, not bundled by default. See
> [Optional: Mixpanel analytics](#optional-analytics) and
> [Optional: Voice input (Speech-to-Text)](#optional-voice-stt) below.

> **16 KB page-size note:** the SDK ships native libraries that are 16 KB
> page-size aligned (required by Google Play for apps targeting Android 15+).
> No action is needed on your side.

---

## Optional: Analytics

By default, `chat-widget-android` does **not** include Mixpanel or any other
analytics provider. Analytics events (e.g. `CHAT_SDK_INITIALIZED`, launch
errors, terms accepted/declined) are routed through a pluggable
`NCWAnalyticsTracker` interface, which is a no-op unless you register a
tracker.

If you want the SDK's analytics events sent to Mixpanel (as configured by
your bot), add the optional `chat-widget-android-analytics` artifact:

```kotlin
dependencies {
    implementation("com.netomi.chat:chat-widget-android:1.29.0")
    implementation("com.netomi.chat:chat-widget-android-analytics:1.29.0")
}
```

That's it — no application code required. If you don't add this module, no
Mixpanel classes are pulled into your APK/AAR.

---

## Optional: Voice input (Speech-to-Text) {#optional-voice-stt}

By default, `chat-widget-android` does **not** include the Microsoft
Cognitive Services Speech SDK or any voice-input (Speech-to-Text) capability.
The chat UI's mic button stays hidden and voice input is unreachable unless
you add the optional artifact below. (Voice **output** — the agent's spoken
replies — has no Microsoft Speech dependency and works regardless.)

If you want users to be able to speak their messages, add the optional
`chat-widget-android-voicestt` artifact:

```kotlin
dependencies {
    implementation("com.netomi.chat:chat-widget-android:1.29.0")
    implementation("com.netomi.chat:chat-widget-android-voicestt:1.29.0")
}
```

That's it — no application code required. Adding this module also declares
the `RECORD_AUDIO` permission, requested at runtime the first time the user
taps the mic button. If you don't add this module, no Microsoft Speech SDK
classes or the `RECORD_AUDIO` permission are pulled into your APK/AAR, and
the mic button never appears.

---

### ➡️ Next step

Installed the SDK? Continue to **[Usage](usage.md)** to initialize and launch
the chat.

> Hitting a build or sync error? See **[Troubleshooting & FAQ](troubleshooting.md)**.
