# Advanced

[← Back to documentation index](../README.md)

> **What this guide covers:** optional controls beyond the basic launch flow: tracking consent, custom menus, custom parameters, API headers, and logging.
>
> **Read this when:** the [Usage](usage.md) flow works, but you need extra control over a specific behavior.
>
> 💡 Each section below is **independent and optional**. Use only the sections relevant to your app. Looking for **push notifications**? See **[Push Notifications](push-notifications.md)**.

---

## 🔒 Tracking Consent

Use `setTrackingConsent(...)` when your app needs to control SDK observability consent at runtime. For example, use it to honor a user's privacy choice.

```kotlin
NCWChatSdk.setTrackingConsent(NCWTrackingConsent.granted)
```

Common values are `NCWTrackingConsent.granted`, `NCWTrackingConsent.notGranted`, and `NCWTrackingConsent.pending`.

- `granted` — data is collected and uploaded to Datadog.
- `pending` — data is collected locally but not sent until consent changes.
- `notGranted` — collection stops and any stored data is discarded.

---

## 🧩 Configure Initial Menu

Use `setInitialMenu(...)` when your app needs to override the server-configured initial menu at runtime. Call it **before** launching the chat.

```kotlin
val initialMenu = NCWInitialMenuConfiguration(
    header = "How can we help you?",
    menuItems = listOf(
        NCWInitialMenuItem(name = "track_order", label = "Track Order"),
        NCWInitialMenuItem(name = "refund_order", label = "Refund Order")
    )
)

NCWChatSdk.setInitialMenu(initialMenu)
NCWChatSdk.launch(context = this)
```

- `header`: Text displayed above the initial menu items.
- `menuItems`: Menu items shown in the chat. Each item requires:
  - `name`: Unique identifier or event name associated with the menu item.
  - `label`: User-visible text displayed for the menu item.
- The override is applied during chat launch. It does not update an already visible chat session.
- Pass `null` or call `clearInitialMenu()` to remove the override and fall back to the server configuration.

```kotlin
NCWChatSdk.clearInitialMenu()
// or
NCWChatSdk.setInitialMenu(null)
```

---

## 🧩 Send Custom Parameters

Use custom parameters to personalize the chat experience or pass session-specific metadata to the AI backend.

### 🔹 Send a single key-value parameter

```kotlin
// Example: Indicate that the current user is a premium member
NCWChatSdk.sendCustomParameter(name = "user_role", value = "premium_user")
```

### 🔹 Set multiple custom parameters at once

```kotlin
// Example: Pass user profile info during initialization
val userParams = mutableMapOf(
    "user_id" to "12345",            // Unique user identifier
    "user_name" to "John Doe",       // Full name of the user
    "membership_level" to "gold",    // Membership tier (e.g., gold, silver)
    "app_version" to "7.2.0"         // App version for targeting or debugging
)

NCWChatSdk.setCustomParameter(userParams)
```

---

## 🧾 Pass Custom API Headers (Optional)

Send custom HTTP headers with each SDK API request. This is useful for authentication tokens, versioning, experiment targeting, or localization context.

```kotlin
val customHeaders = mapOf(
    "X-App-Version" to "7.2.0",                   // Current app version
    "X-Device-ID" to "device-98765",              // Unique device identifier
    "X-Platform" to "android",                    // OS platform info
    "X-User-Type" to "beta_tester",               // User group/segment
    "X-Experiment-Variant" to "A",                // A/B test group
    "X-Locale" to Locale.getDefault().toString()  // e.g., "en_US"
)

NCWChatSdk.updateApiHeaderConfiguration(customHeaders)
```

> ⚠️ These headers are automatically sent with each SDK API call.
> Do not include sensitive data such as passwords or secrets.

---

## 🔍 Enable Logging

You can set the log level at any time during app runtime. Keep logging off (`NONE`) in production. `setupLogging(...)` only takes effect in debug builds.

```kotlin
if (BuildConfig.DEBUG) {
    NCWChatSdk.setupLogging(level = NetomiSDKLogLevel.INFO)
}
```

### 📚 Available Log Levels

| Level | Description |
| --- | --- |
| `NetomiSDKLogLevel.NONE` | No logs will be printed (recommended for production). |
| `NetomiSDKLogLevel.ERROR` | Prints only SDK-related public error logs. |
| `NetomiSDKLogLevel.INFO` | Prints public informational and error logs. |

> **Default:** `NetomiSDKLogLevel.NONE`

---

### ➡️ Related

- Core launch & lifecycle → **[Usage](usage.md)**
- Push notification setup → **[Push Notifications](push-notifications.md)**
- JWT auth and events → **[Events & Authentication](events-and-auth.md)**
- Consent, privacy, secrets → **[Security & Privacy](security-and-privacy.md)**
- Something not working? → **[Troubleshooting & FAQ](troubleshooting.md)**
