package com.netomi.chat.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\n\u001a\u00020\tJ\u0006\u0010\u000b\u001a\u00020\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/netomi/chat/utils/MessageSoundPlayer;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mediaPlayerBot", "Landroid/media/MediaPlayer;", "mediaPlayerUser", "playBotSound", "", "playUserSound", "release", "netomichatsdk_debug"})
public final class MessageSoundPlayer {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaPlayer mediaPlayerUser;
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaPlayer mediaPlayerBot;
    
    public MessageSoundPlayer(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void playUserSound() {
    }
    
    public final void playBotSound() {
    }
    
    public final void release() {
    }
}