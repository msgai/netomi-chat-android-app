# Netomi Mobile Chat SDK (Android)

## Overview

The **Netomi Android Chat SDK** adds a fully managed AI chat experience to your
app. It supports:

- 💬 Rich media responses, file attachments, and forms
- 🧑‍💼 Live agent handoff
- 🔔 Push notifications (Firebase Cloud Messaging)
- 🎨 Flexible UI styling (via the Netomi Dashboard or in code)
- 🔐 Optional JWT-authenticated sessions
- 🎙️ Voice output (TTS), with optional voice input (Speech-to-Text add-on module)

---

## 📚 Documentation

### 📦 [Installation](docs/installation.md)

Prerequisites, Gradle / Maven Central setup, and the dependencies managed by the SDK.
**Read this when** you are adding the SDK to a project for the first time.

### 🚀 [Usage](docs/usage.md)

Initialize, launch, hide, resume, and reset the chat.
**Read this when** you need the core chat lifecycle.

### 🎨 [UI Theming](docs/ui-theming.md)

Customize colors, headers, footers, message bubbles, and other visual styling in code.
**Read this when** the Netomi Dashboard styling is not enough.

### 🔔 [Push Notifications](docs/push-notifications.md)

Register a device push token, handle token refresh, and migrate from the deprecated FCM API.
**Read this when** chat replies should arrive as push notifications.

### 🔐 [Events & Authentication](docs/events-and-auth.md)

Use JWT auth, receive SDK events, send events back, and handle reauthorization.
**Read this when** your bot uses authenticated sessions or you need event callbacks.

### ⚙️ [Advanced](docs/advanced.md)

Initial menus, custom parameters, custom API headers, and logging.
**Read this when** you need finer control beyond the basic flow.

### 🔐 [Security & Privacy](docs/security-and-privacy.md)

Play Store Data safety and guidance for keeping secrets and PII out of the SDK.
**Read this when** you are preparing a Play Store submission or a privacy review.

### 🛠️ [Troubleshooting & FAQ](docs/troubleshooting.md)

Common issues, debugging guidance, and frequently asked questions.
**Read this when** something isn't working or you want a quick answer.

---

## ⚡ Quick Start

The three steps below get a basic chat running. See [Installation](docs/installation.md) and [Usage](docs/usage.md) for full details and options.

### 1. Install

Ensure `mavenCentral()` is in your Gradle repositories, then add the dependency
(version `1.29.0`) in your app module's `build.gradle(.kts)`:

```kotlin
dependencies {
    implementation("com.netomi.chat:chat-widget-android:1.29.0")
}
```

> ⚠️ The old coordinate `com.netomi.chat:-android` is **deprecated**. Use
> `com.netomi.chat:chat-widget-android` instead. See [Installation](docs/installation.md).

### 2. Initialize (once, at app launch)

```kotlin
NCWChatSdk.initialize(
    context = this,
    newBotRefId = "YOUR_BOT_REF_ID",
    environment = NCWEnvironment.us
)
```

### 3. Launch the chat

```kotlin
NCWChatSdk.launch(context = this)
```

For prefilled queries, custom animations, error handling, hiding/resuming, and more, continue to the [Usage](docs/usage.md) guide.

---

## ✅ Prerequisites (at a glance)

- Android Studio 2022.2 or newer
- `minSdkVersion` 26 or higher
- Gradle 8.6 or higher
- Kotlin (the SDK is written in Kotlin)
- Your Netomi bot credentials (`botRefId`, `environment`)

Full details, including managed dependency versions, are in [Installation](docs/installation.md).

---

## 🧪 Example App

This repository is a working example app that demonstrates SDK integration.

1. Open the project in Android Studio.
2. Replace `YOUR_BOT_REF_ID` and `environment` with your own values.
3. Build & run on an emulator or device (API 26+).

---

## 🛠 Support

For SDK issues or integration help:

- 📘 [Netomi Website](https://www.netomi.com)
- 📩 [support@netomi.com](mailto:support@netomi.com)

---

## 📄 License

```text
© 2026 Netomi. All rights reserved.
The Netomi Mobile Chat SDK may include its own license terms.
Refer to Netomi's official documentation for legal details.
```
