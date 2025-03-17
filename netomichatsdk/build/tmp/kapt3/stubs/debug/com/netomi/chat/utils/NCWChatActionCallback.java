package com.netomi.chat.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u001a\u0010\t\u001a\u00020\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH&J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u0012\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u0011H&J\u0010\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H&\u00a8\u0006\u0015"}, d2 = {"Lcom/netomi/chat/utils/NCWChatActionCallback;", "", "carouselButtonAction", "", "it", "Lcom/netomi/chat/model/messages/NCWCarouselButton;", "onMediaClick", "message", "Lcom/netomi/chat/model/NCWMessage;", "onQuickReply", "option", "Lcom/netomi/chat/model/messages/NCWQuickReplyOption;", "position", "", "onRetryClicked", "onScrollToPosition", "isScrollToPosition", "", "onSourceClicked", "multipleSourceDetail", "Lcom/netomi/chat/model/messages/MultipleSourceDetail;", "netomichatsdk_debug"})
public abstract interface NCWChatActionCallback {
    
    public abstract void onQuickReply(@org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWQuickReplyOption option, int position);
    
    public abstract void onMediaClick(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.NCWMessage message);
    
    public abstract void carouselButtonAction(@org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWCarouselButton it);
    
    public abstract void onRetryClicked(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.NCWMessage message);
    
    public abstract void onSourceClicked(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.messages.MultipleSourceDetail multipleSourceDetail);
    
    public abstract void onScrollToPosition(boolean isScrollToPosition);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}