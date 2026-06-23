# Push Notifications

[← Back to documentation index](../README.md)

> **What this guide covers:** how to give the SDK a device push token so chat replies can be delivered to your users as push notifications.
>
> **Read this when:** you want the user to be notified about new chat activity while the chat UI is not in the foreground.

## How it works

The SDK does **not** request notification permission or manage your Firebase setup. Your app owns that flow. Your responsibility is to:

1. Set up Firebase Cloud Messaging (FCM) in your app and obtain a device token.
2. Hand that token to the SDK using `setPushToken(...)`.
3. Hand the SDK a fresh token whenever it changes (token refresh).

Once the SDK has a valid token, Netomi can route chat notifications to the device.

---

## Prerequisites

- Firebase Cloud Messaging configured in your app (a `google-services.json` file and the Google Services Gradle plugin).
- On Android 13 (API 33) and above, request the `POST_NOTIFICATIONS` runtime permission in your app.
- Your Netomi bot configured to send push notifications. Contact Netomi support if you are unsure.

> The SDK accepts an FCM device token through `setPushToken(...)`.

---

## Step 1 — Get the FCM token in your app

This is standard Android/Firebase setup performed by your app. Retrieve the current token and forward it to the SDK after `initialize(...)`:

```kotlin
FirebaseMessaging.getInstance().token
    .addOnSuccessListener { token ->
        NCWChatSdk.setPushToken(token)
    }
```

---

## Step 2 — Give the token to the SDK

Pass the token to the SDK as soon as you receive it (and on every refresh) from your `FirebaseMessagingService`:

```kotlin
class MyMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        NCWChatSdk.setPushToken(token)
    }
}
```

> ✅ `setPushToken(...)` is the preferred API for FCM tokens.

---

## Handling token refresh

Push tokens can change after an app reinstall, restored backup, or FCM rotation. `onNewToken(...)` fires automatically whenever the token changes — forward the new value to `setPushToken(...)` so notifications keep working.

There is no separate "update" call. Calling `setPushToken(...)` with the new value replaces the previous token.

---

## Deprecated: setFCMToken

```kotlin
// ❌ Deprecated — do not use in new code.
NCWChatSdk.setFCMToken("your-fcm-token")
```

> ⚠️ `setFCMToken(...)` is **deprecated** and forwards to `setPushToken(...)`. It exists for backward compatibility.
> **Always use `setPushToken(...)` instead.**

---

## Troubleshooting

- **Notifications never arrive**
  > Likely cause: token not passed to the SDK. Check that `setPushToken(...)` is called after `initialize(...)` and after every token refresh.

- **Notifications stop after reinstall**
  > Likely cause: stale token. Check that `onNewToken(...)` calls `setPushToken(...)` with the new value.

- **No notifications on Android 13+**
  > Likely cause: the `POST_NOTIFICATIONS` runtime permission was not granted. Request it in your app.

- **Still nothing**
  > Likely cause: bot not configured for push. Confirm with Netomi support that push notifications are enabled for your bot.

---

### ➡️ Related

- Open and manage the chat → **[Usage](usage.md)**
- React to SDK events → **[Events & Authentication](events-and-auth.md)**
- More push help → **[Troubleshooting & FAQ](troubleshooting.md)**
