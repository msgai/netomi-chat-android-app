package com.netomi.chat.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0007J\b\u0010\u0007\u001a\u00020\u0006H\u0007J\b\u0010\b\u001a\u00020\u0006H\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fJ\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\fH\u0002J\b\u0010\u0014\u001a\u00020\u0006H\u0007J\b\u0010\u0015\u001a\u00020\u0006H\u0007J\b\u0010\u0016\u001a\u00020\u0006H\u0007J\b\u0010\u0017\u001a\u00020\u0006H\u0007J\b\u0010\u0018\u001a\u00020\u0006H\u0007J\b\u0010\u0019\u001a\u00020\u0006H\u0007J\b\u0010\u001a\u001a\u00020\u0006H\u0007J\b\u0010\u001b\u001a\u00020\u0006H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/netomi/chat/utils/NCWMessageUtilsTest;", "", "()V", "context", "Landroid/content/Context;", "createJsonObject", "", "jsonModel", "mergeChunksWithMltipleMessages", "mockFile", "Ljava/io/File;", "sizeMB", "", "fileName", "", "realComponent", "Lcom/netomi/chat/model/messages/Component;", "maxSizeMB", "", "previousSizeMB", "setUp", "splitIntoChunks_whenTextHasExactlyWordsPerChunk_returnsSingleChunk", "splitIntoChunks_whenTextHasLessThanWordsPerChunk_returnsSingleChunk", "splitIntoChunks_whenTextHasRemainderWords_returnsLastChunkWithRemainingWords", "splitIntoChunks_whenTextIsEmpty_returnsEmptyChunk", "splitIntoChunks_whenTextIsMultipleOfWordsPerChunk_returnsEqualChunks", "splitIntoChunks_whenTextIsSingleWord_returnsSingleChunk", "testValidateFileAttachment", "netomichatsdk_debugAndroidTest"})
public final class NCWMessageUtilsTest {
    private android.content.Context context;
    
    public NCWMessageUtilsTest() {
        super();
    }
    
    @org.junit.Before()
    public final void setUp() {
    }
    
    @org.junit.Test()
    public final void splitIntoChunks_whenTextIsMultipleOfWordsPerChunk_returnsEqualChunks() {
    }
    
    @org.junit.Test()
    public final void splitIntoChunks_whenTextHasRemainderWords_returnsLastChunkWithRemainingWords() {
    }
    
    @org.junit.Test()
    public final void splitIntoChunks_whenTextHasLessThanWordsPerChunk_returnsSingleChunk() {
    }
    
    @org.junit.Test()
    public final void splitIntoChunks_whenTextHasExactlyWordsPerChunk_returnsSingleChunk() {
    }
    
    @org.junit.Test()
    public final void splitIntoChunks_whenTextIsEmpty_returnsEmptyChunk() {
    }
    
    @org.junit.Test()
    public final void splitIntoChunks_whenTextIsSingleWord_returnsSingleChunk() {
    }
    
    @org.junit.Test()
    @kotlin.jvm.Throws(exceptionClasses = {java.lang.Exception.class})
    public final void testValidateFileAttachment() throws java.lang.Exception {
    }
    
    @org.junit.Test()
    public final void mergeChunksWithMltipleMessages() {
    }
    
    private final java.io.File mockFile(double sizeMB) {
        return null;
    }
    
    private final com.netomi.chat.model.messages.Component realComponent(long maxSizeMB, double previousSizeMB) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.io.File mockFile(@org.jetbrains.annotations.NotNull()
    java.lang.String fileName, double sizeMB) {
        return null;
    }
    
    @org.junit.Test()
    public final void createJsonObject() {
    }
    
    @org.junit.Test()
    public final void jsonModel() {
    }
}