# Usage

[← Back to documentation index](../README.md)

> **What this guide covers:** the core chat lifecycle: initializing the SDK, launching the chat, managing visibility, and clearing a session.
>
> **Read this when:** you want to open, hide, resume, or reset the chat. This is the main guide for day-to-day integration.
>
> 🧭 **Typical flow:** call `initialize(...)` once at app startup, then call `launch(...)` when the user taps your chat button. Everything else is optional.

---

## ✅ Initialize SDK

Initialize the SDK **once** with your bot reference ID and environment before launching chat. For example, do this in your `Application` class or when your app's session starts.

```kotlin
NCWChatSdk.initialize(
    context = applicationContext,
    newBotRefId = "YOUR_BOT_REF_ID",
    environment = NCWEnvironment.us
)
```

- `newBotRefId`: Your Netomi bot reference ID.
- `environment`: Netomi environment. Supported values are `NCWEnvironment.us`, `NCWEnvironment.sg`, `NCWEnvironment.eu`, `NCWEnvironment.qa`, `NCWEnvironment.qaint`, and `NCWEnvironment.dev`.
- `isDynamicEnv`: Optional. Pass `true` only when your bot is configured for
  dynamic SDK configuration. If no dynamic configuration is available for the bot, the SDK
  does **not** fall back to the default configuration. It reports an initialization error with
  `DYNAMIC_CONFIG_NOT_AVAILABLE` instead (see [Handle Initialization Errors](#optional-handle-initialization-errors)).

```kotlin
NCWChatSdk.initialize(
    context = applicationContext,
    newBotRefId = "YOUR_BOT_REF_ID",
    environment = NCWEnvironment.qa,
    isDynamicEnv = true
)
```

> The SDK safely ignores duplicate initialization calls that use the same `botRefId`, `environment`, and `isDynamicEnv`. If any value changes, the SDK resets its current state and initializes with the new configuration.
>
> 🔹 Most visual styling can be configured via the Netomi Dashboard.
> To customize it locally in code, see [UI Theming](ui-theming.md).

### (Optional) Handle Initialization Errors

`initialize(...)` accepts an optional `errorHandler`. It runs on the main thread, once per failure, when initialization can't succeed. Existing calls without a handler keep working unchanged.

```kotlin
NCWChatSdk.initialize(
    context = applicationContext,
    newBotRefId = "YOUR_BOT_REF_ID",
    environment = NCWEnvironment.us,
    errorHandler = { error ->
        Log.e("ChatSDK", "Initialization failed: ${error.errorDescription}")
        Log.e("ChatSDK", "Type: ${error.type}, Code: ${error.exceptionCode}")
        Log.e("ChatSDK", "Status: ${error.statusCode}, Message: ${error.statusMessage}")
    }
)
```

The handler also works with `isDynamicEnv`:

```kotlin
NCWChatSdk.initialize(
    context = applicationContext,
    newBotRefId = "YOUR_BOT_REF_ID",
    environment = NCWEnvironment.us,
    isDynamicEnv = true
) { error ->
    Log.e("ChatSDK", "Initialization error: $error")
}
```

From Java, pass `isDynamicEnv` and then the handler as a lambda:

```java
NCWChatSdk.initialize(
    getApplicationContext(),
    "YOUR_BOT_REF_ID",
    NCWEnvironment.us,
    false,
    error -> Log.e("ChatSDK", "Initialization failed: " + error.getErrorDescription())
);
```

Fields are available through getters such as `error.getExceptionCode()` and `error.getStatusCode()`.

> ⚠️ **Callback lifetime:** the SDK keeps `errorHandler` until the configuration fetch finishes, so it can run after the `Activity` or `Fragment` that called `initialize` has been destroyed. Prefer calling `initialize` from your `Application` with an app-scoped handler. If the handler touches UI, check that the component is still alive first (for example `isAdded` in a Fragment) and avoid holding the `Activity` or `Fragment` beyond its lifecycle.

The same error is also delivered as an `error` event through `getEventUpdatesFromSDK`, whether or not you pass a handler. See [Events & Authentication](events-and-auth.md#initialization-error-events).

| Situation | `type` | `exceptionCode` | `statusCode` | `statusMessage` |
| --- | --- | --- | --- | --- |
| `botRefId` is empty or blank. Reported before `initialize` returns. | `invalid_state` | `REQUIRED_BOT_REFERENCE_ID` | none | `Bot reference ID is required to launch the SDK.` |
| `botRefId` is invalid or unknown | `invalid_state` | `INVALID_BOT_REFERENCE_ID` | `403` | `Bot reference ID is invalid or not found.` |
| `isDynamicEnv = true`, but dynamic configuration is not enabled or is incomplete for the bot | `invalid_state` | `DYNAMIC_CONFIG_NOT_AVAILABLE` | none | `Dynamic bot configuration is not enabled or properly configured for the current bot.` |
| No internet connection | `network_unavailable` | `NO_NETWORK` | `-1009` | `Please check your network and try again.` |
| Request timed out | `config_fetch_failed` | `BOT_CONFIG_FETCH_FAILED` | `-1001` | `Request timed out.` |
| Connection lost | `config_fetch_failed` | `BOT_CONFIG_FETCH_FAILED` | `-1005` | `Connection lost` |
| Any other failure loading the bot configuration | `config_fetch_failed` | `BOT_CONFIG_FETCH_FAILED` | HTTP status, or `-1` | Server or system message |

If the server returns its own `type` or `exceptionCode`, those values are passed through unchanged. The status codes match the iOS SDK, so one handler can serve both platforms.

- **Timing:** an empty `botRefId` and a missing network connection are reported before `initialize` returns. Configuration-fetch errors arrive shortly afterwards.
- **Non-fatal fetch errors:** no network, and any other configuration-fetch error, don't fail initialization. The SDK stays initialized (`isInitialized(...)` returns `true` and `CHAT_SDK_INITIALISED` is still emitted). The `error` event is emitted and `errorHandler` is invoked to tell you the bot configuration wasn't loaded. The event is emitted even when you don't pass an `errorHandler`. `launch()` fetches the configuration again when called.
- **Fatal error:** an empty `botRefId` stops initialization. The SDK isn't initialized and `CHAT_SDK_INITIALISED` isn't emitted.
- **Once per failure:** each failure is reported once. Repeating `initialize` with the same values does nothing, and a fetch replaced by a newer `initialize` call never reports.
- **Unknown environment code:** the deprecated `String` overload still throws `IllegalArgumentException`. Use the `NCWEnvironment` overload to avoid this.
- **`launch()`:** works as before. It retries the configuration fetch and reports its own errors through its `callback`.
- **Upgrading:** existing source compiles unchanged, and Java code calling `initialize(context, botRefId, environment, isDynamicEnv)` keeps working without a rebuild. Kotlin code compiled against an earlier SDK version that relied on the default `isDynamicEnv` (for example a prebuilt library) must be recompiled against this version. Otherwise it fails at runtime with `NoSuchMethodError`.

### (Optional) Check Initialization State

Use `isInitialized(botRefId, environment, isDynamicEnv)` only when your app needs to check whether the SDK is already initialized for a specific configuration.

```kotlin
val isReady = NCWChatSdk.isInitialized(
    botRefId = "YOUR_BOT_REF_ID",
    environment = NCWEnvironment.us
)
```

For dynamic environment mode, pass the same `isDynamicEnv` value used during initialization:

```kotlin
val isReady = NCWChatSdk.isInitialized(
    botRefId = "YOUR_BOT_REF_ID",
    environment = NCWEnvironment.qa,
    isDynamicEnv = true
)
```

- Returns `true` only when the SDK was initialized with the same `botRefId`, `environment`, and `isDynamicEnv`.
- Returns `false` if any value is different. For example, if you initialized with `isDynamicEnv = false`, checking with `isDynamicEnv = true` returns `false`.
- Do **not** gate `initialize()` or `launch()` based on this. The SDK safely handles repeated calls.

---

## 🚀 Launch Chat

Open the chat directly, or open it with an optional prefilled query. You can also customize the **animation style and duration** using `NCWAnimationConfig`.

> ℹ️ Launch the chat from an `Activity`/`FragmentActivity` context. A `FragmentActivity` is required when your bot uses terms & conditions or alert dialogs, since those are shown as bottom sheets.

Pick the variant that matches your need:

- **Basic:** open the chat. Recommended for most apps.
- **With error handling:** know if the chat could not be shown.
- **With initial query:** prefill the user's first message.
- **With custom animation:** set a specific open animation or duration.

### 🔹 Basic (Recommended)

```kotlin
NCWChatSdk.launch(context = this)
```

Use this in most cases. The SDK handles all internal validation and state management.

### 🔹 With Optional Error Handling

```kotlin
NCWChatSdk.launch(
    context = this,
    jwtToken = null,
    callback = { error ->
        Log.e("ChatSDK", "Chat launch failed: ${error.errorDescription}")
        Log.e("ChatSDK", "Type: ${error.type}, Code: ${error.exceptionCode}, Status: ${error.statusCode}")
    }
)
```

- `callback` is invoked **only if the chat cannot be shown**.
- It is **not** a completion callback.
- It always runs on the **main thread** and receives the same `NCWErrorData` type as the `initialize(...)` error handler.

| Situation | `type` | `exceptionCode` | `statusCode` | `statusMessage` |
| --- | --- | --- | --- | --- |
| No internet connection when `launch` is called | `network_unavailable` | `NO_NETWORK` | none | `No internet connection` |
| SDK not initialized (no `botRefId`) | `invalid_state` | `REQUIRED_BOT_REFERENCE_ID` | none | `Bot reference ID is required to launch the SDK.` |
| Terms are enabled but `context` is not a `FragmentActivity` | `invalid_state` | `FRAGMENT_ACTIVITY_REQUIRED` | none | `Context must be FragmentActivity to show terms dialog when terms configuration is enabled` |
| Bot configuration, JWT login, or conversation request failed | Server value, if any | Server value, if any | HTTP status, or `-1009` / `-1001` / `-1005` / `-1` for network failures | Server or system message |

> **Upgrading from 1.x:** `NCWLaunchCallback.onError` now receives `NCWErrorData` instead of a `String`. See the [Migration Guide](migration.md#launch-error-callback-now-receives-ncwerrordata) for before/after code in Kotlin and Java.

### 🔹 With Initial Query

```kotlin
NCWChatSdk.launchWithQuery(
    context = this,
    query = "Hello, I need help",
    jwtToken = null
)
```

### 🔹 With Custom Animation

```kotlin
val animation = NCWAnimationConfig(
    animationType = NCWAnimationType.FADE,
    duration = 350L
)

NCWChatSdk.launch(
    context = this,
    jwtToken = null,
    animationConfig = animation
)
```

### 🔹 With Query + Custom Animation

```kotlin
val animation = NCWAnimationConfig(animationType = NCWAnimationType.FADE, duration = 350L)

NCWChatSdk.launchWithQuery(
    context = this,
    query = "Hello, I need help",
    jwtToken = null,
    animationConfig = animation
)
```

### ⚙️ Animation Config

| Option | Description | Default |
| --- | --- | --- |
| `animationType` | One of `NCWAnimationType.SYSTEM`, `NCWAnimationType.FADE`, `NCWAnimationType.SLIDE`, `NCWAnimationType.SCALE`, or `NCWAnimationType.NONE` | `SYSTEM` |
| `duration` | Duration of the animation in milliseconds | `350L` |

> 🔐 Passing a `jwtToken` here is only required for authenticated bots. See [Events & Authentication](events-and-auth.md) for details.

---

## 🪟 Manage Chat UI Visibility

Once the chat is open, you can check whether it is visible, hide it for later, or destroy it.

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
        animationType = NCWAnimationType.FADE,
        duration = 350L
    ),
    context = this
)

if (wasResumed) {
    Log.d("ChatSDK", "Chat resumed successfully")
} else {
    Log.d("ChatSDK", "No hidden chat to resume")
}
```

> If there is no hidden chat to resume, this returns `false` and is a no-op.

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

The `dismissBehavior` parameter controls what happens to the chat UI:

- `NCWDismissBehavior.HIDE`: Keeps the UI off-screen so it can be resumed later.
- `NCWDismissBehavior.DESTROY`: Tears down the UI so the next open starts fresh.

The `animationConfig` parameter sets the animation preset and duration.

---

## 🧩 Clear Current Chat Session Manually

Logs out the user and resets the session so the next launch starts clean. Calling this **ends any active conversation**, **dismisses the chat UI if it is currently visible**, and clears the stored session state. Use this, for example, after a user logs out.

```kotlin
NCWChatSdk.clearChatSession(context)
```

---

### ➡️ Next steps

- Style the chat in code → **[UI Theming](ui-theming.md)**
- Push notifications → **[Push Notifications](push-notifications.md)**
- Authenticated sessions → **[Events & Authentication](events-and-auth.md)**
- Configure consent, menus, headers, and logging → **[Advanced](advanced.md)**
- Something not working? → **[Troubleshooting & FAQ](troubleshooting.md)**
