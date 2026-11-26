# Migration Guide

[← Back to documentation index](../README.md)

> **What this guide covers:** breaking changes between major SDK versions, and what to change in your code for each one.
>
> **Read this when:** you are upgrading to a new major version, or your build fails after upgrading the SDK.

---

## Upgrading from 4.0.0

This release undoes the `sendEventToSdk(...)` changes that 4.0.0 made, so the method behaves like 3.x again. One of those changes can break code written for 4.0.0.

### Breaking: reserved custom event names ignore case again

A `CUSTOM` event name is rejected when, ignoring case and leading and trailing spaces, it equals `custom`, `reauthorization_success`, `reauthorization_failure` or `none`. 4.0.0 rejected only the exact names `custom`, `reauthorization_success` and `reauthorization_failure`. So a name that 4.0.0 accepted, such as `Custom`, `CUSTOM` or `none`, now throws `NCWEventError.ReservedCustomEventName`, which crashes your app if you don't catch it. Rename the event:

**Kotlin**

```kotlin
// Before (accepted by 4.0.0)
NCWChatSdk.sendEventToSdk(type = NetomiEventType.CUSTOM, eventName = "Custom", data = emptyMap())

// After
NCWChatSdk.sendEventToSdk(type = NetomiEventType.CUSTOM, eventName = "form_submitted", data = emptyMap())
```

**Java**

```java
// Before (accepted by 4.0.0)
NCWChatSdk.sendEventToSdk(NetomiEventType.CUSTOM, "Custom", null, Collections.emptyMap());

// After
NCWChatSdk.sendEventToSdk(NetomiEventType.CUSTOM, "form_submitted", null, Collections.emptyMap());
```

### Also changed since 4.0.0

- **`NCWEventError.MissingJWT` is no longer thrown.** A `REAUTHORIZATION_SUCCESS` without a JWT emits an `error` event instead. See [`REAUTHORIZATION_SUCCESS` without a JWT](#reauthorization_success-without-a-jwt). A `try`/`catch` you added for it can stay; it never runs.
- **`data` is checked as in 3.x.** A non-JSON value nested in `data` is accepted again, a `NaN` or `Infinity` number emits an `error` event with `event_sub_type` `invalid_payload`, and the `InvalidPayload` message is `Payload is not JSON serializable` again.

---

## Upgrading from 3.x

No code changes are needed. Upgrade to the latest release, not to 4.0.0.

4.0.0 changed `sendEventToSdk(...)` so that some calls that worked in 3.x threw an exception. Releases after 4.0.0 undo those changes and behave like 3.x again, except when you send `REAUTHORIZATION_SUCCESS` without a JWT. They also emit an `error` event when `data` has a `NaN` or `Infinity` number.

| `sendEventToSdk(...)` | 3.x | 4.0.0 only | Later releases |
| --- | --- | --- | --- |
| `REAUTHORIZATION_SUCCESS` with a `null` or blank `jwtToken` | Accepted. The chat was told that reauthorization succeeded, although the user wasn't signed in again. | Throws `NCWEventError.MissingJWT` | Doesn't throw. The chat is told that reauthorization failed, and an `error` event is emitted. See [`REAUTHORIZATION_SUCCESS` without a JWT](#reauthorization_success-without-a-jwt). |
| A value nested inside `data` that isn't valid JSON, such as an object that isn't a map, list, string, number or boolean | Accepted | Throws `NCWEventError.InvalidPayload` | Accepted, as in 3.x |
| `NaN` or `Infinity` inside `data` | Accepted | Throws `NCWEventError.InvalidPayload` | Accepted, as in 3.x. An `error` event with `event_sub_type` `invalid_payload` is also emitted. |
| `data` that contains itself | Crashes with `StackOverflowError` | Throws `NCWEventError.InvalidPayload` | Throws `NCWEventError.InvalidPayload` |
| `InvalidPayload` message | `Payload is not JSON serializable` | `Payload must be JSON-serializable.` | `Payload is not JSON serializable`, as in 3.x |
| `CUSTOM` event named `Custom`, `REAUTHORIZATION_SUCCESS`, `none` or another reserved name in any letter case | Throws `NCWEventError.ReservedCustomEventName` | Accepted, unless it is exactly `custom`, `reauthorization_success` or `reauthorization_failure` | Throws `NCWEventError.ReservedCustomEventName`, as in 3.x |

If you already moved to 4.0.0, see [Upgrading from 4.0.0](#upgrading-from-400).

### `REAUTHORIZATION_SUCCESS` without a JWT

When `type` is `NetomiEventType.REAUTHORIZATION_SUCCESS` and `jwtToken` is `null`, empty or only whitespace, `sendEventToSdk(...)` doesn't throw. The SDK emits an `error` event with `event_sub_type` `missing_jwt`, and tells the chat that reauthorization failed, as if you had sent `REAUTHORIZATION_FAILURE`. If the chat session has already timed out from inactivity, only the `error` event is emitted.

In 3.x the chat was told that reauthorization succeeded. If your bot flow continued as if the user were signed in, send a valid JWT instead. To find calls that send no JWT, listen for the `error` event as shown in [Events & Authentication → Reauthorization Success](events-and-auth.md#reauthorization-success).

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
