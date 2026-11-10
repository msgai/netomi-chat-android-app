# Migration Guide

[← Back to documentation index](../README.md)

> **What this guide covers:** breaking changes between major SDK versions, and what to change in your code for each one.
>
> **Read this when:** you are upgrading to a new major version, or your build fails after upgrading the SDK.

---

## Upgrading from 2.x

Three methods that were deprecated in earlier releases have been removed. Each one has a direct replacement that already exists in 2.x, so you can switch before you upgrade.

| Removed | Use instead |
| --- | --- |
| `initialize(context, newBotRefId, env: String, isDynamicEnv, errorHandler)` | `initialize(context, newBotRefId, environment: NCWEnvironment, isDynamicEnv, errorHandler)`. See [`initialize` with a `String` environment](#initialize-with-a-string-environment). |
| `setFCMToken(token)` | `setPushToken(token)` |
| `isInitialized()` | `isInitialized(botRefId, environment, isDynamicEnv)`. See [`isInitialized()` without arguments](#isinitialized-without-arguments). |

### `initialize` with a `String` environment

Pass an `NCWEnvironment` value instead of the environment code.

**Kotlin**

```kotlin
// Before
NCWChatSdk.initialize(context, "YOUR_BOT_REF_ID", "us")

// After
NCWChatSdk.initialize(context, "YOUR_BOT_REF_ID", NCWEnvironment.us)
```

**Java**

```java
// Before
NCWChatSdk.initialize(context, "YOUR_BOT_REF_ID", "us", false, errorHandler);

// After
NCWChatSdk.initialize(context, "YOUR_BOT_REF_ID", NCWEnvironment.us, false, errorHandler);
```

If the code comes from your own configuration at runtime, convert it with `NCWEnvironment.from(code)`. Like the removed overload, it throws `IllegalArgumentException` for an unknown code:

```kotlin
NCWChatSdk.initialize(context, botRefId, NCWEnvironment.from(envCode))
```

### `setFCMToken(...)`

`setFCMToken(token)` only forwarded to `setPushToken(token)`, so the replacement behaves the same.

```kotlin
// Before
NCWChatSdk.setFCMToken(token)

// After
NCWChatSdk.setPushToken(token)
```

From Java, `NCWChatSdk.INSTANCE.setFCMToken(token)` becomes `NCWChatSdk.setPushToken(token)`.

### `isInitialized()` without arguments

The removed method returned `true` once any configuration was initialized. The replacement returns `true` only for the configuration you pass, so pass the same values you gave `initialize(...)`:

```kotlin
// Before
val isReady = NCWChatSdk.isInitialized()

// After
val isReady = NCWChatSdk.isInitialized(
    botRefId = "YOUR_BOT_REF_ID",
    environment = NCWEnvironment.us
)
```

From Java, pass `isDynamicEnv` too: `NCWChatSdk.isInitialized("YOUR_BOT_REF_ID", NCWEnvironment.us, false)`. You rarely need this check. See [Usage → Check Initialization State](usage.md#optional-check-initialization-state).

#### Also check

- **`initialize` no longer drops analytics events that are waiting to be sent.** Until analytics finishes setting up, the SDK holds its analytics events in a queue. The removed `String` overload emptied that queue every time you called it. The `NCWEnvironment` overload has never done this. If you call `initialize` again before analytics has finished setting up, for example to switch bots, the earlier events are now sent to your [analytics tracker](installation.md#optional-analytics) once it is ready. No code change is needed. After analytics has finished setting up, events are sent straight away and this doesn't apply.
- **Prebuilt libraries must be recompiled.** Code compiled against 2.x that calls a removed method, such as a wrapper library, fails at runtime with `NoSuchMethodError` until it is rebuilt against the new version.

---

## Upgrading from 1.x

| What changed | What you need to do |
| --- | --- |
| The `launch(...)` error callback (`NCWLaunchCallback.onError`) now receives `NCWErrorData` instead of a `String` | Update every `callback` you pass to `NCWChatSdk.launch(...)`. See [Launch error callback now receives `NCWErrorData`](#launch-error-callback-now-receives-ncwerrordata). |

### Launch error callback now receives `NCWErrorData`

`NCWChatSdk.launch(...)` used to report a failure as a plain message string. It now passes an `NCWErrorData`, the same type the `initialize(...)` `errorHandler` receives, so you can tell failures apart by code instead of by message text.

The `launch(...)` method itself is unchanged. Only the value your callback receives is different, so a callback that uses that value as a `String` no longer compiles.

**Kotlin**

```kotlin
// Before
NCWChatSdk.launch(context = this, callback = { message ->
    showError(message)
})

// After
NCWChatSdk.launch(context = this, callback = { error ->
    showError(error.errorDescription)
})
```

**Java**

```java
// Before
NCWChatSdk.launch(this, null, new NCWAnimationConfig(), new NCWLaunchCallback() {
    @Override
    public void onError(String message) {
        showError(message);
    }
});

// After
NCWChatSdk.launch(this, null, new NCWAnimationConfig(), new NCWLaunchCallback() {
    @Override
    public void onError(NCWErrorData error) {
        showError(error.getErrorDescription());
    }
});
```

`errorDescription` is a one-line summary of the error. The individual fields (`type`, `exceptionCode`, `statusCode`, `statusMessage`) are listed in [Usage → With Optional Error Handling](usage.md#-with-optional-error-handling).

#### Also check

- **Branch on codes, not on message text.** The text of every launch error has changed. Compare `error.exceptionCode` (or `error.type`) instead:

  ```kotlin
  // Before
  if (message.contains("not initialized")) { /* ... */ }

  // After
  if (error.exceptionCode == "REQUIRED_BOT_REFERENCE_ID") { /* ... */ }
  ```

- **Callbacks that only interpolate the value still compile.** For example, `Log.d(TAG, "Launch failed: $error")` builds without errors, but now logs `NCWErrorData(type=…, …)`. Use `error.errorDescription` if you want the readable summary.
- **The no-network message is no longer localized.** It used to come from your bot's localized strings when available. It is now always `No internet connection`, with `exceptionCode` `NO_NETWORK`. If you show this message to your users, show your own localized text for `NO_NETWORK`.
- **The callback always runs on the main thread.** Some failures used to be reported on the thread that called `launch(...)`.
- **Prebuilt libraries must be recompiled.** Code compiled against a 1.x SDK that implements `NCWLaunchCallback`, such as a wrapper library, fails at runtime with `AbstractMethodError` until it is rebuilt against the new version.

---

## Earlier migrations

| Change | Where it's covered |
| --- | --- |
| Maven coordinate `com.netomi.chat:-android` replaced by `com.netomi.chat:chat-widget-android` | [Installation → Migration example](installation.md#migration-example) |

---

### ➡️ Related

- Launch options and error codes → **[Usage](usage.md)**
- Initialization errors (`NCWErrorData`) → **[Usage](usage.md#optional-handle-initialization-errors)**
- Something not working → **[Troubleshooting & FAQ](troubleshooting.md)**
