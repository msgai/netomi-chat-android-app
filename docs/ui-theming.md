# UI Theming

[← Back to documentation index](../README.md)

> **What this guide covers:** how to override the chat's visual styling in code: colors, the header, footer, message bubbles, other UI elements and dark mode.
>
> **Read this when:** the Netomi Dashboard styling is not enough and you need code-level control.
>
> 🔹 **Prefer the Dashboard first.** Most visual styling can be configured via the Netomi Dashboard without any code. Use the APIs below only when you need to override styling locally in the app.

## 🌗 Light & Dark Theme

The Dashboard can configure two independent themes:

```json
{
  "lightTheme": { /* same shape as today */ },
  "darkTheme": { /* same shape — every option below is available for dark mode too */ },
  "themeMode": "LIGHT"
}
```

`themeMode` decides which theme is active by default:

| Value   | Behavior |
|---------| --- |
| `LIGHT` | Always use `lightTheme`. |
| `DARK`  | Always use `darkTheme`. |
| `AUTO`  | Follow the device's system appearance (Light/Dark). |

**Backward compatibility:** if `themeMode` is omitted (existing integrations that only configure `lightTheme`), the SDK defaults to `LIGHT` — behavior is unchanged.

### Overriding the theme mode at runtime

Use `overrideThemeMode(_:)` to force a theme mode from your app, regardless of what the Dashboard configured. Unlike the configuration APIs below, this applies **immediately** — it does not require calling before `launch()` and does not require reinitializing the SDK. In `AUTO`, the SDK keeps tracking system appearance changes live.

```kotlin
// Force dark mode, e.g. to match your app's own theme toggle
NCWChatSdk.overrideThemeMode(NCWThemeMode.DARK)

// Follow the system appearance again
NCWChatSdk.overrideThemeMode(NCWThemeMode.AUTO)

// Clear the override and fall back to the Dashboard-configured themeMode
NCWChatSdk.overrideThemeMode(null)

// Read the mode currently in effect (override if set, otherwise the configured value)
val mode = NCWChatSdk.currentThemeMode()
```

### `AUTO` requires your app to support both appearances

`LIGHT` and `DARK` always work, no matter how your app is configured — they force a concrete style on the chat regardless of anything else.

`AUTO` is different: it does not poll the device directly. It inherits whatever appearance your app process is currently allowed to display. If your app forces a single night mode by calling::

```kotlin
AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
// or
AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
```

Android prevents that app — every window in it, including the chat's — from ever seeing the other style. The device's actual Light/Dark Mode setting never reaches your app, so `AUTO` will always resolve to whichever style is locked.

|Your app's night mode| `theme(LIGHT)` / `overrideThemeMode(LIGHT)` | `theme(DARK)` / `overrideThemeMode(DARK)` | `AUTO`                   |
| --- |---------------------------------------------|-------------------------------------------|--------------------------|
| `MODE_NIGHT_NO` | ✅ works                                     | ✅ works                                   | always resolves to light |
| `MODE_NIGHT_YES` | ✅ works                                     | ✅ works                                   | always resolves to dark  |
| MODE_NIGHT_FOLLOW_SYSTEM (or no forced night mode) | ✅ works                                     | ✅ works                                   | ✅ tracks the device live |

If your app only ever supports a single appearance, that's not an issue by itself — just use the matching explicit `themeMode` (or `overrideThemeMode`) instead of `AUTO`. `AUTO` specifically requires the host app to **not** force a fixed night mode, since it has nothing else to inherit from.

### Scoping code-level overrides to light or dark

There are only **two** override buckets, no matter how you call these APIs: a **default** bucket and a **dark-only** bucket. Where each call writes to, and when each bucket is used, is fixed:

| Call | Writes to | Used when                                                                                     |
| --- | --- |-----------------------------------------------------------------------------------------------|
| `NCWChatSdk.updateBotConfiguration(config) (unscoped)` | **default** bucket | light mode — and dark mode too, *unless* you've also set a dark-specific override (see below) |
| `NCWChatSdk.theme(LIGHT).updateBotConfiguration(config)` | **default** bucket (identical to the unscoped call above) | same as above                                                                                 |
| `NCWChatSdk.theme(AUTO).updateBotConfiguration(config)` | **default** bucket (identical to the unscoped call above) | same as above — `AUTO` is a mode *selector*, not a separate bucket                            |
| `NCWChatSdk.theme(DARK).updateBotConfiguration(config)` | **dark-only** bucket | dark mode only — takes priority over the default bucket for whichever properties it sets      |

In other words: `theme(LIGHT)` and `theme(AUTO)` are just spellings of the same unscoped call — they all land in the one default bucket. The **only** call that behaves differently is `theme(DARK)`.

If you never call `theme(DARK)`, dark mode silently reuses your default overrides — nothing changes for existing integrations. Call `theme(DARK)` only for the specific properties that should look different in dark mode; everything else still falls back to the default bucket.

```kotlin
// Default bucket — used in light mode, and as the dark-mode fallback
NCWChatSdk.updateBotConfiguration(botConfig)

// Dark-only bucket — wins over the default bucket, but only while the chat is in dark mode
val darkBotConfig = NCWBotConfigOverride(
    backgroundColor = "#000000",
    textColor = "#FFFFFF"
)
NCWChatSdk.theme(NCWThemeMode.DARK).updateBotConfiguration(darkBotConfig)
```
## When to apply theming

Call all UI customization APIs **before `launch()`** so the overrides are applied when the chat opens. Changes made after the chat is visible are not guaranteed to take effect on the current session.

> Each configuration object below is **independent**. Apply only the ones you need. You do not have to set every property; unspecified properties keep their default values. Colors are passed as hex strings (e.g. `"#2196F3"`).
>⚠️ **`theme(LIGHT)` and `theme(AUTO)` are not separate buckets — they're the same call as the unscoped method shown below.** `NCWChatSdk.updateTermsConfiguration(config)`, `NCWChatSdk.theme(LIGHT).updateTermsConfiguration(config)`, and `NCWChatSdk.theme(AUTO).updateTermsConfiguration(config)` all write to the one **default** bucket — calling more than one of them just overwrites the same values again. The **only** call that writes somewhere different is `theme(DARK)`. Each section below shows the unscoped form + `theme(DARK)` for brevity; swap in `theme(LIGHT)`/`theme(AUTO)` if you prefer to be explicit, but don't call both the unscoped form *and* `theme(LIGHT)` expecting two different results. See [Scoping code-level overrides to light or dark](#scoping-code-level-overrides-to-light-or-dark) above.
---

## 🧩 Header

The app bar at the top of the chat.
**Default** — used in light mode, and as the dark-mode fallback for anything not set below:

```kotlin
val headerConfig = NCWHeaderConfig(
    backgroundColor = "#2196F3",                // Header background color
    tintColor = "#FFFFFF",                      // Icon and text tint
    logoImage = "https://example.com/logo.png"  // Optional logo image
)
NCWChatSdk.updateHeaderConfiguration(headerConfig)
```

**Dark mode override** — optional, wins over the default above only while the chat is in dark mode:

```kotlin
val darkHeader = NCWHeaderConfigOverride(
    backgroundColor = "#000000"
)
NCWChatSdk.theme(NCWThemeMode.DARK).updateHeaderConfiguration(darkHeader)
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
**Dark mode override**:

```kotlin
val darkFooter = NCWFooterConfigOverride(
    backgroundColor = "#000000",
    inputBoxTextColor = "#FFFFFF"
)
NCWChatSdk.theme(NCWThemeMode.DARK).updateFooterConfiguration(darkFooter)
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

**Dark mode override**:

```kotlin
val darkBotConfig = NCWBotConfigOverride(
    backgroundColor = "#A9A9A9", 
    textColor = "#FFFFFF"
)

NCWChatSdk.theme(NCWThemeMode.DARK).updateBotConfiguration(darkBotConfig)
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
**Dark mode override**:

```kotlin
val darkUserConfig = NCWUserConfig(
        backgroundColor = "#1976D2"
        )

NCWChatSdk.theme(NCWThemeMode.DARK).updateUserConfiguration(darkUserConfig)
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
**Dark mode override** — `borderRadius` isn't set here, so it still falls back to the default above:

```kotlin
val darkBubbleConfig = NCWBubbleConfig(
    timeStampColor = "#D3D3D3"
)

NCWChatSdk.theme(NCWThemeMode.DARK).updateBubbleConfiguration(darkBubbleConfig)
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
**Dark mode override**:

```kotlin
val darkWindowConfig = NCWChatWindowConfig(
    chatWindowBackgroundColor = "#000000"
)

NCWChatSdk.theme(NCWThemeMode.DARK).updateChatWindowConfiguration(darkWindowConfig)
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

**Dark mode override**:

```kotlin
val darkOtherConfig = NCWOtherConfig(
    backgroundColor = "#000000",
    titleColor = "#FFFFFF",
    descriptionColor = "#D3D3D3"
)

NCWChatSdk.theme(NCWThemeMode.DARK).updateOtherConfiguration(darkOtherConfig)
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

**Dark mode override**:

```kotlin
val darkTermsConfig = NCWTermsThemeConfig(
    backgroundColor = "#000000",
    titleColor = "#FFFFFF"
)

NCWChatSdk.theme(NCWThemeMode.DARK).updateTermsConfiguration(darkTermsConfig)
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
**Dark mode override**:

```kotlin
val darkAlertsConfig = NCWAlertsConfig(
    highAlert = NCWAlertConfig.defaultHigh()
    // Customize colors for dark mode as needed
)

NCWChatSdk.theme(NCWThemeMode.DARK).updateAlertsConfiguration(darkAlertsConfig)
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
