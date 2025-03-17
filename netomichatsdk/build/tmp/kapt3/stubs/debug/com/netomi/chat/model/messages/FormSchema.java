package com.netomi.chat.model.messages;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u001c\b\u0002\u0010\n\u001a\u0016\u0012\u0004\u0012\u00020\t\u0018\u00010\u0005j\n\u0012\u0004\u0012\u00020\t\u0018\u0001`\u0007\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\u0019\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007H\u00c6\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\u001d\u0010\u001a\u001a\u0016\u0012\u0004\u0012\u00020\t\u0018\u00010\u0005j\n\u0012\u0004\u0012\u00020\t\u0018\u0001`\u0007H\u00c6\u0003JW\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0018\b\u0002\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\u001c\b\u0002\u0010\n\u001a\u0016\u0012\u0004\u0012\u00020\t\u0018\u00010\u0005j\n\u0012\u0004\u0012\u00020\t\u0018\u0001`\u0007H\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001J\t\u0010!\u001a\u00020\tH\u00d6\u0001R.\u0010\n\u001a\u0016\u0012\u0004\u0012\u00020\t\u0018\u00010\u0005j\n\u0012\u0004\u0012\u00020\t\u0018\u0001`\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\r\u00a8\u0006\""}, d2 = {"Lcom/netomi/chat/model/messages/FormSchema;", "", "properties", "Lcom/netomi/chat/model/messages/Properties;", "schema", "Ljava/util/ArrayList;", "Lcom/netomi/chat/model/messages/Component;", "Lkotlin/collections/ArrayList;", "requestId", "", "formValues", "(Lcom/netomi/chat/model/messages/Properties;Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/ArrayList;)V", "getFormValues", "()Ljava/util/ArrayList;", "setFormValues", "(Ljava/util/ArrayList;)V", "getProperties", "()Lcom/netomi/chat/model/messages/Properties;", "getRequestId", "()Ljava/lang/String;", "setRequestId", "(Ljava/lang/String;)V", "getSchema", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "netomichatsdk_debug"})
public final class FormSchema {
    @org.jetbrains.annotations.NotNull()
    private final com.netomi.chat.model.messages.Properties properties = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.ArrayList<com.netomi.chat.model.messages.Component> schema = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String requestId;
    @org.jetbrains.annotations.Nullable()
    private java.util.ArrayList<java.lang.String> formValues;
    
    public FormSchema(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.messages.Properties properties, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.Component> schema, @org.jetbrains.annotations.Nullable()
    java.lang.String requestId, @org.jetbrains.annotations.Nullable()
    java.util.ArrayList<java.lang.String> formValues) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.messages.Properties getProperties() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.netomi.chat.model.messages.Component> getSchema() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getRequestId() {
        return null;
    }
    
    public final void setRequestId(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.ArrayList<java.lang.String> getFormValues() {
        return null;
    }
    
    public final void setFormValues(@org.jetbrains.annotations.Nullable()
    java.util.ArrayList<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.messages.Properties component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.netomi.chat.model.messages.Component> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.ArrayList<java.lang.String> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.messages.FormSchema copy(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.messages.Properties properties, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.Component> schema, @org.jetbrains.annotations.Nullable()
    java.lang.String requestId, @org.jetbrains.annotations.Nullable()
    java.util.ArrayList<java.lang.String> formValues) {
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