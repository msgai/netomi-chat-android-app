
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u0006\u0010\u000e\u001a\u00020\u0006J\u0006\u0010\u000f\u001a\u00020\u0006R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"LNCWIdleTimeoutManager;", "", "idleTimeoutMillis", "", "onTimeout", "Lkotlin/Function0;", "", "(JLkotlin/jvm/functions/Function0;)V", "hasTimedOut", "", "getIdleTimeoutMillis", "()J", "initializationTime", "lastActiveTime", "checkForTimeout", "updateLastActiveTime", "netomichatsdk_debug"})
public final class NCWIdleTimeoutManager {
    private final long idleTimeoutMillis = 0L;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<kotlin.Unit> onTimeout = null;
    private long lastActiveTime;
    private boolean hasTimedOut = false;
    private final long initializationTime = 0L;
    
    public NCWIdleTimeoutManager(long idleTimeoutMillis, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onTimeout) {
        super();
    }
    
    public final long getIdleTimeoutMillis() {
        return 0L;
    }
    
    public final void updateLastActiveTime() {
    }
    
    public final void checkForTimeout() {
    }
}