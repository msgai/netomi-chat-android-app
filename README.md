# 📱 Netomi Mobile Chat SDK Sample App (Android)

This repository contains a working example of how to integrate and customize the [Netomi Chat SDK](https://www.netomi.com) in an Android app using Kotlin.

It showcases:

* ✅ SDK initialization with `botRefId`
* ✅ JWT-based chat launching
* ✅ Runtime UI customization (header, footer, bubbles, etc.)
* ✅ Push token registration

---

## 📋 Table of Contents

* [Overview](#-overview)
* [Prerequisites](#prerequisites)
  * [Initialize SDK](#-initialize-sdk)
  * [Launch Chat](#-launch-chat)
  * [Pass JWT Token](#-pass-jwt-token-optional)
  * [Send Custom Parameters](#-send-custom-parameters)
  * [Send Custom Initial_Menu](#-Configure-Initial-Menu)
  * [Pass Custom API Headers](#-pass-custom-api-headers-optional)
  * [Apply UI Customization](#-apply-ui-customization-optional)
  * [Set Push Token](#-set-push-token)
  * [Event Handling](#-event-handling)
  * [Manage Chat UI Visibility](#-manage-chat-ui-visibility)
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
    implementation("com.netomi.chat:chat-widget-android:1.24.0")
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
implementation("com.netomi.chat:chat-widget-android:1.24.0")
```

---

### Step 3: Sync Gradle

Click **Sync Project** in Android Studio to download the SDK and required dependencies.

---

## Quick Start

### ✅ Initialize SDK
> Initialize the SDK once with your bot reference ID and environment before launching chat.

```kotlin
NCWChatSdk.initialize(
    context = requireContext(),
    newBotRefId = "YOUR_BOT_REF_ID",
    environment = NCWEnvironment.us
)
```

- `newBotRefId`: Your Netomi bot reference ID.
- `environment`: Netomi environment. Supported values: `.USProd`, `.SGProd`, `.EUProd`, `.QA`, `.QAInternal`, `.Development`.
- `isDynamicEnv`: Optional. Pass `true` only when your bot is configured for dynamic SDK configuration.

 
```kotlin
NCWChatSdk.initialize(
  context = requireContext(),
  newBotRefId = "YOUR_BOT_REF_ID",
  environment = NCWEnvironment.QA,
  isDynamicEnv = true
)
```

> The SDK safely ignores duplicate initialization calls for the same `botRefId`, `env`, and `isDynamicEnv`.
> If any of these values change, the SDK resets the current SDK state and initializes for the new configuration.
> 
> 🔹 Most visual styling can be configured via the Netomi Dashboard.  
>
> 🔹 If you'd like to customize it locally in code, see [Apply UI Customization](#-apply-ui-customization-optional)

### (Optional) Check Initialization State

- Use `isInitialized(botRefId:environment:isDynamicEnv:)` only when your app needs to check whether the SDK is already initialized for a specific configuration.

- For dynamic environment mode, pass the same `isDynamicEnv` value used during initialization:

```kotlin
var isReady = NCWChatSdk.isInitialized(
    botRefId ="YOUR_BOT_REF_ID",
    environment= NCWEnvironment.QA,
    isDynamicEnv =  true
)
```

- Returns `true` only when the SDK was initialized with the same `botRefId`, `environment`, and `isDynamicEnv`.
- Returns `false` if any value is different. For example, if you initialized with `isDynamicEnv: false`, checking with `isDynamicEnv: true` returns `false`.
- Do **not** gate `initialize()` or `launch()` based on this. The SDK safely handles repeated calls.
---

### 🚀 Launch Chat

Open the chat directly or with an optional prefilled query. You can also customize the **animation style and duration** using `NCWAnimationConfig`. If using a custom initial menu, call `setInitialMenu(_:)` before launching the chat.

#### 🔹 Basic

```kotlin
NCWChatSdk.launch(
    context = requireContext(),
    jwtToken = null,
    onError = { error ->
        Log.d("ChatSDK", "Launch failed: $error")
    }
)
```

#### 🔹 With Initial Query

```kotlin
NCWChatSdk.launchWithQuery(
    context = requireContext(),
    query = "Hello, I need help",
    jwtToken = null
)
```

#### 🔹 With Custom Animation

```kotlin
val animation = NCWAnimationConfig(animationType = NCWAnimationType.FADE, duration = 3000L)
NCWChatSdk.launch(context = requireContext(), jwtToken = "your-jwt-token" animationConfig = animation) { errorMessage ->
    Log.d("ChatSDK", "Launch with animation failed: $errorMessage")
}
```

#### 🔹 With Query + Custom Animation

```kotlin
val animation = NCWAnimationConfig(animationType = NCWAnimationType.FADE, duration = 3000L)

NCWChatSdk.launchWithQuery(context = requireContext(), "Hello, I need help",
 jwt = "your-jwt-token",
 animationConfig = animation)
```

---

### ⚙️ Animation Config

| Option            | Description                                           | Default |
|-------------------|-------------------------------------------------------|---------|
| `NCWAnimationType`   | `SYSTEM` (slide), `FADE`, or other supported preset | `.SYSTEM` |
| `duration`        | Duration of the animation in milliseconds                  | `3000L`   |

---

## 🔐 Pass JWT Token (Optional)

```kotlin
val jwt = "your-jwt-token"
NCWChatSdk.launch(context = requireContext(), jwtToken = jwt)
```

---

### 🔐 JWT Token Usage

| Use Case                     | JWT Required | Notes                                                                 |
|-------------------------------|--------------|-----------------------------------------------------------------------|
| `launch(jwtToken)`                | ❌ Optional  | Use if your bot requires authentication; otherwise pass `nil`.        |
| `launchWithQuery(jwtToken)`     | ❌ Optional  | Same as above.                                                        |
| `NetomiEventType.REAUTHORIZATION_SUCCESS`     | ✅ Required  | Must provide a valid JWT if the session started with JWT.              |
| `NetomiEventType.REAUTHORIZATION_FAILURE`     | ❌ Optional  | You can omit JWT here.                                                |
| Other events                  | ❌ Optional  | JWT is ignored if provided.                                           |

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

### 🧩 Configure Initial Menu

Use `setInitialMenu(_:)` when your app needs to override the server-configured initial menu at runtime.
Call it before launching the chat.

```kotlin

val initialMenu = NCWInitialMenuConfiguration(
  header = "How can we help you?",
  menuItems = listOf(
    NCWInitialMenuItem(
      name = "track_order",
      label = "Track Order"
    ),
    NCWInitialMenuItem(
      name = "refund_order",
      label = "Refund Order"
    )
  )
)
NCWChatSdk.setInitialMenu(initialMenu)

NCWChatSdk.launch(
    context = requireContext(),
    jwtToken = null
)

```

- `header`: Text displayed above the initial menu items.
- `menuItems`: Menu items shown in the chat. Each item requires:
  - `name`: Unique identifier or event name associated with the menu item.
  - `label`: User-visible text displayed for the menu item.
- The override is applied during chat launch and does not update an already visible chat session.
- Pass `null` or call `clearInitialMenu()` to remove the override and fall back to the server configuration.

```kotlin
NCWChatSdk.clearInitialMenu()
// or
NCWChatSdk.setInitialMenu(null)
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
val pushToken = "your-push-token"
NCWChatSdk.setPushToken(pushToken)
```

Or dynamically:

```kotlin
FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
    NCWChatSdk.setPushToken(token)
}
```

> ⚠️ Deprecated: `setFCMToken(String)` is deprecated. Use `setPushToken(String)` instead.

---

## 🪟 Manage Chat UI Visibility

You can programmatically check whether the chat UI is currently visible, resume a hidden chat, or hide/destroy it.

### 🔹 Check if Chat is Visible

```kotlin
if (NCWChatSdk.isChatVisible()) {
    Log.d("ChatSDK", "Chat is currently visible")
}
```

### 🔹 Resume a Previously Hidden Chat

```kotlin
val wasResumed = NCWChatSdk.resumeChat(
  animationConfig = NCWAnimationConfig(
  duration = 3000L, animationType = NCWAnimationType.FADE
), context= this)

if (wasResumed) {
    Log.d("ChatSDK", "Chat resumed successfully")
} else {
    Log.d("ChatSDK", "No hidden chat to resume")
}
```

If there's no hidden chat to resume, this will return `false` and be a no-op. The default animation is `SYSTEM` with a 350ms duration.

### 🔹 Hide or Destroy Chat

```kotlin

NCWChatSdk.hideChat(
    dismissBehavior = NCWDismissBehavior.HIDE,
    animationConfig = NCWAnimationConfig(
        animationType = NCWAnimationType.FADE,
        duration = 250L
    )
)
```

* `NCWDismissBehavior.DESTORY` → Tears down the UI so the next open starts fresh.  

* `NCWDismissBehavior.HIDE` → Keeps the UI off-screen so it can be resumed later.  

* `animationConfig` → Optional animation preset and duration.  

---

## 🔔 Event Handling

The SDK provides a way for your app to receive event callbacks from the SDK as well as to send custom events into it.

### Receive Events from SDK

Set up a callback to receive events from the SDK:

```kotlin
NCWChatSdk.getEventUpdatesFromSDK = { eventJson ->
    try {
        val eventType = JSONObject(eventJson).optString("event_type")

        when (eventType) {
            NCWPublicEvent.CHAT_SDK_INITIALISED.value -> {
                // Handle initialization event
                Log.d("ChatSDK", "SDK initialized")
            }

            NCWPublicEvent.REAUTHORIZATION_REQUEST.value -> {
                // Trigger reauthorization flow in your app
                Log.d("ChatSDK", "Reauthorization requested")
            }

            NCWPublicEvent.CHAT_OPENED.value -> {
                Log.d("ChatSDK", "Chat opened")
            }

            else -> {
                Log.d("ChatSDK", "Event received: $eventType")
            }
        }
    } catch (e: Exception) {
        Log.e("ChatSDK", "Error parsing event: ${e.message}")
    }
}
```

**Important:** Set up the event callback **before** initializing the SDK to ensure you don't miss any events.

### Send Events to SDK

**Use `sendEventToSdk(type:eventName:jwt:data:)` when the SDK expects a response from the host app, such as JWT reauthorization, or when you need to send a supported custom event.**

#### Reauthorization Success

```kotlin
// Send reauthorization success with JWT token
try {
  NCWChatSdk.sendEventToSdk(
    type = NetomiEventType.REAUTHORIZATION_SUCCESS,
    jwtToken = "eyJhbGciOi...",  //Required for reauthorization success
    data = mapOf("userId" to "1234")
  )
}
catch (e:Exception){
  Log.e("ChatSDK", "Failed: ${e.message}", e)
}
```


#### Reauthorization Failure

```kotlin
// Send reauthorization failure
try {
  NCWChatSdk.sendEventToSdk(
    type = NetomiEventType.REAUTHORIZATION_FAILURE,
    data = mapOf("reason" to "User cancelled")
  )
}
catch (e:Exception){
  Log.e("ChatSDK", "Failed: ${e.message}", e)
}
```

#### Custom Event

```kotlin
//Send Custom Event
try {
  NCWChatSdk.sendEventToSdk(
    type = NetomiEventType.CUSTOM,
    eventName = "html_state_update",
    data = mapOf(
      "status" to "submitted",
      "custom_attributes" to mapOf(
        "formId" to "feedback_form"
      )
    )
  )
} catch (e: Exception) {
  Log.e("ChatSDK", "Failed: ${e.message}", e)
}
```

**Parameters:**

* `type`: Event category sent to the SDK. (see [NetomiEventType](#event-types-you-can-send))

* `eventName`: Required only when `type` is `.custom`.
  - Must be non-empty.
  - Must not use reserved SDK event names such as `reauthorization_success`, `reauthorization_failure`, or `custom`.

* `jwtToken`: An optional JSON Web Token. Only required for certain events (e.g., `REAUTHORIZATION_SUCCESS`). Ignored if not applicable.

* `data`: A map containing additional payload data. Defaults to empty map.
  - Values must be compatible with JSON serialization.

### 📚 Supported Event Types

#### Events Received from SDK

| Event Type | Description |
|------------|-------------|
| `CHAT_SDK_INITIALISED` | SDK initialization completed successfully |
| `CHAT_OPENED` | Chat interface was opened |
| `REAUTHORIZATION_REQUEST` | SDK requesting reauthorization |
| `END_CHAT_CONFIRMED` | User confirmed ending the chat |
| `TRANSCRIPT_DOWNLOADED` | Chat transcript downloaded |

#### Event Types You Can Send

| Event Type                | Description                                    |
|---------------------------|------------------------------------------------|
| `REAUTHORIZATION_SUCCESS` | Reauthorization Completed Successfully         |
| `REAUTHORIZATION_FAILURE` | Reauthorization Failed                         |
| `CUSTOM`                  | Vendor/app-specific event.Requires `eventName` |
| `NONE`                    | Placeholder, No Event                          |

---

### 🔍 Enable Logging

You can set the log level at any time during app runtime:

```kotlin
if (BuildConfig.DEBUG) {
    NCWChatSdk.setupLogging(level = NetomiSDKLogLevel.INFO)
}
 ```

### 📚 Available Log Levels

| Level      | Description                                                                 |
|------------|-----------------------------------------------------------------------------|
| `NetomiSDKLogLevel.NONE`    | No logs will be printed (recommended for production).                   |
| `NetomiSDKLogLevel.ERROR`   | Prints only SDK-related public error logs.                              |
| `NetomiSDKLogLevel.INFO`    | Prints both public informational and error logs (ideal for development). |

> **Default:** `NetomiSDKLogLevel.NONE`

---

## 🛠 Support

For SDK issues or integration help:

* 📘 [Netomi Website](https://www.netomi.com)

* 📩 [support@netomi.com](mailto\:support@netomi.com)

---

## 📄 License

```text
© 2025 Netomi. All rights reserved.
The Netomi Mobile Chat SDK may include its own license terms.
Refer to Netomi's official documentation for legal details.
```
