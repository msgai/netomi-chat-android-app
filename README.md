# 📱 Netomi Mobile Chat SDK Sample App (Android)

This repository contains a working example of how to integrate and customize the [Netomi Chat SDK](https://www.netomi.com) in an Android app using Kotlin.

It showcases:

* ✅ SDK initialization with `botRefId`
* ✅ JWT-based chat launching
* ✅ Runtime UI customization (header, footer, bubbles, etc.)
* ✅ FCM token registration

---

## 📋 Table of Contents

* [Overview](#-overview)
* [Prerequisites](#prerequisites)
  * [Initialize SDK](#-initialize-sdk)
  * [Launch Chat](#-launch-chat)
  * [Pass JWT Token](#-pass-jwt-token-optional)
  * [Send Custom Parameters](#-send-custom-parameters)
  * [Pass Custom API Headers](#-pass-custom-api-headers-optional)
  * [Apply UI Customization](#-apply-ui-customization-optional)
  * [Set Push Token](#-set-push-token)
* [Support](#-support)
* [License](#-license)

---

## 📖 Overview

The **Netomi Android Chat SDK** enables you to embed conversational AI into your mobile app. It supports:

* Rich media responses
* File uploads and forms
* Live agent handoff
* Firebase push notifications
* Flexible UI styling (via code or Netomi dashboard)

This sample app demonstrates how to integrate, configure, and customize the SDK within an Android project.

---

## Prerequisites

* Android Studio (2022.2 or newer)
* Android `minSdkVersion`: 26 or higher
* Gradle Version: 8.6 or higher
* Kotlin (the SDK is written in Kotlin)
* `botRefId` and environment (`us`, `sg`, `eu`, etc.) from Netomi

---

### Step 1: Clone or Download

```bash
git clone https://github.com/msgai/netomi-chat-android-app.git
```

Then open the project in Android Studio.

---

### Step 2: Add Dependency

Ensure `mavenCentral()` is included in your Gradle build files, then add:

```gradle
dependencies {
    implementation("com.netomi.chat:chat-widget-android:1.9.0")
}
```

> ⚠️ **Deprecation Notice**  
> The old package `com.netomi.chat:-android` has been **deprecated**.  
> Please use the new package `com.netomi.chat:chat-widget-android` instead.

### Migration Example

```gradle
// ❌ Deprecated
implementation("com.netomi.chat:-android:1.1.x")

// ✅ Use this instead
implementation("com.netomi.chat:chat-widget-android:1.9.0")
```

---

### Step 3: Sync Gradle

Click **Sync Project** in Android Studio to download the SDK and required dependencies.

---

## Quick Start

### ✅ Initialize SDK

```kotlin
NCWChatSdk.initialize(
    context,
    newBotRefId: "YOUR_BOT_REF_ID",
    environment: NCWEnvironment.us
)
```

> Replace `YOUR_BOT_REF_ID` and choose the environment: `us`, `sg`, `eu`, `qa`, `qaint`, `dev`
>
> 🔹 Most visual styling can be configured via the Netomi Dashboard.  
>
> 🔹 If you'd like to customize it locally in code, see [Apply UI Customization](#-apply-ui-customization-optional)

---

### 🚀 Launch Chat

```kotlin
NCWChatSdk.launch(
    context,
    jwt = null,
    onError = { error ->
        Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
    }
)
```

With a search query:

```kotlin
NCWChatSdk.launchWithQuery(
    context,
    query = "Track my order",
    jwt = null,
    onError = { error ->
        Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
    }
)
```

---

## 🔐 Pass JWT Token (Optional)

```kotlin
val jwt = "your-jwt-token"
NCWChatSdk.launch(context, jwt = jwt)
```

---

### 🧩 Clear Current Chat Session Manually

```kotlin
NCWChatSdk.clearChatSession(context)
```

---

### 🧩 Send Custom Parameters

You can pass custom parameters to personalize the chat experience or pass session-specific metadata to the AI backend.

#### 🔹 To send a single key-value parameter

```kotlin
// Example: Indicate that the current user is a premium member
NCWChatSdk.sendCustomParameter("user_role", "premium_user") // User role info (e.g., premium, guest)
```

#### 🔹 To set multiple custom parameters at once

```kotlin
// Example: Pass user profile info during initialization
val userParams = mapOf(
    "user_id" to "12345",                  // Unique user identifier
    "user_name" to "John Doe",             // Full name of the user
    "membership_level" to "gold",          // Membership tier (e.g., gold, silver)
    "app_version" to "7.2.0"               // App version for targeting or debugging
)

NCWChatSdk.setCustomParameter(userParams)
```

---

### 🧾 Pass Custom API Headers (Optional)

> Send custom HTTP headers with each SDK API request — useful for:
>
> - Authentication tokens
> - Versioning
> - Experiment targeting
> - Localization context

```kotlin
val customHeaders = mapOf(
    "X-App-Version" to "7.2.0",                  // Current app version
    "X-Device-ID" to "device-98765",             // Unique device identifier
    "X-Platform" to "android",                   // OS platform
    "X-User-Type" to "beta_tester",              // User group/segment
    "X-Experiment-Variant" to "A",               // A/B test group
    "X-Locale" to Locale.getDefault().toString() // User locale (e.g., en_US)
)

NCWChatSdk.updateApiHeaderConfiguration(customHeaders)
```

> ⚠️ These headers are automatically sent with each SDK API call.
Avoid including any sensitive data like passwords or secrets.

---

### 🎨 Apply UI Customization (Optional)

Customize the appearance of various chat components:

```kotlin
// 🧩 Header Configuration
val headerConfig = NCWHeaderConfig(
    backgroundColor = "#2196F3",                        // Header background color
    tintColor = "#FFFFFF",                              // Icon and text tint
    logoImage = "https://example.com/logo.png"          // Optional logo image
)
NCWChatSdk.updateHeaderConfiguration(headerConfig)

// 🧩 Footer Configuration
val footerConfig = NCWFooterConfig(
    backgroundColor = "#FFFFFF",                        // Footer background
    inputBoxBackgroundColor = "#F0F0F0",                // Input box background
    isNetomiBrandingEnabled = true,                     // Toggle Netomi branding
    netomiBrandingText = "Powered by Netomi",           // Branding text
    netomiBrandingTextColor = "#999999"                 // Branding text color
)
NCWChatSdk.updateFooterConfiguration(footerConfig)

// 🧩 Bot Configuration
val botConfig = NCWBotConfig(
    bubbleBackgroundColor = "#E0E0E0",                  // Bot message background
    textBubbleTextColor = "#000000",                    // Bot text color
    quickReplyBackgroundColor = "#D0D0D0",              // Quick reply background
    quickReplyTextColor = "#000000"                     // Quick reply text color
)
NCWChatSdk.updateBotConfiguration(botConfig)

// 🧩 User Configuration
val userConfig = NCWUserConfig(
    bubbleBackgroundColor = "#4CAF50",                  // User message background
    textBubbleTextColor = "#FFFFFF"                     // User text color
)
NCWChatSdk.updateUserConfiguration(userConfig)

// 🧩 Bubble Styling
val bubbleConfig = NCWBubbleConfig(
    bubbleRadius = 12f,                                 // Corner radius
    timestampTextColor = "#666666"                      // Timestamp text color
)
NCWChatSdk.updateBubbleConfiguration(bubbleConfig)

// 🧩 Chat Window Background
val windowConfig = NCWChatWindowConfig(
    backgroundColor = "#FFFFFF"                         // Chat screen background
)
NCWChatSdk.updateChatWindowConfiguration(windowConfig)

// 🧩 Other Options
val otherConfig = NCWOtherConfig(
    downloadTranscriptButtonColor = "#008000",          // Button color
    downloadTranscriptTextColor = "#FFFFFF"             // Button text color
)
NCWChatSdk.updateOtherConfiguration(otherConfig)
```

---

### 🔔 Set Push Token

To enable push notifications:

```kotlin
val fcmToken = "your-fcm-token"
NCWChatSdk.setPushToken(fcmToken)
```

Or dynamically:

```kotlin
FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
    NCWChatSdk.setFCMToken(token)
}
```

> ⚠️ Deprecated: `setFCMToken(String)` is deprecated. Use `setPushToken(String)` instead.

---


## 🛠 Support

For SDK issues or integration help:

- 📘 [Netomi Website](https://www.netomi.com)
- 📩 [support@netomi.com](mailto\:support@netomi.com)

---

## 📄 License

```text
© 2025 Netomi. All rights reserved.
The Netomi Mobile Chat SDK may include its own license terms.
Refer to Netomi's official documentation for legal details.
```
