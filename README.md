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
  - [Sending Custom Parameters](#sending-custom-parameters)
- [Customization Examples](#customization-examples)
  - [Header, Footer, and Bubble Styles](#header-footer-and-bubble-styles)
  - [Push Notifications](#push-notifications)
- [Public SDK Functions](#public-sdk-functions)
  - [Initialization](#initialization)
  - [Launch Chat](#launch-chat)
  - [Custom Parameters](#custom-parameters)
  - [Notifications](#notifications)
  - [UI Customization](#ui-customization)
  - [API Configuration](#api-configuration)
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
git clone https://github.com/<YourOrganization>/android-netomi-chat-sample.git
```

Or download the ZIP and extract it. Open the project in Android Studio.

### Configure the SDK

**Add Maven Central**

Ensure that `mavenCentral()` is included in your project’s top-level `build.gradle` or in the `settings.gradle` dependencyResolutionManagement block.

**Dependencies**

In the sample app’s `app/build.gradle`, ensure the following dependency is present:

```gradle
dependencies {
    implementation("com.netomi.chat:-android:1.1.11")
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

In the sample `MainActivity` (or wherever you want to initialize the chat), you will see code like:


## Public SDK Functions

### Initialization

Initialize the SDK with your bot credentials:

```kotlin
NCWChatSdk.initialize(
  context = context,
  botRefId = "YOUR_BOT_REF_ID",
  env = "us"  // or "qa", "sg", etc.
)
```

### Launch Chat

Launch the chat interface with optional JWT authentication and error handling:

```kotlin
// Basic chat launch
NCWChatSdk.launch(
  activityContext,
  jwt = null,  // Optional JWT token for authenticated users
  onError = { errorMsg ->
    // Handle any errors
    Toast.makeText(activityContext, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
  }
)

// Launch with pre-filled query
NCWChatSdk.launchWithQuery(
  activityContext,
  query = "Help with my order",
  jwt = null,
  onError = { errorMsg ->
    // Handle any errors
    Toast.makeText(activityContext, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
  }
)
```

### Custom Parameters

Send custom parameters to personalize the chat experience:

```kotlin
// Send single parameter
NCWChatSdk.sendCustomParameter(
  name = "userId",
  value = "12345"
)

// Send multiple parameters
val userParams = mutableMapOf(
  "userId" to "12345",
  "department" to "support",
  "locale" to "en_US"
)
NCWChatSdk.setCustomParameter(userParams)
```

### Notifications

Configure push notifications for your chat:

```kotlin
// Set FCM token for push notifications
val fcmToken = "your_fcm_token_here"
NCWChatSdk.setFCMToken(fcmToken)

// Example of getting FCM token in your app
FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
  NCWChatSdk.setFCMToken(token)
}
```

### UI Customization

Customize the chat interface with various configuration options:

```kotlin
// Header configuration
NCWChatSdk.updateHeaderConfiguration(
  NCWHeaderConfig(
    backgroundColor = "#FFFFFF",
    tintColor = "#000000",
    logoImage = "https://example.com/logo.png"
  )
)

// Footer configuration
NCWChatSdk.updateFooterConfiguration(
  NCWFooterConfig(
    backgroundColor = "#F8F8F8",
    inputBoxBackgroundColor = "#FFFFFF",
    isNetomiBrandingEnabled = true,
    netomiBrandingText = "Powered by Netomi",
    netomiBrandingTextColor = "#999999"
  )
)

// Bot configuration
NCWChatSdk.updateBotConfiguration(
  NCWBotConfig(
    bubbleBackgroundColor = "#008000",
    textBubbleTextColor = "#FFFFFF",
    quickReplyBackgroundColor = "#008000",
    quickReplyTextColor = "#FFFFFF"
  )
)

// User configuration
NCWChatSdk.updateUserConfiguration(
  NCWUserConfig(
    bubbleBackgroundColor = "#4CAF50",
    textBubbleTextColor = "#FFFFFF"
  )
)

// Bubble configuration
NCWChatSdk.updateBubbleConfiguration(
  NCWBubbleConfig(
    bubbleRadius = 12f,
    timestampTextColor = "#666666"
  )
)

// Chat window configuration
NCWChatSdk.updateChatWindowConfiguration(
  NCWChatWindowConfig(
    backgroundColor = "#FFFFFF"
  )
)

// Other configuration
NCWChatSdk.updateOtherConfiguration(
  NCWOtherConfig(
    downloadTranscriptButtonColor = "#008000",
    downloadTranscriptTextColor = "#FFFFFF"
  )
)
```

### API Configuration

Add custom headers to your API requests:

```kotlin
// Add custom headers
val customHeaders = mapOf(
  "X-Api-Key" to "your_api_key_here",
  "X-User-Id" to "12345",
  "X-Session-Id" to "session_123"
)
NCWChatSdk.updateApiHeaderConfiguration(customHeaders)
```

## License & Legal

© 2025 Netomi. All rights reserved. This sample application is for demonstration purposes. The Netomi Chat SDK itself may be covered by its own license terms. Please see the LICENSE file in this repository (if provided) and refer to Netomi’s official documentation for additional legal information.

## Support

For questions, feature requests, or bug reports related to this sample app or the Netomi Mobile Chat SDK, please contact:
- Netomi Support: [http://www.netomi.com](http://www.netomi.com)
- Or reach out via your Netomi representative.

Happy integrating!