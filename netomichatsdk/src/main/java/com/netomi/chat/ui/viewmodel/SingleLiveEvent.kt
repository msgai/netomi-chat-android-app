package com.netomi.chat.ui.viewmodel

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean
/**
 * A lifecycle-aware observable that sends only **new updates** after subscription.
 *
 * This class is useful for events such as **navigation** or **snackbars/toasts** where you
 * want to ensure that the event is **handled only once**, even if the configuration changes (like
 * screen rotation). Unlike `MutableLiveData`, `SingleLiveEvent` ensures that **only one observer**
 * is notified of changes.
 *
 * ## Purpose:
 * - Prevents multiple observers from receiving the same event multiple times (e.g., on rotation).
 * - Ensures that events are consumed only **once**.
 *
 * ## Usage:
 * This class is typically used for one-time events such as:
 * - Navigation actions (e.g., navigate to another screen).
 * - Showing **snackbars, toasts**, or **alerts**.
 *
 * @param T The type of data held by this event (can be `Void` if no data is needed).
 */
class SingleLiveEvent<T> : MutableLiveData<T>(), java.io.Serializable {
    /** Atomic flag to track if the event is pending. */
    private val pending = AtomicBoolean(false)

    /**
     * Observes the `SingleLiveEvent` for changes.
     *
     * This method ensures that only **one observer** will be notified of changes,
     * even if multiple observers are registered. When the event is pending, the
     * observer is notified and the pending flag is reset to `false`.
     *
     * @param owner The `LifecycleOwner` that controls the observer.
     * @param observer The `Observer` that will be notified of changes.
     */
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }
        // Observe the internal MutableLiveData
        super.observe(owner) { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }
    /**
     * Sets a new value for the `SingleLiveEvent`.
     *
     * When this method is called, the **pending flag is set to `true`**. This ensures
     * that the observer will be notified only once, even if the same event is emitted
     * multiple times during configuration changes.
     *
     * @param t The new value to be set for the event.
     */
    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    /**
     * Convenience method for cases where `T` is `Void`.
     *
     * This method allows triggering the event without passing any data,
     * making the call cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }

    companion object {
        private val TAG = "SingleLiveEvent"
    }
}