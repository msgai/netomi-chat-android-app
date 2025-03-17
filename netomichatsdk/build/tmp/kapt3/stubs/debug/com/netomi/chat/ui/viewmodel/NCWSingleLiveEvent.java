package com.netomi.chat.ui.viewmodel;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u0011*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003:\u0001\u0011B\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0007J \u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u000e\u0010\f\u001a\n\u0012\u0006\b\u0000\u0012\u00028\u00000\rH\u0017J\u0017\u0010\u000e\u001a\u00020\b2\b\u0010\u000f\u001a\u0004\u0018\u00018\u0000H\u0017\u00a2\u0006\u0002\u0010\u0010R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/netomi/chat/ui/viewmodel/NCWSingleLiveEvent;", "T", "Landroidx/lifecycle/MutableLiveData;", "Ljava/io/Serializable;", "()V", "pending", "Ljava/util/concurrent/atomic/AtomicBoolean;", "call", "", "observe", "owner", "Landroidx/lifecycle/LifecycleOwner;", "observer", "Landroidx/lifecycle/Observer;", "setValue", "t", "(Ljava/lang/Object;)V", "Companion", "netomichatsdk_debug"})
public final class NCWSingleLiveEvent<T extends java.lang.Object> extends androidx.lifecycle.MutableLiveData<T> implements java.io.Serializable {
    
    /**
     * Atomic flag to track if the event is pending.
     */
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicBoolean pending = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "SingleLiveEvent";
    @org.jetbrains.annotations.NotNull()
    public static final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent.Companion Companion = null;
    
    public NCWSingleLiveEvent() {
        super(null);
    }
    
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
    @java.lang.Override()
    @androidx.annotation.MainThread()
    public void observe(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.LifecycleOwner owner, @org.jetbrains.annotations.NotNull()
    androidx.lifecycle.Observer<? super T> observer) {
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
    @java.lang.Override()
    @androidx.annotation.MainThread()
    public void setValue(@org.jetbrains.annotations.Nullable()
    T t) {
    }
    
    /**
     * Convenience method for cases where `T` is `Void`.
     *
     * This method allows triggering the event without passing any data,
     * making the call cleaner.
     */
    @androidx.annotation.MainThread()
    public final void call() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/netomi/chat/ui/viewmodel/NCWSingleLiveEvent$Companion;", "", "()V", "TAG", "", "netomichatsdk_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}