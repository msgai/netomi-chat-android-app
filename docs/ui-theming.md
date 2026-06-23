# UI Theming

[← Back to documentation index](../README.md)

> **What this guide covers:** how to override the chat's visual styling in code: colors, the header, footer, message bubbles, and other UI elements.
>
> **Read this when:** the Netomi Dashboard styling is not enough and you need code-level control.
>
> 🔹 **Prefer the Dashboard first.** Most visual styling can be configured via the Netomi Dashboard without any code. Use the APIs below only when you need to override styling locally in the app.

## When to apply theming

Call all UI customization APIs **before `launch()`** so the overrides are applied when the chat opens. Changes made after the chat is visible are not guaranteed to take effect on the current session.

> Each configuration object below is **independent**. Apply only the ones you need. You do not have to set every property; unspecified properties keep their default values. Colors are passed as hex strings (e.g. `"#2196F3"`).

---

## 🧩 Header

The app bar at the top of the chat.

```kotlin
val headerConfig = NCWHeaderConfig(
    backgroundColor = "#2196F3",                // Header background color
    tintColor = "#FFFFFF",                      // Icon and text tint
    logoImage = "https://example.com/logo.png"  // Optional logo image
)
NCWChatSdk.updateHeaderConfiguration(headerConfig)
```

---

## 🧩 Footer

The message input area at the bottom.

```kotlin
val footerConfig = NCWFooterConfig(
    backgroundColor = "#FFFFFF",                 // Footer background
    inputBoxBackgroundColor = "#F0F0F0",         // Input box background
    isNetomiBrandingEnabled = true,              // Show "Powered by Netomi" branding
    netomiBrandingText = "Powered by Netomi",    // Branding text
    netomiBrandingTextColor = "#999999"          // Branding text color
)
NCWChatSdk.updateFooterConfiguration(footerConfig)
```

---

## 🧩 Bot Message Bubbles

Styling for bot messages and their quick replies.

```kotlin
val botConfig = NCWBotConfig(
    backgroundColor = "#E0E0E0",                 // Bot message background
    textColor = "#000000",                       // Bot message text color
    quickReplyBackgroundColor = "#D0D0D0",       // Quick reply pill color
    quickReplyBorderColor = "#D0D0D0",           // Quick reply border color
    quickReplyTextColor = "#000000"              // Quick reply text color
)
NCWChatSdk.updateBotConfiguration(botConfig)
```

---

## 🧩 User Message Bubbles

Styling for messages sent by the user.

```kotlin
val userConfig = NCWUserConfig(
    backgroundColor = "#4CAF50",                 // User message bubble color
    textColor = "#FFFFFF"                        // User message text color
)
NCWChatSdk.updateUserConfiguration(userConfig)
```

---

## 🧩 Bubble (General Style)

Shared bubble styling applied to all messages.

```kotlin
val bubbleConfig = NCWBubbleConfig(
    borderRadius = "12",                         // Corner radius for messages (string value)
    timeStampColor = "#666666"                   // Timestamp text color
)
NCWChatSdk.updateBubbleConfiguration(bubbleConfig)
```

---

## 🧩 Chat Window Background

The background behind all chat bubbles.

```kotlin
val windowConfig = NCWChatWindowConfig(
    chatWindowBackgroundColor = "#FFFFFF"        // Chat screen background
)
NCWChatSdk.updateChatWindowConfiguration(windowConfig)
```

---

## 🧩 Other Options

Styling for additional elements such as the download-transcript button.

```kotlin
val otherConfig = NCWOtherConfig(
    downloadTranscriptColor = "#008000",         // Download-transcript button color
    titleColor = "#000000",                      // Title text color
    descriptionColor = "#333333",                // Description / subtext color
    backgroundColor = "#FFFFFF"                  // Info section background
)
NCWChatSdk.updateOtherConfiguration(otherConfig)
```

---

## 🧩 Terms & Conditions

Styling for the terms and conditions bottom sheet (when enabled for your bot).

```kotlin
val termsConfig = NCWTermsThemeConfig(
    // set the properties you want to override
)
NCWChatSdk.updateTermsThemeConfiguration(termsConfig)
```

---

## 🧩 Alerts

Styling for alert banners shown in the chat (when enabled for your bot).

```kotlin
val alertsConfig = NCWAlertThemeConfig(
    // set the properties you want to override
)
NCWChatSdk.updateAlertThemeConfiguration(alertsConfig)
```

---

## ✅ Apply, Then Launch

After setting the configurations you need, launch the chat so the styling is applied:

```kotlin
// Apply styling before launching so it takes effect when the chat opens.
NCWChatSdk.launch(context = this)
```

---

### ➡️ Related

- Open the chat after styling → **[Usage](usage.md)**
- Customize the initial menu shown to users → **[Advanced](advanced.md)**
- Theming not taking effect? → **[Troubleshooting & FAQ](troubleshooting.md)**
