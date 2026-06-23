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
  dynamic SDK configuration.

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
        Log.d("ChatSDK", "Chat launch failed: $error")
    }
)
```

- `callback` is invoked **only if the chat cannot be shown**.
- It is **not** a completion callback.

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

Resets the current conversation so the next launch starts clean. Use this, for example, after a user logs out.

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
