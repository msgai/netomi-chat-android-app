package com.netomi.chat.utils;

/**
 * A generic data class that represents the base structure of an API response.
 *
 * This class wraps the common fields returned by the NCW server, such as status codes, error codes,
 * and messages. It also holds the **result data** of type `T`, which makes the class reusable
 * for various types of API responses. Using this class ensures consistency in parsing and handling
 * responses across the Chat SDK.
 *
 * @param T The type of the data object contained in the response.
 * @property code The **HTTP status code** returned by the server.
 * @property errorCode The specific **error code** provided by the API in case of a failure.
 * @property message A **message** from the NCW server, typically indicating success or error details.
 * @property data The **result object** of type `T` containing the API's response data.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0019\u001a\u00020\u0004H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0004H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0007H\u00c6\u0003J\u000e\u0010\u001c\u001a\u00028\u0000H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000fJ<\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00028\u0000H\u00c6\u0001\u00a2\u0006\u0002\u0010\u001eJ\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003J\t\u0010\"\u001a\u00020\u0004H\u00d6\u0001J\t\u0010#\u001a\u00020\u0007H\u00d6\u0001R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR \u0010\b\u001a\u00028\u00008\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0005\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000b\"\u0004\b\u0014\u0010\rR\u001e\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018\u00a8\u0006$"}, d2 = {"Lcom/netomi/chat/utils/NCWBaseResponse;", "T", "", "code", "", "errorCode", "message", "", "data", "(IILjava/lang/String;Ljava/lang/Object;)V", "getCode", "()I", "setCode", "(I)V", "getData", "()Ljava/lang/Object;", "setData", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "getErrorCode", "setErrorCode", "getMessage", "()Ljava/lang/String;", "setMessage", "(Ljava/lang/String;)V", "component1", "component2", "component3", "component4", "copy", "(IILjava/lang/String;Ljava/lang/Object;)Lcom/netomi/chat/utils/NCWBaseResponse;", "equals", "", "other", "hashCode", "toString", "netomichatsdk_debug"})
public final class NCWBaseResponse<T extends java.lang.Object> {
    @com.google.gson.annotations.SerializedName(value = "statusCode")
    @com.google.gson.annotations.Expose()
    private int code;
    @com.google.gson.annotations.SerializedName(value = "errorCode")
    @com.google.gson.annotations.Expose()
    private int errorCode;
    @com.google.gson.annotations.SerializedName(value = "message")
    @com.google.gson.annotations.Expose()
    @org.jetbrains.annotations.NotNull()
    private java.lang.String message;
    @com.google.gson.annotations.SerializedName(value = "result")
    @com.google.gson.annotations.Expose()
    private T data;
    
    public NCWBaseResponse(int code, int errorCode, @org.jetbrains.annotations.NotNull()
    java.lang.String message, T data) {
        super();
    }
    
    public final int getCode() {
        return 0;
    }
    
    public final void setCode(int p0) {
    }
    
    public final int getErrorCode() {
        return 0;
    }
    
    public final void setErrorCode(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMessage() {
        return null;
    }
    
    public final void setMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final T getData() {
        return null;
    }
    
    public final void setData(T p0) {
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    public final T component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.utils.NCWBaseResponse<T> copy(int code, int errorCode, @org.jetbrains.annotations.NotNull()
    java.lang.String message, T data) {
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