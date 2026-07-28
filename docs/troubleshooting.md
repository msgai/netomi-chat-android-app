# Troubleshooting & FAQ

[← Back to documentation index](../README.md)

> **What this guide covers:** common integration issues, how to debug them, and answers to frequently asked questions.
>
> **Read this when:** something is not working as expected, or you want a quick answer before reading a full guide.

---

## 🔍 Debugging guidance

Before diving into a specific symptom, turn on logging and observe SDK events. These two signals explain most issues.

### 1. Enable logging (debug builds only)

```kotlin
if (BuildConfig.DEBUG) {
    NCWChatSdk.setupLogging(level = NetomiSDKLogLevel.INFO)
}
```

| Level | Use |
| --- | --- |
| `NetomiSDKLogLevel.NONE` | Production default — no logs. |
| `NetomiSDKLogLevel.ERROR` | Public SDK error logs only. |
| `NetomiSDKLogLevel.INFO` | Public info + error logs — best for development. |

> Keep logging at `NONE` in production builds. `setupLogging(...)` only takes effect in debug builds.

### 2. Observe SDK events (including errors)

Set `getEventUpdatesFromSDK` **before** `initialize(...)` and inspect the event JSON.

```kotlin
NCWChatSdk.getEventUpdatesFromSDK = { eventJson ->
    val eventType = JSONObject(eventJson).optString("event_type")
    Log.d("ChatSDK", "SDK event: $eventType — $eventJson")
}
```

### 3. Inspect launch failures

`launch(...)` accepts an optional `callback` that is invoked **only when the chat cannot be shown**. It receives an error message string.

```kotlin
NCWChatSdk.launch(
    context = this,
    jwtToken = null,
    callback = { error ->
        Log.d("ChatSDK", "Chat launch failed: $error")
    }
)
```

---

## 🛠 Common issues

| Symptom | Likely cause | Resolution |
| --- | --- | --- |
| Chat won't launch / `callback` fires | SDK not initialized, invalid/blank `botRefId`, no network, or bot config could not be fetched | Confirm `initialize(...)` ran with valid credentials before `launch()`. Pass a `callback` and inspect the message. Set log level to `INFO`. |
| "Bot reference ID is not initialized" | `launch`/`resumeChat` called before `initialize` | Call `initialize(...)` first. |
| "Context must be FragmentActivity" | Terms/alerts are enabled but a non-`FragmentActivity` context was passed | Launch from a `FragmentActivity` so terms/alert bottom sheets can show. |
| No bot messages arrive | Real-time connection or session not established (network/auth) | Check device connectivity. For authenticated bots, confirm the JWT flow. Watch the SDK events and `INFO` logs. |
| Reauthorization keeps repeating | Missing or expired JWT on `REAUTHORIZATION_SUCCESS` | Respond to `REAUTHORIZATION_REQUEST` with a **fresh, valid** JWT via `sendEventToSdk(type = NetomiEventType.REAUTHORIZATION_SUCCESS, jwtToken = ...)`. See [Events & Authentication](events-and-auth.md). |
| `sendEventToSdk(...)` throws | Missing JWT, missing/reserved custom event name, or non-JSON payload | Provide a JWT for `REAUTHORIZATION_SUCCESS`, give a non-reserved `eventName` for `CUSTOM`, and ensure `data` is JSON-serializable. |
| UI theming not applied | `update*Configuration(...)` called **after** `launch()` | Apply all theming overrides **before** `launch()`. See [UI Theming](ui-theming.md). |
| `themeMode: "auto"` (or `overrideThemeMode(AUTO)`) always shows light, or always shows dark, ignoring the device's Dark Mode setting | Your app is forcing a specific night mode (for example, by calling AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES) or MODE_NIGHT_NO), so the system theme is never propagated to the SDK. | Remove the forced night mode or use AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM if you want AUTO to follow the device theme. If your app intentionally always uses a single appearance, use the corresponding explicit LIGHT or DARK themeMode/overrideThemeMode instead of AUTO.. See [UI Theming](ui-theming.md#auto-requires-your-app-to-support-both-appearances). |
| Push notifications never arrive | Token not handed to the SDK, stale token, or bot not configured for push | Call `setPushToken(...)` after `initialize(...)` and on every token refresh. See [Push Notifications](push-notifications.md). |
| Build error: duplicate classes / dependency conflict | A managed dependency was added manually | Remove manually added copies of AWS IoT, Lottie, Mixpanel, etc. They resolve transitively from `chat-widget-android`. See [Installation](installation.md). |
| `Dependency not found` on sync | `mavenCentral()` missing, or wrong coordinate | Add `mavenCentral()` and use `com.netomi.chat:chat-widget-android:<version>` (not the deprecated `:-android`). |
| AAR metadata error: "requires core library desugaring to be enabled" | Core library desugaring is not enabled in your app | Enable desugaring in your app's `build.gradle(.kts)`: set `isCoreLibraryDesugaringEnabled = true` in `compileOptions` and add `coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")` to dependencies. See [Installation](installation.md). |
| `RELRO ... not 16 KB aligned` warning | A non-aligned native lib elsewhere in your app | The SDK ships 16 KB-aligned libraries; check other native dependencies in your app. |

---

## ❓ Frequently asked questions

### Installation & setup

**How do I add the SDK?**
Add `mavenCentral()` to your repositories and `implementation("com.netomi.chat:chat-widget-android:<version>")` to your app module. See [Installation](installation.md).

**Do I need to add AWS, Microsoft Speech, Lottie, or Mixpanel myself?**
No. `chat-widget-android` manages those dependencies for you. Adding them manually can cause duplicate-class build errors.

**What is the minimum Android version?**
`minSdkVersion` 26 (Android 8.0). `compileSdk`/`targetSdk` 36 is recommended.

**Can I use the SDK from Java?**
Yes. Public APIs are exposed as static methods (`@JvmStatic`), so they're Java-friendly.

### Initialization & launch

**Do I need to call `initialize(...)` more than once?**
No. Call it once at app startup. The SDK safely ignores duplicate calls that use the same `botRefId`, `environment`, and `isDynamicEnv`. Changing any of those resets state and re-initializes.

**Should I gate `launch()` behind `isInitialized(...)`?**
No. The SDK handles repeated calls and internal state. Use `isInitialized(...)` only if your app specifically needs to check the current configuration.

### Authentication & users

**Is a JWT required?**
Only if your bot is configured for authenticated sessions. Otherwise pass `null` (or omit it) for a guest session. A JWT passed to a non-authenticated bot is safely ignored. See [Events & Authentication](events-and-auth.md).

**How do I switch users or handle logout?**
Call `clearChatSession(context)` to end any active conversation, dismiss the chat UI if visible, and clear the stored session state. Then launch again with the new user's JWT (authenticated) or without one (guest).

**Can I attach my own user attributes?**
Yes, with `setCustomParameter(...)` / `sendCustomParameter(name, value)`. These are metadata only — they do not authenticate the user. Do not put secrets in custom parameters or API headers.

### Notifications & customization

**Why aren't push notifications working?**
Most often the token was not handed to the SDK, or it went stale. Call `setPushToken(...)` after `initialize(...)` and on every refresh. On Android 13+, also request the `POST_NOTIFICATIONS` permission. See [Push Notifications](push-notifications.md).

**My theming changes don't show up. Why?**
UI customization must be applied **before** `launch()`. Changes made while the chat is already visible are not guaranteed to take effect on the current session. See [UI Theming](ui-theming.md).

**What's the difference between `theme(LIGHT)`, `theme(DARK)`, `theme(AUTO)`, and just calling `update*Configuration(...)` directly?**
Only `theme(DARK)` is actually different. There are just two override buckets: a **default** bucket and a **dark-only** bucket. The unscoped call, `theme(LIGHT)`, and `theme(AUTO)` are all equivalent — they all write to the same default bucket (`AUTO` is a mode *selector* for which theme is active, not a separate bucket of overrides). `theme(DARK)` is the only one that writes to the dark-only bucket, and it only takes effect while the chat is in dark mode — any property you don't set there falls back to the default bucket. If you never call `theme(DARK)`, dark mode just reuses your default overrides, so existing integrations are unaffected. See [UI Theming](ui-theming.md#scoping-code-level-overrides-to-light-or-dark).

**My app only supports light mode (or only dark) — does that break theming?**
No. Explicit `themeMode`/`overrideThemeMode` values (`LIGHT` or `DARK`) always force the chat to that style regardless of how your app is configured. Only `AUTO` is affected — it requires your app to follow the system theme (for example, by using AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM rather than forcing MODE_NIGHT_YES or MODE_NIGHT_NO), since it inherits the app's appearance rather than polling it directly.. See [UI Theming](ui-theming.md#auto-requires-your-app-to-support-both-appearances).

**Can I change the chat's language?**
Localized strings are driven by your bot configuration in the Netomi Dashboard. The SDK renders the configured language; there is no separate string-override API in the app.

---

## 🆘 Still stuck?

If logging and the steps above don't resolve the issue, contact Netomi support with:

- Your SDK version (`com.netomi.chat:chat-widget-android:<version>`).
- The `botRefId` and `environment` you initialize with.
- Relevant `INFO`-level logs and any SDK error event details.

- 📘 [Netomi Website](https://www.netomi.com)
- 📩 [support@netomi.com](mailto:support@netomi.com)

---

### ➡️ Related

- Add the SDK to your project → **[Installation](installation.md)**
- Core launch & lifecycle → **[Usage](usage.md)**
- JWT auth and events → **[Events & Authentication](events-and-auth.md)**
- Push notification setup → **[Push Notifications](push-notifications.md)**
- Privacy and secrets → **[Security & Privacy](security-and-privacy.md)**
