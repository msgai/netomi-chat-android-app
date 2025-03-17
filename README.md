# Netomi Mobile Chat SDK Sample App (Android)

This repository contains a sample Android application that demonstrates how to integrate the Netomi Mobile Chat SDK for a conversational AI experience. The sample shows how to initialize the SDK, launch the chat interface, and apply various customizations like theming and user attributes.

## Table of Contents
- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Setup & Installation](#setup--installation)
    - [Clone or Download the Sample](#clone-or-download-the-sample)
    - [Configure the SDK](#configure-the-sdk)
    - [Build & Run](#build--run)
- [Sample Code Walkthrough](#sample-code-walkthrough)
    - [Initialization](#initialization)
    - [Launching the Chat](#launching-the-chat)
    - [Sending Custom Parameters](#sending-custom-parameters)D
- [Customization Examples](#customization-examples)
    - [Header, Footer, and Bubble Styles](#header-footer-and-bubble-styles)
    - [Pass FCM Token](#pass-fcm-token)
- [License & Legal](#license--legal)
- [Support](#support)

## Overview

The Netomi Mobile Chat SDK lets you embed a fully interactive AI chatbot into your Android application. Users can engage in automated conversations, share files, fill out forms, and even switch to a live agent if needed. This sample app illustrates a basic setup with optional customization hooks.

## Prerequisites

- Android Studio (2022.2 or newer)
- Android `minSdkVersion`: 26 or higher
- Gradle Version: 8.6 or higher
- Kotlin: The SDK is written in Kotlin; the sample app also uses Kotlin.
- Bot Credentials: Your `botRefId` and environment (`env`) (e.g., `us`, `sg`, `eu`, etc.) provided by Netomi.

## Setup & Installation

### Clone or Download the Sample

```bash
git clone https://github.com/msgai/netomi-chat-android-app.git
```
Or download the ZIP and extract it.

- Open the project in Android Studio.

### Configure the SDK

**Add Maven Central**

Ensure that `mavenCentral()` is included in your project’s top-level `build.gradle` or in the `settings.gradle` dependencyResolutionManagement block.

**Dependencies**

In the sample app’s `app/build.gradle`, ensure the following dependency is present:

```gradle
dependencies {
    implementation("com.netomi.chat:-android:1.0.2")
    // ...other dependencies
}
```

**Sync Gradle**

Click "Sync Project" in Android Studio to install the Netomi SDK.

**BotRefId & Environment**

Store your `botRefId` and `env` in the sample app (e.g., in `local.properties`, Gradle properties, or directly in code). Use valid credentials from your Netomi account.

### Build & Run

1. Select a device or emulator running API Level 26 or higher.
2. Press "Run" (▶) in Android Studio.
3. The sample app’s home screen will display a button to "Launch Chat".

## Sample Code Walkthrough

### Initialization

In the sample `MainActivity` (or wherever you want to initialize the chat), you will see code like:

```kotlin
NCWChatSdk.initialize(
    context = this,
    botRefId = "YOUR_BOT_REF_ID",  // Usually retrieved from a secure store
    env = "us"                     // Example environment
)
```

### Launching the Chat

When the user taps the "Launch Chat" button:

```kotlin
launchChatButton.setOnClickListener {
    NCWChatSdk.launch(
        jwt = null, // or supply a valid JWT if you have authenticated users
        onError = { errorMsg ->
            // Handle error, e.g., log or show a toast
        }
    )
}
```

- `jwt` (optional): Include a valid JSON Web Token for authenticated user sessions.
- `onError` (optional): Handle any issues that might occur.

### Sending Custom Parameters

If you want to pass extra user context (like department or user IDs):

```kotlin
NCWChatSdk.sendCustomParameter(name = "department", value = "marketing")
```

## Customization Examples

### Header, Footer, and Bubble Styles

Configure chat UI elements as shown below:

```kotlin
NCWChatSdk.updateHeaderConfiguration(
    NCWHeaderConfig(
        backgroundColor = "#FFFFFF",
        tintColor = "#000000",
        logoImage = "https://example.com/logo.png"
    )
)

NCWChatSdk.updateFooterConfiguration(
    NCWFooterConfig(
        backgroundColor = "#F8F8F8",
        inputBoxBackgroundColor = "#FFFFFF",
        isNetomiBrandingEnabled = true,
        netomiBrandingText = "Powered by Netomi",
        netomiBrandingTextColor = "#999999"
    )
)
```

### Pass FCM Token

If your app demonstrates push notifications:

```kotlin
NCWChatSdk.setFCMToken(fcmToken)
```

Ensure Firebase Cloud Messaging is properly configured in your app.

## License & Legal

© 2025 Netomi. All rights reserved. This sample application is for demonstration purposes. The Netomi Chat SDK itself may be covered by its own license terms. Please see the LICENSE file in this repository (if provided) and refer to Netomi’s official documentation for additional legal information.

## Support

For questions, feature requests, or bug reports related to this sample app or the Netomi Mobile Chat SDK, please contact:
- Netomi Support: [http://www.netomi.com](http://www.netomi.com)
- Or reach out via your Netomi representative.

Happy integrating!
