package com.netomi.chat.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H&J \u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H&\u00a8\u0006\n"}, d2 = {"Lcom/netomi/chat/utils/NCWFeedbackActionCallback;", "", "onThumbDownClick", "", "requestId", "", "position", "", "attachmentIndex", "onThumbUpClick", "netomichatsdk_debug"})
public abstract interface NCWFeedbackActionCallback {
    
    public abstract void onThumbUpClick(@org.jetbrains.annotations.NotNull()
    java.lang.String requestId, int position, int attachmentIndex);
    
    public abstract void onThumbDownClick(@org.jetbrains.annotations.NotNull()
    java.lang.String requestId, int position, int attachmentIndex);
}