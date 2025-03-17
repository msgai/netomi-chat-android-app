package com.netomi.chat.utils.analyticsManager;

/**
 * Contains all the events that can be tracked using the analytics manager.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b!\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/netomi/chat/utils/analyticsManager/AnalyticsEvents;", "", "()V", "API_ERROR", "", "ATTACHMENT_CLICKED", "ATTACHMENT_TYPE", "ATTACHMENT_UPLOADED", "ATTACHMENT_URL", "BOT_ID", "BOT_REF_ID", "CHAT_SDK_INITIALIZED", "CONVERSATION_ID", "CRASH", "CRASH_DETAILS", "END_CHAT_CONFIRMED", "END_CHAT_POPUP_SHOWN", "ERROR", "ERROR_DETAILS", "FORM_SUBMITTED", "LANGUAGE_CHANGED", "LAUNCHED_URL", "QUICK_MENU_OPENED", "REDIRECT_DIALER", "RESTART_CHAT_CONFIRMED", "RESTART_CHAT_POPUP_SHOWN", "RESUME_LATER_CONFIRMED", "SELECTED_LANGUAGE", "SETTINGS_OPENED", "SURVEY_SKIPPED", "SURVEY_SUBMITTED", "THUMBS_DOWN_CLICKED", "THUMBS_UP_CLICKED", "TRANSCRIPT_DOWNLOADED", "TRANSCRIPT_EMAIL_SENT", "URL_LAUNCHED_BUTTON", "URL_LAUNCHED_INLINE", "netomichatsdk_debug"})
public final class AnalyticsEvents {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHAT_SDK_INITIALIZED = "Chat SDK Initialised";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String END_CHAT_POPUP_SHOWN = "End Chat Popup Shown";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String END_CHAT_CONFIRMED = "End Chat Confirmed";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String RESTART_CHAT_POPUP_SHOWN = "Restart Chat Popup Shown";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String RESTART_CHAT_CONFIRMED = "Restart Chat Confirmed";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String RESUME_LATER_CONFIRMED = "Return Later Confirmed";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TRANSCRIPT_DOWNLOADED = "Transcript Downloaded";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TRANSCRIPT_EMAIL_SENT = "Transcript Email Sent";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LANGUAGE_CHANGED = "Language Changed";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FORM_SUBMITTED = "Form Submitted";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SURVEY_SUBMITTED = "Survey Submitted";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SURVEY_SKIPPED = "Survey Skipped";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String THUMBS_UP_CLICKED = "Thumbs Up Clicked";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String THUMBS_DOWN_CLICKED = "Thumbs Down Clicked";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String QUICK_MENU_OPENED = "Quick Menu Opened";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SETTINGS_OPENED = "Settings Opened";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ATTACHMENT_CLICKED = "Attachment Clicked";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ATTACHMENT_UPLOADED = "Attachment Uploaded";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String URL_LAUNCHED_BUTTON = "URL Launched Button";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String URL_LAUNCHED_INLINE = "URL Launched Inline";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REDIRECT_DIALER = "Redirect Dialer";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CRASH = "Crash";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ERROR = "Error";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String API_ERROR = "Api Error";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CONVERSATION_ID = "conversationId";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BOT_ID = "botId";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BOT_REF_ID = "botRefId";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SELECTED_LANGUAGE = "selectedlanguage";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LAUNCHED_URL = "launchedURL";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ATTACHMENT_TYPE = "attachmentType";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ATTACHMENT_URL = "attachmentURL";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CRASH_DETAILS = "crashDetails";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ERROR_DETAILS = "errorDetails";
    @org.jetbrains.annotations.NotNull()
    public static final com.netomi.chat.utils.analyticsManager.AnalyticsEvents INSTANCE = null;
    
    private AnalyticsEvents() {
        super();
    }
}