# Migration Guide

[← Back to documentation index](../README.md)

> **What this guide covers:** breaking changes between major SDK versions, and what to change in your code for each one.
>
> **Read this when:** you are upgrading to a new major version, or your build fails after upgrading the SDK.

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
