# Migration Guide

[← Back to documentation index](../README.md)

> **What this guide covers:** breaking changes between major SDK versions, and what to change in your code for each one.
>
> **Read this when:** you are upgrading to a new major version, or your build fails after upgrading the SDK.

---

## Upgrading from 3.x

`sendEventToSdk(...)` now checks every value in `data`, including values inside nested maps and lists. It also compares custom event names with the reserved names exactly. The method signature hasn't changed, so your code still compiles, but a call that used to succeed can now throw.

| What changed | What you need to do |
| --- | --- |
| `sendEventToSdk(...)` throws `NCWEventError.InvalidPayload` when any value in `data`, at any level, isn't a JSON value | Convert those values to strings, numbers, booleans, maps or lists before you send them. See [`sendEventToSdk` rejects non-JSON values anywhere in `data`](#sendeventtosdk-rejects-non-json-values-anywhere-in-data). |
| The `InvalidPayload` message is now `Payload must be JSON-serializable.` | If you compare the message text, check the exception type instead. |
| Reserved custom event names are matched exactly, so names such as `Custom` and `none` are now accepted | No change needed, unless you relied on those names being rejected. See [Reserved custom event names are matched exactly](#reserved-custom-event-names-are-matched-exactly). |

### `sendEventToSdk` rejects non-JSON values anywhere in `data`

At every level, `data` can contain only:

- `String`, `Boolean`, and numbers, including `Long` and `BigDecimal`. `Double` and `Float` values must be finite, so `NaN` and infinity are rejected.
- `null`, including `JSONObject.NULL`.
- Maps whose keys are all `String`.
- Lists, sets and other collections, and arrays.
- `JSONObject` and `JSONArray`.

Anything else, such as an enum, a `Char`, a `Date`, a `UUID` or one of your own classes, makes the call throw `NCWEventError.InvalidPayload`. So does a map or list that contains itself, directly or through another map or list. 3.x did not reject most of these values.

> ⚠️ **If the call isn't inside a `try`/`catch`, this exception crashes your app.** Code that worked in 3.x can crash after you upgrade, so check every `sendEventToSdk` call that sends one of these values, and wrap the call as shown in [Events & Authentication](events-and-auth.md#send-events-to-sdk).

**Kotlin**

```kotlin
val shippedAt = Date()

// Before: accepted in 3.x, now throws NCWEventError.InvalidPayload
NCWChatSdk.sendEventToSdk(
    type = NetomiEventType.CUSTOM,
    eventName = "order_update",
    data = mapOf(
        "status" to OrderStatus.SHIPPED,
        "details" to mapOf("shippedAt" to shippedAt)
    )
)

// After
NCWChatSdk.sendEventToSdk(
    type = NetomiEventType.CUSTOM,
    eventName = "order_update",
    data = mapOf(
        "status" to OrderStatus.SHIPPED.name,
        "details" to mapOf("shippedAt" to shippedAt.time)
    )
)
```

**Java**

```java
Date shippedAt = new Date();
Map<String, Object> details = new HashMap<>();
Map<String, Object> data = new HashMap<>();

// Before: accepted in 3.x, now throws NCWEventError.InvalidPayload
details.put("shippedAt", shippedAt);
data.put("status", OrderStatus.SHIPPED);
data.put("details", details);

// After
details.put("shippedAt", shippedAt.getTime());
data.put("status", OrderStatus.SHIPPED.name());
data.put("details", details);

NCWChatSdk.sendEventToSdk(NetomiEventType.CUSTOM, "order_update", null, data);
```

`sendEventToSdk` doesn't declare its exceptions for Java, so a Java `catch (NCWEventError e)` doesn't compile. Catch `Exception` and check the type:

```java
try {
    NCWChatSdk.sendEventToSdk(NetomiEventType.CUSTOM, "order_update", null, data);
} catch (Exception e) {
    if (e instanceof NCWEventError.InvalidPayload) {
        Log.e("ChatSDK", "Event payload has a non-JSON value", e);
    }
}
```

### Reserved custom event names are matched exactly

For `NetomiEventType.CUSTOM`, `eventName` is trimmed and then compared with `custom`, `reauthorization_success` and `reauthorization_failure`, matching case. Only those exact names throw `NCWEventError.ReservedCustomEventName`.

In 3.x the comparison ignored case and also reserved `none`, so names such as `Custom`, `REAUTHORIZATION_SUCCESS` and `none` threw. They are now sent like any other custom event name.

#### Also check

- **Check the exception type, not the message.** The `InvalidPayload` message changed from `Payload is not JSON serializable` to `Payload must be JSON-serializable.`:

  ```kotlin
  // Before
  if (e.message == "Payload is not JSON serializable") { /* ... */ }

  // After
  if (e is NCWEventError.InvalidPayload) { /* ... */ }
  ```

- **Prebuilt libraries don't need to be recompiled.** The signature is unchanged, so a library built against 3.x keeps working, but its calls get the new checks.

---

## Upgrading from 2.x

Three methods that were deprecated in earlier releases have been removed. Each one has a direct replacement that already exists in 2.x, so you can switch before you upgrade.

| Removed | Use instead |
| --- | --- |
| `initialize(context, newBotRefId, env: String, isDynamicEnv, errorHandler)` | `initialize(context, newBotRefId, environment: NCWEnvironment, isDynamicEnv, errorHandler)`. See [`initialize` with a `String` environment](#initialize-with-a-string-environment). |
| `setFCMToken(token)` | `setPushToken(token)` |
| `isInitialized()` | `isInitialized(botRefId, environment, isDynamicEnv)`. See [`isInitialized()` without arguments](#isinitialized-without-arguments). |

`sendEventToSdk(...)` has also changed: it now throws when you send `REAUTHORIZATION_SUCCESS` without a JWT. See [`REAUTHORIZATION_SUCCESS` without a JWT now throws](#reauthorization_success-without-a-jwt-now-throws).

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

### `REAUTHORIZATION_SUCCESS` without a JWT now throws

`sendEventToSdk(...)` with `type = NetomiEventType.REAUTHORIZATION_SUCCESS` now throws `NCWEventError.MissingJWT` when `jwtToken` is `null`, empty or only whitespace. In 2.x the SDK accepted the call and told your bot that reauthorization had succeeded, without signing the user in again.

The method signature is unchanged, so your code still compiles. If the call isn't inside a `try`/`catch`, your app now crashes when there's no JWT to send.

When the SDK throws, it sends nothing to the chat, and the chat keeps waiting for your response with the message input disabled. Catch the error, then either call again with a valid JWT or send `REAUTHORIZATION_FAILURE`.

**Kotlin**

```kotlin
// Before
NCWChatSdk.sendEventToSdk(
    type = NetomiEventType.REAUTHORIZATION_SUCCESS,
    jwtToken = newJwt,
    data = mapOf("userId" to "1234")
)

// After
try {
    NCWChatSdk.sendEventToSdk(
        type = NetomiEventType.REAUTHORIZATION_SUCCESS,
        jwtToken = newJwt,
        data = mapOf("userId" to "1234")
    )
} catch (e: NCWEventError.MissingJWT) {
    NCWChatSdk.sendEventToSdk(
        type = NetomiEventType.REAUTHORIZATION_FAILURE,
        data = mapOf("reason" to "No JWT available")
    )
}
```

**Java**

`sendEventToSdk` doesn't declare the exception for Java, so catch `Exception` and check its type:

```java
// Before
NCWChatSdk.sendEventToSdk(NetomiEventType.REAUTHORIZATION_SUCCESS, null, newJwt, data);

// After
try {
    NCWChatSdk.sendEventToSdk(NetomiEventType.REAUTHORIZATION_SUCCESS, null, newJwt, data);
} catch (Exception e) {
    if (e instanceof NCWEventError.MissingJWT) {
        NCWChatSdk.sendEventToSdk(NetomiEventType.REAUTHORIZATION_FAILURE, null, null, failureData);
    } else {
        Log.e("ChatSDK", "Failed: " + e.getMessage(), e);
    }
}
```

The SDK checks the JWT before anything else, so the call throws even if the chat session has already timed out. See [Events & Authentication → Reauthorization Success](events-and-auth.md#reauthorization-success).

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
- Reauthorization and `sendEventToSdk` → **[Events & Authentication](events-and-auth.md#send-events-to-sdk)**
- Something not working → **[Troubleshooting & FAQ](troubleshooting.md)**
