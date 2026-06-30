# Events & Authentication

[← Back to documentation index](../README.md)

> **What this guide covers:** identifying users, authenticating them with JWT, receiving events **from** the SDK, and sending events **to** the SDK.
>
> **Read this when:** your bot uses authenticated sessions, or your app needs to react to chat events or send events back to the SDK.

---

## 👤 Guest vs. Authenticated Users

The SDK supports two user models. Which one applies depends on how your **bot** is configured in the Netomi Dashboard.

| | Guest (anonymous) user | Authenticated user |
| --- | --- | --- |
| **When to use** | Default. Your bot does not require sign-in. | Your bot is configured for authenticated sessions. |
| **JWT** | Not required — pass `null` (or omit it). | A valid JWT is required at launch and on reauthorization. |
| **Identity** | The SDK manages an anonymous session. | The JWT identifies the user to the Netomi backend. |
| **Typical call** | `NCWChatSdk.launch(context = this)` | `NCWChatSdk.launch(context = this, jwtToken = "your-jwt-token")` |

> ℹ️ There is no separate "login" API. A user is treated as **authenticated** when you launch the chat with a valid JWT for a bot configured to require one; otherwise the session is a **guest** session.

### Identifying the user (both models)

You can attach your own user attributes to either a guest or an authenticated session using custom parameters. This personalizes the experience and forwards metadata to the AI backend — it does **not** authenticate the user on its own.

```kotlin
NCWChatSdk.setCustomParameter(
    mutableMapOf(
        "user_id" to "12345",
        "membership_level" to "gold"
    )
)
```

> ⚠️ Custom parameters are metadata, not credentials. For authenticated bots, identity is still established by the JWT. Do not put secrets in custom parameters. See **[Advanced](advanced.md)** for the full custom-parameter API.

### Switching users / logging out

When the signed-in user changes or logs out, call `clearChatSession(context)` so the next launch starts clean. This ends any active conversation, dismisses the chat UI if it is visible, and clears the stored session state:

```kotlin
NCWChatSdk.clearChatSession(context)
```

Then launch again with the new user's JWT (authenticated) or with no JWT (guest).

---

## 🔐 JWT Authentication (Optional)

Pass a JWT when launching the chat if your bot is configured for authenticated sessions:

```kotlin
NCWChatSdk.launch(context = this, jwtToken = "your-jwt-token")
```

- A JWT is required **only** if your bot is configured for authenticated sessions.
- Passing a JWT when not required is safely ignored.

### When is a JWT required?

| Use case | JWT required | Notes |
| --- | --- | --- |
| `launch(jwtToken)` | ❌ Optional | Use when your bot requires authentication; otherwise pass `null`. |
| `launchWithQuery(jwtToken)` | ❌ Optional | Same as above. |
| `NetomiEventType.REAUTHORIZATION_SUCCESS` | ✅ Required | Must provide a valid JWT if the session started with JWT. |
| `NetomiEventType.REAUTHORIZATION_FAILURE` | ❌ Optional | You can omit the JWT. |
| Other events | ❌ Optional | The JWT is ignored if not applicable. |

---

## 🔔 Event Handling

The SDK communicates with your app in **two directions**:

- **SDK → app:** the SDK notifies you about chat activity, including initialized,
  opened, reauthorization requested, and errors.
- **App → SDK:** you send the SDK a response or a custom event, such as the
  result of a reauthorization.

### Receive Events from SDK

Assign a handler to be called whenever the SDK emits an event. Set this up
**before** `initialize(...)` so you don't miss any events. Events are delivered
as a JSON string.

```kotlin
NCWChatSdk.getEventUpdatesFromSDK = { eventJson ->
    try {
        val eventType = JSONObject(eventJson).optString("event_type")

        when (eventType) {
            NCWPublicEvent.CHAT_SDK_INITIALISED.value ->
                Log.d("ChatSDK", "SDK initialized")

            NCWPublicEvent.CHAT_OPENED.value ->
                Log.d("ChatSDK", "Chat opened")

            NCWPublicEvent.REAUTHORIZATION_REQUEST.value -> {
                // Trigger your app's reauthorization flow, then respond with
                // sendEventToSdk(...) — see below.
                Log.d("ChatSDK", "Reauthorization requested")
            }

            else -> Log.d("ChatSDK", "Event received: $eventType")
        }
    } catch (e: Exception) {
        Log.e("ChatSDK", "Error parsing event: ${e.message}")
    }
}
```

### Send Events to SDK

Use `sendEventToSdk(type, eventName?, jwtToken?, data)` when the SDK expects a response from the host app, such as JWT reauthorization. You can also use it to send a supported custom event.

> 🔁 **Reauthorization flow:** the SDK emits `REAUTHORIZATION_REQUEST`, your app re-authenticates the user, then your app responds with `REAUTHORIZATION_SUCCESS` (with a fresh JWT) or `REAUTHORIZATION_FAILURE`.

#### Reauthorization Success

```kotlin
try {
    NCWChatSdk.sendEventToSdk(
        type = NetomiEventType.REAUTHORIZATION_SUCCESS,
        jwtToken = "eyJhbGciOi...",   // Required for reauthorization success
        data = mapOf("userId" to "1234")
    )
} catch (e: Exception) {
    Log.e("ChatSDK", "Failed: ${e.message}", e)
}
```

#### Reauthorization Failure

```kotlin
try {
    NCWChatSdk.sendEventToSdk(
        type = NetomiEventType.REAUTHORIZATION_FAILURE,
        data = mapOf("reason" to "User cancelled")
    )
} catch (e: Exception) {
    Log.e("ChatSDK", "Failed: ${e.message}", e)
}
```

#### Custom Event

```kotlin
try {
    NCWChatSdk.sendEventToSdk(
        type = NetomiEventType.CUSTOM,
        eventName = "html_state_update",
        data = mapOf(
            "status" to "submitted",
            "custom_attributes" to mapOf("formId" to "feedback_form")
        )
    )
} catch (e: Exception) {
    Log.e("ChatSDK", "Failed: ${e.message}", e)
}
```

#### Parameters

- `type`: Event category sent to the SDK.
- `eventName`: Required only when `type` is `NetomiEventType.CUSTOM`.
  - Must be non-empty.
  - Must not use reserved SDK event names such as `reauthorization_success`, `reauthorization_failure`, or `custom`.
- `jwtToken`: An **optional JSON Web Token**.
  - Required for `REAUTHORIZATION_SUCCESS`.
  - Ignored if not applicable.
- `data`: A JSON-serializable map for additional payload. Defaults to an empty map.
  - Values must be compatible with JSON serialization.

### 📚 Supported Event Types

#### Events received from SDK

| Event Type | Description |
| --- | --- |
| `CHAT_SDK_INITIALISED` | SDK initialization completed successfully. |
| `CHAT_OPENED` | The chat interface was opened. |
| `REAUTHORIZATION_REQUEST` | SDK requesting reauthorization. |
| `END_CHAT_CONFIRMED` | User confirmed ending the chat. |
| `TRANSCRIPT_DOWNLOADED` | Chat transcript downloaded. |

#### Event types you can send

| Event Type | Description |
| --- | --- |
| `REAUTHORIZATION_SUCCESS` | Reauthorization completed successfully. |
| `REAUTHORIZATION_FAILURE` | Reauthorization failed. |
| `CUSTOM` | Vendor/app-specific event. Requires `eventName`. |
| `NONE` | Placeholder, no event. |

---

### ➡️ Related

- Launch the chat (with or without a JWT) → **[Usage](usage.md)**
- Pass extra metadata or custom headers → **[Advanced](advanced.md)**
- JWT handling, consent, and PII → **[Security & Privacy](security-and-privacy.md)**
- Auth or event issues? → **[Troubleshooting & FAQ](troubleshooting.md)**
