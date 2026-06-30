# Installation

[← Back to documentation index](../README.md)

> **What this guide covers:** how to add the SDK to your project, the supported installation method, and the third-party dependencies managed by the SDK.
>
> **Read this when:** you are integrating the SDK for the first time, or upgrading to a new version.

---

## Prerequisites

- Android Studio 2022.2 (Flamingo) or newer
- `minSdkVersion` 26 or higher
- `compileSdk` / `targetSdk` 35 recommended
- Gradle 8.6 or higher, JDK 17
- Kotlin (the SDK is written in Kotlin; Java host apps are also supported)
- Your Bot Credentials from Netomi (`botRefId`, `environment`)

> **Important:** Do not add AWS IoT, Microsoft Speech, Datadog, Lottie, Mixpanel,
> or any Netomi artifact manually. The `chat-widget-android` dependency manages
> those for you and they resolve transitively.

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

2. Add the SDK to your **app module** `build.gradle(.kts)` (version `1.25.2`):

   ```kotlin
   dependencies {
       implementation("com.netomi.chat:chat-widget-android:1.25.2")
   }
   ```

3. Click **Sync Project** in Android Studio to download the SDK and its
   dependencies.

> ⚠️ **Deprecation Notice**
> The old package `com.netomi.chat:-android` has been **deprecated**.
> Use `com.netomi.chat:chat-widget-android` instead.

### Migration example

```kotlin
// ❌ Deprecated
implementation("com.netomi.chat:-android:1.1.x")

// ✅ Use this instead
implementation("com.netomi.chat:chat-widget-android:1.25.2")
```

---

## Managed Dependency Versions

`chat-widget-android` bundles and manages these third-party dependencies. They
resolve transitively — do **not** add separate versions unless Netomi support
asks you to.

| Dependency | Used for |
| --- | --- |
| AWS Android SDK (IoT + Core) | Real-time messaging (MQTT) |
| Retrofit / OkHttp | REST networking |
| Glide | Image loading / caching |
| Lottie | Animations |
| Microsoft Cognitive Services Speech | Voice input/output |
| Datadog (core + logs) | Observability (consent-gated) |
| Mixpanel | Analytics (enabled by bot configuration) |
| AndroidX DataStore / Security-Crypto | Local persistence |

> **16 KB page-size note:** the SDK ships native libraries that are 16 KB
> page-size aligned (required by Google Play for apps targeting Android 15+).
> No action is needed on your side.

---

### ➡️ Next step

Installed the SDK? Continue to **[Usage](usage.md)** to initialize and launch
the chat.

> Hitting a build or sync error? See **[Troubleshooting & FAQ](troubleshooting.md)**.
