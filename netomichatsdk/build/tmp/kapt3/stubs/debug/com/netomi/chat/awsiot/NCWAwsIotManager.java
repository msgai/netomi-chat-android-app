package com.netomi.chat.awsiot;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u0005\u001a\u00020\rJ\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\tJ\u0006\u0010\u0013\u001a\u00020\u0006J\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\bJ&\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\tJ\u0016\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\tJ\u0018\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u001e\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010 \u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\tJ\u0010\u0010!\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\tH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/netomi/chat/awsiot/NCWAwsIotManager;", "", "()V", "awsCredentialsProvider", "Lcom/amazonaws/auth/AWSCredentialsProvider;", "connectionStatus", "", "connectionStatusLiveData", "Lcom/netomi/chat/ui/viewmodel/NCWSingleLiveEvent;", "", "mqttManager", "Lcom/amazonaws/mobileconnectors/iot/AWSIotMqttManager;", "callBackConnectLost", "", "connect", "", "chatViewModel", "Lcom/netomi/chat/ui/viewmodel/NCWChatViewModel;", "topic", "getConnectionStatus", "getConnectionStatusLiveData", "initialize", "accessKey", "secretKey", "sessionToken", "iotEndpoint", "publishMessage", "message", "subscribeToTopic", "switchTopic", "oldTopic", "newTopic", "unsubscribeRestart", "unsubscribeToTopic", "netomichatsdk_debug"})
public final class NCWAwsIotManager {
    private static com.amazonaws.mobileconnectors.iot.AWSIotMqttManager mqttManager;
    private static com.amazonaws.auth.AWSCredentialsProvider awsCredentialsProvider;
    private static int connectionStatus = 0;
    @org.jetbrains.annotations.NotNull()
    private static final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<java.lang.String> connectionStatusLiveData = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.netomi.chat.awsiot.NCWAwsIotManager INSTANCE = null;
    
    private NCWAwsIotManager() {
        super();
    }
    
    /**
     * Initialize the IoT Manager with provided credentials.
     */
    public final void initialize(@org.jetbrains.annotations.NotNull()
    java.lang.String accessKey, @org.jetbrains.annotations.NotNull()
    java.lang.String secretKey, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionToken, @org.jetbrains.annotations.NotNull()
    java.lang.String iotEndpoint) {
    }
    
    /**
     * Connects to the AWS IoT Broker.
     */
    public final void connect(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.ui.viewmodel.NCWChatViewModel chatViewModel, @org.jetbrains.annotations.NotNull()
    java.lang.String topic) {
    }
    
    public final boolean callBackConnectLost(boolean connectionStatus) {
        return false;
    }
    
    /**
     * Unsubscribes from the current topic and subscribes to a new one.
     */
    public final void switchTopic(@org.jetbrains.annotations.NotNull()
    java.lang.String oldTopic, @org.jetbrains.annotations.NotNull()
    java.lang.String newTopic, @org.jetbrains.annotations.NotNull()
    com.netomi.chat.ui.viewmodel.NCWChatViewModel chatViewModel) {
    }
    
    /**
     * Subscribes to a topic to receive messages.
     * @param topic Topic to subscribe to.
     */
    private final void subscribeToTopic(java.lang.String topic, com.netomi.chat.ui.viewmodel.NCWChatViewModel chatViewModel) {
    }
    
    public final void unsubscribeRestart(@org.jetbrains.annotations.NotNull()
    java.lang.String topic) {
    }
    
    /**
     * Subscribes to a topic to receive messages.
     * @param topic Topic to subscribe to.
     */
    private final void unsubscribeToTopic(java.lang.String topic) {
    }
    
    /**
     * Publishes a message to the specified topic.
     * @param topic Topic to publish to.
     * @param message Message to send.
     */
    public final void publishMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String topic, @org.jetbrains.annotations.NotNull()
    java.lang.String message) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWSingleLiveEvent<java.lang.String> getConnectionStatusLiveData() {
        return null;
    }
    
    public final int getConnectionStatus() {
        return 0;
    }
}