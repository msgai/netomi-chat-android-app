package com.netomi.chat.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2 = {"Lcom/netomi/chat/utils/NCWRootChecker;", "", "()V", "Companion", "netomichatsdk_debug"})
public final class NCWRootChecker {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "RootChecker";
    @org.jetbrains.annotations.NotNull()
    public static final com.netomi.chat.utils.NCWRootChecker.Companion Companion = null;
    
    public NCWRootChecker() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\u0006J\u0006\u0010\n\u001a\u00020\u0006J\u0006\u0010\u000b\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/netomi/chat/utils/NCWRootChecker$Companion;", "", "()V", "TAG", "", "isDebuggable", "", "context", "Landroid/content/Context;", "isEmulator", "isRooted", "isUsingProxy", "netomichatsdk_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * If the device is rooted, it returns true.
         */
        public final boolean isRooted() {
            return false;
        }
        
        /**
         * If the device is an emulator, it returns true.
         */
        public final boolean isEmulator() {
            return false;
        }
        
        /**
         * If the device using proxy, it returns true.
         */
        public final boolean isUsingProxy() {
            return false;
        }
        
        /**
         * If the application is debuggable, it returns true
         *
         * @param Context
         */
        public final boolean isDebuggable(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return false;
        }
    }
}