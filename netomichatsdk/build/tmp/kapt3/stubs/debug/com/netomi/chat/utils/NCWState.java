package com.netomi.chat.utils;

/**
 * A sealed class representing the state of a UI or data operation.
 * It encapsulates different states that can occur during an operation,
 * including loading, success, error, and unauthorized access.
 *
 * @param T The type of data that the state may hold.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u0004*\u0004\b\u0000\u0010\u00012\u00020\u0002:\b\u0004\u0005\u0006\u0007\b\t\n\u000bB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0003\u0082\u0001\u0005\f\r\u000e\u000f\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/netomi/chat/utils/NCWState;", "T", "", "()V", "Companion", "Error", "Loading", "LoadingType", "Resource", "SendMessageError", "Success", "UnAuthorized", "Lcom/netomi/chat/utils/NCWState$Error;", "Lcom/netomi/chat/utils/NCWState$Loading;", "Lcom/netomi/chat/utils/NCWState$SendMessageError;", "Lcom/netomi/chat/utils/NCWState$Success;", "Lcom/netomi/chat/utils/NCWState$UnAuthorized;", "netomichatsdk_debug"})
public abstract class NCWState<T extends java.lang.Object> {
    @org.jetbrains.annotations.NotNull()
    public static final com.netomi.chat.utils.NCWState.Companion Companion = null;
    
    private NCWState() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J+\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0001\u0010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\nJ(\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00050\f\"\u0004\b\u0001\u0010\u00052\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000e2\u0006\u0010\u000f\u001a\u00020\u0007J$\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0011\"\u0004\b\u0001\u0010\u00052\u0006\u0010\u000f\u001a\u00020\u00072\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013J?\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00160\u0015\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u0010\u00162\u0006\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0017\u001a\u0002H\u0016\u00a2\u0006\u0002\u0010\u0018J\'\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00050\u001a\"\u0004\b\u0001\u0010\u00052\u0006\u0010\u001b\u001a\u0002H\u00052\u0006\u0010\u000f\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u001cJ\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00050\u001e\"\u0004\b\u0001\u0010\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u00a8\u0006\u001f"}, d2 = {"Lcom/netomi/chat/utils/NCWState$Companion;", "", "()V", "error", "Lcom/netomi/chat/utils/NCWState$Error;", "T", "message", "", "code", "", "(Ljava/lang/String;Ljava/lang/Integer;)Lcom/netomi/chat/utils/NCWState$Error;", "fromResource", "Lcom/netomi/chat/utils/NCWState;", "resource", "Lcom/netomi/chat/utils/NCWState$Resource;", "apiConstant", "loading", "Lcom/netomi/chat/utils/NCWState$Loading;", "loadingType", "Lcom/netomi/chat/utils/NCWState$LoadingType;", "sendMessageError", "Lcom/netomi/chat/utils/NCWState$SendMessageError;", "P", "payload", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Object;)Lcom/netomi/chat/utils/NCWState$SendMessageError;", "success", "Lcom/netomi/chat/utils/NCWState$Success;", "data", "(Ljava/lang/Object;Ljava/lang/String;)Lcom/netomi/chat/utils/NCWState$Success;", "unAuthorized", "Lcom/netomi/chat/utils/NCWState$UnAuthorized;", "netomichatsdk_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Creates and returns a [NCWState.Loading] instance.
         *
         * @param apiConstant A string constant representing the API request type.
         * @param loadingType An optional [LoadingType] enum value indicating the type of loading.
         * @return A [NCWState.Loading] instance.
         */
        @org.jetbrains.annotations.NotNull()
        public final <T extends java.lang.Object>com.netomi.chat.utils.NCWState.Loading<T> loading(@org.jetbrains.annotations.NotNull()
        java.lang.String apiConstant, @org.jetbrains.annotations.Nullable()
        com.netomi.chat.utils.NCWState.LoadingType loadingType) {
            return null;
        }
        
        /**
         * Creates and returns a [NCWState.Success] instance.
         *
         * @param data The data to emit with status.
         * @param apiConstant A string constant representing the API request type.
         * @return A [NCWState.Success] instance.
         */
        @org.jetbrains.annotations.NotNull()
        public final <T extends java.lang.Object>com.netomi.chat.utils.NCWState.Success<T> success(T data, @org.jetbrains.annotations.NotNull()
        java.lang.String apiConstant) {
            return null;
        }
        
        /**
         * Creates and returns a [NCWState.Error] instance.
         *
         * @param message A description of the error.
         * @param code An optional error code associated with the error.
         * @return A [NCWState.Error] instance.
         */
        @org.jetbrains.annotations.NotNull()
        public final <T extends java.lang.Object>com.netomi.chat.utils.NCWState.Error<T> error(@org.jetbrains.annotations.NotNull()
        java.lang.String message, @org.jetbrains.annotations.Nullable()
        java.lang.Integer code) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final <T extends java.lang.Object, P extends java.lang.Object>com.netomi.chat.utils.NCWState.SendMessageError<T, P> sendMessageError(@org.jetbrains.annotations.NotNull()
        java.lang.String message, @org.jetbrains.annotations.Nullable()
        java.lang.Integer code, P payload) {
            return null;
        }
        
        /**
         * Creates and returns a [NCWState.UnAuthorized] instance.
         *
         * @param message A description of the unauthorized access.
         * @return A [NCWState.UnAuthorized] instance.
         */
        @org.jetbrains.annotations.NotNull()
        public final <T extends java.lang.Object>com.netomi.chat.utils.NCWState.UnAuthorized<T> unAuthorized(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
            return null;
        }
        
        /**
         * Creates a [NCWState] from a [Resource].
         *
         * @param resource The resource representing the state.
         * @param apiConstant A string constant representing the API request type.
         * @return A corresponding [NCWState] based on the provided resource.
         */
        @org.jetbrains.annotations.NotNull()
        public final <T extends java.lang.Object>com.netomi.chat.utils.NCWState<T> fromResource(@org.jetbrains.annotations.NotNull()
        com.netomi.chat.utils.NCWState.Resource<T> resource, @org.jetbrains.annotations.NotNull()
        java.lang.String apiConstant) {
            return null;
        }
    }
    
    /**
     * Represents an error that occurred during an operation.
     *
     * @param message A description of the error.
     * @param code An optional error code associated with the error.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0004H\u00c6\u0003J\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\tJ*\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001\u00a2\u0006\u0002\u0010\u0010J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0006H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0004H\u00d6\u0001R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0017"}, d2 = {"Lcom/netomi/chat/utils/NCWState$Error;", "T", "Lcom/netomi/chat/utils/NCWState;", "message", "", "code", "", "(Ljava/lang/String;Ljava/lang/Integer;)V", "getCode", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getMessage", "()Ljava/lang/String;", "component1", "component2", "copy", "(Ljava/lang/String;Ljava/lang/Integer;)Lcom/netomi/chat/utils/NCWState$Error;", "equals", "", "other", "", "hashCode", "toString", "netomichatsdk_debug"})
    public static final class Error<T extends java.lang.Object> extends com.netomi.chat.utils.NCWState<T> {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String message = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer code = null;
        
        public Error(@org.jetbrains.annotations.NotNull()
        java.lang.String message, @org.jetbrains.annotations.Nullable()
        java.lang.Integer code) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getCode() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.netomi.chat.utils.NCWState.Error<T> copy(@org.jetbrains.annotations.NotNull()
        java.lang.String message, @org.jetbrains.annotations.Nullable()
        java.lang.Integer code) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    /**
     * Represents a loading state during an operation.
     *
     * @param apiConstant A string constant representing the API request type.
     * @param enum An optional [LoadingType] enum value indicating the type of loading.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\bR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0019\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\r"}, d2 = {"Lcom/netomi/chat/utils/NCWState$Loading;", "T", "Lcom/netomi/chat/utils/NCWState;", "apiConstant", "", "enum", "", "Lcom/netomi/chat/utils/NCWState$LoadingType;", "(Ljava/lang/String;Ljava/lang/Enum;)V", "getApiConstant", "()Ljava/lang/String;", "getEnum", "()Ljava/lang/Enum;", "netomichatsdk_debug"})
    public static final class Loading<T extends java.lang.Object> extends com.netomi.chat.utils.NCWState<T> {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String apiConstant = null;
        
        public Loading(@org.jetbrains.annotations.NotNull()
        java.lang.String apiConstant, @org.jetbrains.annotations.Nullable()
        java.lang.Enum<com.netomi.chat.utils.NCWState.LoadingType> p1_1559168) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getApiConstant() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Enum<com.netomi.chat.utils.NCWState.LoadingType> getEnum() {
            return null;
        }
    }
    
    /**
     * An enum class representing different types of loading.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/netomi/chat/utils/NCWState$LoadingType;", "", "(Ljava/lang/String;I)V", "PAGINATION", "REFRESH", "LOADER", "netomichatsdk_debug"})
    public static enum LoadingType {
        /*public static final*/ PAGINATION /* = new PAGINATION() */,
        /*public static final*/ REFRESH /* = new REFRESH() */,
        /*public static final*/ LOADER /* = new LOADER() */;
        
        LoadingType() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.netomi.chat.utils.NCWState.LoadingType> getEntries() {
            return null;
        }
    }
    
    /**
     * A sealed class representing the result of a resource operation.
     * It can represent success, failure, or unauthorized access.
     *
     * @param T The type of data that the resource may hold.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002:\u0003\u0004\u0005\u0006B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0003\u0082\u0001\u0003\u0007\b\t\u00a8\u0006\n"}, d2 = {"Lcom/netomi/chat/utils/NCWState$Resource;", "T", "", "()V", "Failed", "Success", "UnAuthorized", "Lcom/netomi/chat/utils/NCWState$Resource$Failed;", "Lcom/netomi/chat/utils/NCWState$Resource$Success;", "Lcom/netomi/chat/utils/NCWState$Resource$UnAuthorized;", "netomichatsdk_debug"})
    public static abstract class Resource<T extends java.lang.Object> {
        
        private Resource() {
            super();
        }
        
        /**
         * Represents a failed resource operation.
         *
         * @param message A description of the failure.
         */
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000*\u0004\b\u0002\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/netomi/chat/utils/NCWState$Resource$Failed;", "T", "Lcom/netomi/chat/utils/NCWState$Resource;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "netomichatsdk_debug"})
        public static final class Failed<T extends java.lang.Object> extends com.netomi.chat.utils.NCWState.Resource<T> {
            @org.jetbrains.annotations.NotNull()
            private final java.lang.String message = null;
            
            public Failed(@org.jetbrains.annotations.NotNull()
            java.lang.String message) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String getMessage() {
                return null;
            }
        }
        
        /**
         * Represents a successful resource operation.
         *
         * @param data The data returned from the successful resource operation.
         */
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000*\u0004\b\u0002\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0002\u00a2\u0006\u0002\u0010\u0004R\u0013\u0010\u0003\u001a\u00028\u0002\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\b"}, d2 = {"Lcom/netomi/chat/utils/NCWState$Resource$Success;", "T", "Lcom/netomi/chat/utils/NCWState$Resource;", "data", "(Ljava/lang/Object;)V", "getData", "()Ljava/lang/Object;", "Ljava/lang/Object;", "netomichatsdk_debug"})
        public static final class Success<T extends java.lang.Object> extends com.netomi.chat.utils.NCWState.Resource<T> {
            private final T data = null;
            
            public Success(T data) {
            }
            
            public final T getData() {
                return null;
            }
        }
        
        /**
         * Represents an unauthorized access resource operation.
         *
         * @param message A description of the unauthorized access.
         */
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000*\u0004\b\u0002\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/netomi/chat/utils/NCWState$Resource$UnAuthorized;", "T", "Lcom/netomi/chat/utils/NCWState$Resource;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "netomichatsdk_debug"})
        public static final class UnAuthorized<T extends java.lang.Object> extends com.netomi.chat.utils.NCWState.Resource<T> {
            @org.jetbrains.annotations.NotNull()
            private final java.lang.String message = null;
            
            public UnAuthorized(@org.jetbrains.annotations.NotNull()
            java.lang.String message) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String getMessage() {
                return null;
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u0000*\u0004\b\u0001\u0010\u0001*\u0004\b\u0002\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B!\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00028\u0002\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0012\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\u0014\u001a\u00028\u0002H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0010J:\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00028\u0002H\u00c6\u0001\u00a2\u0006\u0002\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u00d6\u0003J\t\u0010\u001b\u001a\u00020\u0007H\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u0005H\u00d6\u0001R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\b\u001a\u00028\u0002\u00a2\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001d"}, d2 = {"Lcom/netomi/chat/utils/NCWState$SendMessageError;", "T", "P", "Lcom/netomi/chat/utils/NCWState;", "message", "", "code", "", "payload", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Object;)V", "getCode", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getMessage", "()Ljava/lang/String;", "getPayload", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "component2", "component3", "copy", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Object;)Lcom/netomi/chat/utils/NCWState$SendMessageError;", "equals", "", "other", "", "hashCode", "toString", "netomichatsdk_debug"})
    public static final class SendMessageError<T extends java.lang.Object, P extends java.lang.Object> extends com.netomi.chat.utils.NCWState<T> {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String message = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer code = null;
        private final P payload = null;
        
        public SendMessageError(@org.jetbrains.annotations.NotNull()
        java.lang.String message, @org.jetbrains.annotations.Nullable()
        java.lang.Integer code, P payload) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getCode() {
            return null;
        }
        
        public final P getPayload() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer component2() {
            return null;
        }
        
        public final P component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.netomi.chat.utils.NCWState.SendMessageError<T, P> copy(@org.jetbrains.annotations.NotNull()
        java.lang.String message, @org.jetbrains.annotations.Nullable()
        java.lang.Integer code, P payload) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    /**
     * Represents a successful operation with data.
     *
     * @param data The data returned from the successful operation.
     * @param apiConstant A string constant representing the API request type.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00028\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\f\u001a\u00028\u0001H\u00c6\u0003\u00a2\u0006\u0002\u0010\nJ\t\u0010\r\u001a\u00020\u0005H\u00c6\u0003J(\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0003\u001a\u00028\u0001\u00a2\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0017"}, d2 = {"Lcom/netomi/chat/utils/NCWState$Success;", "T", "Lcom/netomi/chat/utils/NCWState;", "data", "apiConstant", "", "(Ljava/lang/Object;Ljava/lang/String;)V", "getApiConstant", "()Ljava/lang/String;", "getData", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "component2", "copy", "(Ljava/lang/Object;Ljava/lang/String;)Lcom/netomi/chat/utils/NCWState$Success;", "equals", "", "other", "", "hashCode", "", "toString", "netomichatsdk_debug"})
    public static final class Success<T extends java.lang.Object> extends com.netomi.chat.utils.NCWState<T> {
        private final T data = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String apiConstant = null;
        
        public Success(T data, @org.jetbrains.annotations.NotNull()
        java.lang.String apiConstant) {
        }
        
        public final T getData() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getApiConstant() {
            return null;
        }
        
        public final T component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.netomi.chat.utils.NCWState.Success<T> copy(T data, @org.jetbrains.annotations.NotNull()
        java.lang.String apiConstant) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    /**
     * Represents an unauthorized access state.
     *
     * @param message A description of the unauthorized access.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\t\u0010\b\u001a\u00020\u0004H\u00c6\u0003J\u0019\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u00c6\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u00d6\u0003J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0004H\u00d6\u0001R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0011"}, d2 = {"Lcom/netomi/chat/utils/NCWState$UnAuthorized;", "T", "Lcom/netomi/chat/utils/NCWState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "netomichatsdk_debug"})
    public static final class UnAuthorized<T extends java.lang.Object> extends com.netomi.chat.utils.NCWState<T> {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String message = null;
        
        public UnAuthorized(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.netomi.chat.utils.NCWState.UnAuthorized<T> copy(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}