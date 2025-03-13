package com.netomi.chat.model.language;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000#\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0003\b\u00ea\u0001\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0087\u0006\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u0003\u0012\b\b\u0002\u0010 \u001a\u00020\u0003\u0012\b\b\u0002\u0010!\u001a\u00020\u0003\u0012\b\b\u0002\u0010\"\u001a\u00020\u0003\u0012\b\b\u0002\u0010#\u001a\u00020\u0003\u0012\b\b\u0002\u0010$\u001a\u00020\u0003\u0012\b\b\u0002\u0010%\u001a\u00020\u0003\u0012\b\b\u0002\u0010&\u001a\u00020\u0003\u0012\b\b\u0002\u0010\'\u001a\u00020\u0003\u0012\b\b\u0002\u0010(\u001a\u00020\u0003\u0012\b\b\u0002\u0010)\u001a\u00020\u0003\u0012\b\b\u0002\u0010*\u001a\u00020\u0003\u0012\b\b\u0002\u0010+\u001a\u00020\u0003\u0012\b\b\u0002\u0010,\u001a\u00020\u0003\u0012\b\b\u0002\u0010-\u001a\u00020\u0003\u0012\b\b\u0002\u0010.\u001a\u00020\u0003\u0012\b\b\u0002\u0010/\u001a\u00020\u0003\u0012\b\b\u0002\u00100\u001a\u00020\u0003\u0012\b\b\u0002\u00101\u001a\u00020\u0003\u0012\b\b\u0002\u00102\u001a\u00020\u0003\u0012\b\b\u0002\u00103\u001a\u00020\u0003\u0012\b\b\u0002\u00104\u001a\u00020\u0003\u0012\b\b\u0002\u00105\u001a\u00020\u0003\u0012\b\b\u0002\u00106\u001a\u00020\u0003\u0012\b\b\u0002\u00107\u001a\u00020\u0003\u0012\b\b\u0002\u00108\u001a\u00020\u0003\u0012\b\b\u0002\u00109\u001a\u00020\u0003\u0012\b\b\u0002\u0010:\u001a\u00020\u0003\u0012\b\b\u0002\u0010;\u001a\u00020\u0003\u0012\b\b\u0002\u0010<\u001a\u00020\u0003\u0012\b\b\u0002\u0010=\u001a\u00020\u0003\u0012\b\b\u0002\u0010>\u001a\u00020\u0003\u0012\b\b\u0002\u0010?\u001a\u00020\u0003\u0012\b\b\u0002\u0010@\u001a\u00020\u0003\u0012\b\b\u0002\u0010A\u001a\u00020\u0003\u0012\b\b\u0002\u0010B\u001a\u00020\u0003\u0012\b\b\u0002\u0010C\u001a\u00020\u0003\u0012\b\b\u0002\u0010D\u001a\u00020\u0003\u0012\b\b\u0002\u0010E\u001a\u00020\u0003\u0012\b\b\u0002\u0010F\u001a\u00020\u0003\u0012\b\b\u0002\u0010G\u001a\u00020\u0003\u0012\b\b\u0002\u0010H\u001a\u00020\u0003\u0012\b\b\u0002\u0010I\u001a\u00020\u0003\u0012\b\b\u0002\u0010J\u001a\u00020\u0003\u0012\b\b\u0002\u0010K\u001a\u00020\u0003\u0012\b\b\u0002\u0010L\u001a\u00020\u0003\u0012\b\b\u0002\u0010M\u001a\u00020\u0003\u0012\b\b\u0002\u0010N\u001a\u00020\u0003\u0012\b\b\u0002\u0010O\u001a\u00020\u0003\u00a2\u0006\u0002\u0010PJ\n\u0010\u009f\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00a0\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00a1\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00a2\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00a3\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00a4\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00a5\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00a6\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00a7\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00a8\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00a9\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00aa\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00ab\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00ac\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00ad\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00ae\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00af\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00b0\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00b1\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00b2\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00b3\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00b4\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00b5\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00b6\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00b7\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00b8\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00b9\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00ba\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00bb\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00bc\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00bd\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00be\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00bf\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00c0\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00c1\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00c2\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00c3\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00c4\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00c5\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00c6\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00c7\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00c8\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00c9\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00ca\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00cb\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00cc\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00cd\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00ce\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00cf\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00d0\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00d1\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00d2\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00d3\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00d4\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00d5\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00d6\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00d7\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00d8\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00d9\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00da\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00db\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00dc\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00dd\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00de\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00df\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00e0\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00e1\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00e2\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00e3\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00e4\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00e5\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00e6\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00e7\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00e8\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00e9\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00ea\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00eb\u0001\u001a\u00020\u0003H\u00c6\u0003J\u008c\u0006\u0010\u00ec\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u00032\b\b\u0002\u0010\u0017\u001a\u00020\u00032\b\b\u0002\u0010\u0018\u001a\u00020\u00032\b\b\u0002\u0010\u0019\u001a\u00020\u00032\b\b\u0002\u0010\u001a\u001a\u00020\u00032\b\b\u0002\u0010\u001b\u001a\u00020\u00032\b\b\u0002\u0010\u001c\u001a\u00020\u00032\b\b\u0002\u0010\u001d\u001a\u00020\u00032\b\b\u0002\u0010\u001e\u001a\u00020\u00032\b\b\u0002\u0010\u001f\u001a\u00020\u00032\b\b\u0002\u0010 \u001a\u00020\u00032\b\b\u0002\u0010!\u001a\u00020\u00032\b\b\u0002\u0010\"\u001a\u00020\u00032\b\b\u0002\u0010#\u001a\u00020\u00032\b\b\u0002\u0010$\u001a\u00020\u00032\b\b\u0002\u0010%\u001a\u00020\u00032\b\b\u0002\u0010&\u001a\u00020\u00032\b\b\u0002\u0010\'\u001a\u00020\u00032\b\b\u0002\u0010(\u001a\u00020\u00032\b\b\u0002\u0010)\u001a\u00020\u00032\b\b\u0002\u0010*\u001a\u00020\u00032\b\b\u0002\u0010+\u001a\u00020\u00032\b\b\u0002\u0010,\u001a\u00020\u00032\b\b\u0002\u0010-\u001a\u00020\u00032\b\b\u0002\u0010.\u001a\u00020\u00032\b\b\u0002\u0010/\u001a\u00020\u00032\b\b\u0002\u00100\u001a\u00020\u00032\b\b\u0002\u00101\u001a\u00020\u00032\b\b\u0002\u00102\u001a\u00020\u00032\b\b\u0002\u00103\u001a\u00020\u00032\b\b\u0002\u00104\u001a\u00020\u00032\b\b\u0002\u00105\u001a\u00020\u00032\b\b\u0002\u00106\u001a\u00020\u00032\b\b\u0002\u00107\u001a\u00020\u00032\b\b\u0002\u00108\u001a\u00020\u00032\b\b\u0002\u00109\u001a\u00020\u00032\b\b\u0002\u0010:\u001a\u00020\u00032\b\b\u0002\u0010;\u001a\u00020\u00032\b\b\u0002\u0010<\u001a\u00020\u00032\b\b\u0002\u0010=\u001a\u00020\u00032\b\b\u0002\u0010>\u001a\u00020\u00032\b\b\u0002\u0010?\u001a\u00020\u00032\b\b\u0002\u0010@\u001a\u00020\u00032\b\b\u0002\u0010A\u001a\u00020\u00032\b\b\u0002\u0010B\u001a\u00020\u00032\b\b\u0002\u0010C\u001a\u00020\u00032\b\b\u0002\u0010D\u001a\u00020\u00032\b\b\u0002\u0010E\u001a\u00020\u00032\b\b\u0002\u0010F\u001a\u00020\u00032\b\b\u0002\u0010G\u001a\u00020\u00032\b\b\u0002\u0010H\u001a\u00020\u00032\b\b\u0002\u0010I\u001a\u00020\u00032\b\b\u0002\u0010J\u001a\u00020\u00032\b\b\u0002\u0010K\u001a\u00020\u00032\b\b\u0002\u0010L\u001a\u00020\u00032\b\b\u0002\u0010M\u001a\u00020\u00032\b\b\u0002\u0010N\u001a\u00020\u00032\b\b\u0002\u0010O\u001a\u00020\u0003H\u00c6\u0001J\u0016\u0010\u00ed\u0001\u001a\u00030\u00ee\u00012\t\u0010\u00ef\u0001\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u000b\u0010\u00f0\u0001\u001a\u00030\u00f1\u0001H\u00d6\u0001J\n\u0010\u00f2\u0001\u001a\u00020\u0003H\u00d6\u0001R\u0011\u00101\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bQ\u0010RR\u0011\u00108\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bS\u0010RR\u0011\u00107\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bT\u0010RR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bU\u0010RR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bV\u0010RR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bW\u0010RR\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bX\u0010RR\u0011\u0010\u0011\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bY\u0010RR\u0011\u0010\u0014\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bZ\u0010RR\u0011\u0010!\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b[\u0010RR\u0011\u0010#\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\\\u0010RR\u0011\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b]\u0010RR\u0011\u0010,\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b^\u0010RR\u0011\u0010\'\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b_\u0010RR\u0011\u0010\u001f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b`\u0010RR\u0011\u0010$\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\ba\u0010RR\u0011\u0010\u001a\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bb\u0010RR\u0011\u0010\u0019\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bc\u0010RR\u0011\u00102\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bd\u0010RR\u0011\u0010C\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\be\u0010RR\u0011\u0010\u001b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bf\u0010RR\u0011\u0010;\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bg\u0010RR\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bh\u0010RR\u0011\u0010<\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bi\u0010RR\u0011\u0010\"\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bj\u0010RR\u0011\u0010=\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bk\u0010RR\u0011\u0010\u001c\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bl\u0010RR\u0011\u0010*\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bm\u0010RR\u0011\u0010-\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bn\u0010RR\u0011\u0010H\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bo\u0010RR\u0011\u0010I\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bp\u0010RR\u0011\u0010G\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bq\u0010RR\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\br\u0010RR\u0011\u0010\u0015\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bs\u0010RR\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bt\u0010RR\u0011\u00106\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bu\u0010RR\u0011\u0010A\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bv\u0010RR\u0011\u0010(\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bw\u0010RR\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bx\u0010RR\u0011\u0010B\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\by\u0010RR\u0011\u0010>\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bz\u0010RR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b{\u0010RR\u0011\u0010O\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b|\u0010RR\u0011\u0010M\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b}\u0010RR\u0011\u0010D\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b~\u0010RR\u0011\u0010K\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u007f\u0010RR\u0012\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0080\u0001\u0010RR\u0012\u00103\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0081\u0001\u0010RR\u0012\u0010 \u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0082\u0001\u0010RR\u0012\u00100\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0083\u0001\u0010RR\u0012\u0010\u0018\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0084\u0001\u0010RR\u0012\u0010:\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0085\u0001\u0010RR\u0012\u0010J\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0086\u0001\u0010RR\u0012\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0087\u0001\u0010RR\u0012\u0010L\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0088\u0001\u0010RR\u0012\u0010%\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0089\u0001\u0010RR\u0012\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u008a\u0001\u0010RR\u0012\u0010F\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u008b\u0001\u0010RR\u0012\u00104\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u008c\u0001\u0010RR\u0012\u0010&\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u008d\u0001\u0010RR\u0012\u0010/\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u008e\u0001\u0010RR\u0012\u0010N\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u008f\u0001\u0010RR\u0012\u0010)\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0090\u0001\u0010RR\u0012\u0010.\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0091\u0001\u0010RR\u0012\u0010?\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0092\u0001\u0010RR\u0012\u00109\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0093\u0001\u0010RR\u0012\u0010\u001e\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0094\u0001\u0010RR\u0012\u0010\u001d\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0095\u0001\u0010RR\u0012\u0010E\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0096\u0001\u0010RR\u0012\u0010\u0017\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0097\u0001\u0010RR\u0012\u0010\u0016\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0098\u0001\u0010RR\u0012\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u0099\u0001\u0010RR\u0012\u0010+\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u009a\u0001\u0010RR\u0012\u00105\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u009b\u0001\u0010RR\u0012\u0010@\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u009c\u0001\u0010RR\u0012\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u009d\u0001\u0010RR\u0012\u0010\f\u001a\u00020\u0003\u00a2\u0006\t\n\u0000\u001a\u0005\b\u009e\u0001\u0010R\u00a8\u0006\u00f3\u0001"}, d2 = {"Lcom/netomi/chat/model/language/NCWOtherLocalized;", "", "powered_by_netomi", "", "ask_a_question", "ai_bot", "sound", "restart_chat", "are_you_sure_you_want_to_restart", "language", "download_transcript", "session_timeout", "your_session_has_expired_due_to_inactivity", "okay", "logout", "you_have_been_logged_out", "choose_option", "camera", "upload_from_gallery", "attach_from_device", "cancel", "limit_exceed", "upload_files_maximum_size", "unsupported_file_format", "selected_files_type_is_not_supported", "connecting", "connected", "disconnected", "error", "unable_to_open_url", "unable_to_open_call", "close_chat", "return_later", "chat_session_will_be_saved", "end_chat", "chat_will_end_you_wont_able_to_resume_later", "confirm", "skip", "submit", "close", "not_sent_tap_to_retry", "thank_you", "for_completing_the_survey", "view_response", "click_to_upload", "format", "this_field_is_required", "submitted", "select", "_item_selected", "connection_error", "retry", "start_typing", "yes", "no", "add_additional_feedback", "add", "transcripts", "send_transcript", "download", "email", "enter_email", "please_provide_valid_email", "today", "yesterday", "no_network_connection", "please_check_your_network", "connection_lost", "reconnecting", "unknown", "sources", "has_transfered_the_chat_to", "has_joined_the_chat", "has_left_the_chat", "service_unavailable", "request_timed_out", "settings", "quick_menu", "successfully_signed_in", "queue_position", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "get_item_selected", "()Ljava/lang/String;", "getAdd", "getAdd_additional_feedback", "getAi_bot", "getAre_you_sure_you_want_to_restart", "getAsk_a_question", "getAttach_from_device", "getCamera", "getCancel", "getChat_session_will_be_saved", "getChat_will_end_you_wont_able_to_resume_later", "getChoose_option", "getClick_to_upload", "getClose", "getClose_chat", "getConfirm", "getConnected", "getConnecting", "getConnection_error", "getConnection_lost", "getDisconnected", "getDownload", "getDownload_transcript", "getEmail", "getEnd_chat", "getEnter_email", "getError", "getFor_completing_the_survey", "getFormat", "getHas_joined_the_chat", "getHas_left_the_chat", "getHas_transfered_the_chat_to", "getLanguage", "getLimit_exceed", "getLogout", "getNo", "getNo_network_connection", "getNot_sent_tap_to_retry", "getOkay", "getPlease_check_your_network", "getPlease_provide_valid_email", "getPowered_by_netomi", "getQueue_position", "getQuick_menu", "getReconnecting", "getRequest_timed_out", "getRestart_chat", "getRetry", "getReturn_later", "getSelect", "getSelected_files_type_is_not_supported", "getSend_transcript", "getService_unavailable", "getSession_timeout", "getSettings", "getSkip", "getSound", "getSources", "getStart_typing", "getSubmit", "getSubmitted", "getSuccessfully_signed_in", "getThank_you", "getThis_field_is_required", "getToday", "getTranscripts", "getUnable_to_open_call", "getUnable_to_open_url", "getUnknown", "getUnsupported_file_format", "getUpload_files_maximum_size", "getUpload_from_gallery", "getView_response", "getYes", "getYesterday", "getYou_have_been_logged_out", "getYour_session_has_expired_due_to_inactivity", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component35", "component36", "component37", "component38", "component39", "component4", "component40", "component41", "component42", "component43", "component44", "component45", "component46", "component47", "component48", "component49", "component5", "component50", "component51", "component52", "component53", "component54", "component55", "component56", "component57", "component58", "component59", "component6", "component60", "component61", "component62", "component63", "component64", "component65", "component66", "component67", "component68", "component69", "component7", "component70", "component71", "component72", "component73", "component74", "component75", "component76", "component77", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "netomichatsdk_debug"})
public final class NCWOtherLocalized {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String powered_by_netomi = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String ask_a_question = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String ai_bot = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sound = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String restart_chat = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String are_you_sure_you_want_to_restart = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String language = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String download_transcript = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String session_timeout = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String your_session_has_expired_due_to_inactivity = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String okay = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String logout = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String you_have_been_logged_out = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String choose_option = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String camera = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String upload_from_gallery = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String attach_from_device = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String cancel = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String limit_exceed = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String upload_files_maximum_size = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String unsupported_file_format = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String selected_files_type_is_not_supported = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String connecting = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String connected = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String disconnected = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String error = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String unable_to_open_url = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String unable_to_open_call = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String close_chat = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String return_later = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String chat_session_will_be_saved = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String end_chat = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String chat_will_end_you_wont_able_to_resume_later = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String confirm = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String skip = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String submit = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String close = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String not_sent_tap_to_retry = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String thank_you = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String for_completing_the_survey = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String view_response = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String click_to_upload = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String format = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String this_field_is_required = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String submitted = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String select = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String _item_selected = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String connection_error = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String retry = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String start_typing = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String yes = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String no = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String add_additional_feedback = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String add = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String transcripts = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String send_transcript = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String download = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String email = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String enter_email = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String please_provide_valid_email = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String today = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String yesterday = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String no_network_connection = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String please_check_your_network = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String connection_lost = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String reconnecting = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String unknown = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sources = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String has_transfered_the_chat_to = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String has_joined_the_chat = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String has_left_the_chat = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String service_unavailable = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String request_timed_out = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String settings = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String quick_menu = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String successfully_signed_in = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String queue_position = null;
    
    public NCWOtherLocalized(@org.jetbrains.annotations.NotNull()
    java.lang.String powered_by_netomi, @org.jetbrains.annotations.NotNull()
    java.lang.String ask_a_question, @org.jetbrains.annotations.NotNull()
    java.lang.String ai_bot, @org.jetbrains.annotations.NotNull()
    java.lang.String sound, @org.jetbrains.annotations.NotNull()
    java.lang.String restart_chat, @org.jetbrains.annotations.NotNull()
    java.lang.String are_you_sure_you_want_to_restart, @org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    java.lang.String download_transcript, @org.jetbrains.annotations.NotNull()
    java.lang.String session_timeout, @org.jetbrains.annotations.NotNull()
    java.lang.String your_session_has_expired_due_to_inactivity, @org.jetbrains.annotations.NotNull()
    java.lang.String okay, @org.jetbrains.annotations.NotNull()
    java.lang.String logout, @org.jetbrains.annotations.NotNull()
    java.lang.String you_have_been_logged_out, @org.jetbrains.annotations.NotNull()
    java.lang.String choose_option, @org.jetbrains.annotations.NotNull()
    java.lang.String camera, @org.jetbrains.annotations.NotNull()
    java.lang.String upload_from_gallery, @org.jetbrains.annotations.NotNull()
    java.lang.String attach_from_device, @org.jetbrains.annotations.NotNull()
    java.lang.String cancel, @org.jetbrains.annotations.NotNull()
    java.lang.String limit_exceed, @org.jetbrains.annotations.NotNull()
    java.lang.String upload_files_maximum_size, @org.jetbrains.annotations.NotNull()
    java.lang.String unsupported_file_format, @org.jetbrains.annotations.NotNull()
    java.lang.String selected_files_type_is_not_supported, @org.jetbrains.annotations.NotNull()
    java.lang.String connecting, @org.jetbrains.annotations.NotNull()
    java.lang.String connected, @org.jetbrains.annotations.NotNull()
    java.lang.String disconnected, @org.jetbrains.annotations.NotNull()
    java.lang.String error, @org.jetbrains.annotations.NotNull()
    java.lang.String unable_to_open_url, @org.jetbrains.annotations.NotNull()
    java.lang.String unable_to_open_call, @org.jetbrains.annotations.NotNull()
    java.lang.String close_chat, @org.jetbrains.annotations.NotNull()
    java.lang.String return_later, @org.jetbrains.annotations.NotNull()
    java.lang.String chat_session_will_be_saved, @org.jetbrains.annotations.NotNull()
    java.lang.String end_chat, @org.jetbrains.annotations.NotNull()
    java.lang.String chat_will_end_you_wont_able_to_resume_later, @org.jetbrains.annotations.NotNull()
    java.lang.String confirm, @org.jetbrains.annotations.NotNull()
    java.lang.String skip, @org.jetbrains.annotations.NotNull()
    java.lang.String submit, @org.jetbrains.annotations.NotNull()
    java.lang.String close, @org.jetbrains.annotations.NotNull()
    java.lang.String not_sent_tap_to_retry, @org.jetbrains.annotations.NotNull()
    java.lang.String thank_you, @org.jetbrains.annotations.NotNull()
    java.lang.String for_completing_the_survey, @org.jetbrains.annotations.NotNull()
    java.lang.String view_response, @org.jetbrains.annotations.NotNull()
    java.lang.String click_to_upload, @org.jetbrains.annotations.NotNull()
    java.lang.String format, @org.jetbrains.annotations.NotNull()
    java.lang.String this_field_is_required, @org.jetbrains.annotations.NotNull()
    java.lang.String submitted, @org.jetbrains.annotations.NotNull()
    java.lang.String select, @org.jetbrains.annotations.NotNull()
    java.lang.String _item_selected, @org.jetbrains.annotations.NotNull()
    java.lang.String connection_error, @org.jetbrains.annotations.NotNull()
    java.lang.String retry, @org.jetbrains.annotations.NotNull()
    java.lang.String start_typing, @org.jetbrains.annotations.NotNull()
    java.lang.String yes, @org.jetbrains.annotations.NotNull()
    java.lang.String no, @org.jetbrains.annotations.NotNull()
    java.lang.String add_additional_feedback, @org.jetbrains.annotations.NotNull()
    java.lang.String add, @org.jetbrains.annotations.NotNull()
    java.lang.String transcripts, @org.jetbrains.annotations.NotNull()
    java.lang.String send_transcript, @org.jetbrains.annotations.NotNull()
    java.lang.String download, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String enter_email, @org.jetbrains.annotations.NotNull()
    java.lang.String please_provide_valid_email, @org.jetbrains.annotations.NotNull()
    java.lang.String today, @org.jetbrains.annotations.NotNull()
    java.lang.String yesterday, @org.jetbrains.annotations.NotNull()
    java.lang.String no_network_connection, @org.jetbrains.annotations.NotNull()
    java.lang.String please_check_your_network, @org.jetbrains.annotations.NotNull()
    java.lang.String connection_lost, @org.jetbrains.annotations.NotNull()
    java.lang.String reconnecting, @org.jetbrains.annotations.NotNull()
    java.lang.String unknown, @org.jetbrains.annotations.NotNull()
    java.lang.String sources, @org.jetbrains.annotations.NotNull()
    java.lang.String has_transfered_the_chat_to, @org.jetbrains.annotations.NotNull()
    java.lang.String has_joined_the_chat, @org.jetbrains.annotations.NotNull()
    java.lang.String has_left_the_chat, @org.jetbrains.annotations.NotNull()
    java.lang.String service_unavailable, @org.jetbrains.annotations.NotNull()
    java.lang.String request_timed_out, @org.jetbrains.annotations.NotNull()
    java.lang.String settings, @org.jetbrains.annotations.NotNull()
    java.lang.String quick_menu, @org.jetbrains.annotations.NotNull()
    java.lang.String successfully_signed_in, @org.jetbrains.annotations.NotNull()
    java.lang.String queue_position) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPowered_by_netomi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAsk_a_question() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAi_bot() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSound() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRestart_chat() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAre_you_sure_you_want_to_restart() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLanguage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDownload_transcript() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSession_timeout() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getYour_session_has_expired_due_to_inactivity() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOkay() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLogout() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getYou_have_been_logged_out() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getChoose_option() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCamera() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUpload_from_gallery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAttach_from_device() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCancel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLimit_exceed() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUpload_files_maximum_size() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUnsupported_file_format() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSelected_files_type_is_not_supported() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getConnecting() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getConnected() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDisconnected() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getError() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUnable_to_open_url() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUnable_to_open_call() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getClose_chat() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getReturn_later() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getChat_session_will_be_saved() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEnd_chat() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getChat_will_end_you_wont_able_to_resume_later() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getConfirm() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSkip() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSubmit() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getClose() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNot_sent_tap_to_retry() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getThank_you() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFor_completing_the_survey() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getView_response() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getClick_to_upload() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormat() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getThis_field_is_required() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSubmitted() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSelect() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String get_item_selected() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getConnection_error() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRetry() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStart_typing() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getYes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAdd_additional_feedback() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAdd() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTranscripts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSend_transcript() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDownload() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEnter_email() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPlease_provide_valid_email() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getToday() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getYesterday() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNo_network_connection() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPlease_check_your_network() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getConnection_lost() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getReconnecting() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUnknown() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSources() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHas_transfered_the_chat_to() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHas_joined_the_chat() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHas_left_the_chat() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getService_unavailable() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRequest_timed_out() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSettings() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getQuick_menu() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSuccessfully_signed_in() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getQueue_position() {
        return null;
    }
    
    public NCWOtherLocalized() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component17() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component18() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component19() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component20() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component21() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component22() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component23() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component24() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component25() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component26() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component27() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component28() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component29() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component30() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component31() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component32() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component33() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component34() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component35() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component36() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component37() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component38() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component39() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component40() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component41() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component42() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component43() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component44() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component45() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component46() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component47() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component48() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component49() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component50() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component51() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component52() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component53() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component54() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component55() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component56() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component57() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component58() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component59() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component60() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component61() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component62() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component63() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component64() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component65() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component66() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component67() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component68() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component69() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component70() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component71() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component72() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component73() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component74() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component75() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component76() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component77() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.language.NCWOtherLocalized copy(@org.jetbrains.annotations.NotNull()
    java.lang.String powered_by_netomi, @org.jetbrains.annotations.NotNull()
    java.lang.String ask_a_question, @org.jetbrains.annotations.NotNull()
    java.lang.String ai_bot, @org.jetbrains.annotations.NotNull()
    java.lang.String sound, @org.jetbrains.annotations.NotNull()
    java.lang.String restart_chat, @org.jetbrains.annotations.NotNull()
    java.lang.String are_you_sure_you_want_to_restart, @org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    java.lang.String download_transcript, @org.jetbrains.annotations.NotNull()
    java.lang.String session_timeout, @org.jetbrains.annotations.NotNull()
    java.lang.String your_session_has_expired_due_to_inactivity, @org.jetbrains.annotations.NotNull()
    java.lang.String okay, @org.jetbrains.annotations.NotNull()
    java.lang.String logout, @org.jetbrains.annotations.NotNull()
    java.lang.String you_have_been_logged_out, @org.jetbrains.annotations.NotNull()
    java.lang.String choose_option, @org.jetbrains.annotations.NotNull()
    java.lang.String camera, @org.jetbrains.annotations.NotNull()
    java.lang.String upload_from_gallery, @org.jetbrains.annotations.NotNull()
    java.lang.String attach_from_device, @org.jetbrains.annotations.NotNull()
    java.lang.String cancel, @org.jetbrains.annotations.NotNull()
    java.lang.String limit_exceed, @org.jetbrains.annotations.NotNull()
    java.lang.String upload_files_maximum_size, @org.jetbrains.annotations.NotNull()
    java.lang.String unsupported_file_format, @org.jetbrains.annotations.NotNull()
    java.lang.String selected_files_type_is_not_supported, @org.jetbrains.annotations.NotNull()
    java.lang.String connecting, @org.jetbrains.annotations.NotNull()
    java.lang.String connected, @org.jetbrains.annotations.NotNull()
    java.lang.String disconnected, @org.jetbrains.annotations.NotNull()
    java.lang.String error, @org.jetbrains.annotations.NotNull()
    java.lang.String unable_to_open_url, @org.jetbrains.annotations.NotNull()
    java.lang.String unable_to_open_call, @org.jetbrains.annotations.NotNull()
    java.lang.String close_chat, @org.jetbrains.annotations.NotNull()
    java.lang.String return_later, @org.jetbrains.annotations.NotNull()
    java.lang.String chat_session_will_be_saved, @org.jetbrains.annotations.NotNull()
    java.lang.String end_chat, @org.jetbrains.annotations.NotNull()
    java.lang.String chat_will_end_you_wont_able_to_resume_later, @org.jetbrains.annotations.NotNull()
    java.lang.String confirm, @org.jetbrains.annotations.NotNull()
    java.lang.String skip, @org.jetbrains.annotations.NotNull()
    java.lang.String submit, @org.jetbrains.annotations.NotNull()
    java.lang.String close, @org.jetbrains.annotations.NotNull()
    java.lang.String not_sent_tap_to_retry, @org.jetbrains.annotations.NotNull()
    java.lang.String thank_you, @org.jetbrains.annotations.NotNull()
    java.lang.String for_completing_the_survey, @org.jetbrains.annotations.NotNull()
    java.lang.String view_response, @org.jetbrains.annotations.NotNull()
    java.lang.String click_to_upload, @org.jetbrains.annotations.NotNull()
    java.lang.String format, @org.jetbrains.annotations.NotNull()
    java.lang.String this_field_is_required, @org.jetbrains.annotations.NotNull()
    java.lang.String submitted, @org.jetbrains.annotations.NotNull()
    java.lang.String select, @org.jetbrains.annotations.NotNull()
    java.lang.String _item_selected, @org.jetbrains.annotations.NotNull()
    java.lang.String connection_error, @org.jetbrains.annotations.NotNull()
    java.lang.String retry, @org.jetbrains.annotations.NotNull()
    java.lang.String start_typing, @org.jetbrains.annotations.NotNull()
    java.lang.String yes, @org.jetbrains.annotations.NotNull()
    java.lang.String no, @org.jetbrains.annotations.NotNull()
    java.lang.String add_additional_feedback, @org.jetbrains.annotations.NotNull()
    java.lang.String add, @org.jetbrains.annotations.NotNull()
    java.lang.String transcripts, @org.jetbrains.annotations.NotNull()
    java.lang.String send_transcript, @org.jetbrains.annotations.NotNull()
    java.lang.String download, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String enter_email, @org.jetbrains.annotations.NotNull()
    java.lang.String please_provide_valid_email, @org.jetbrains.annotations.NotNull()
    java.lang.String today, @org.jetbrains.annotations.NotNull()
    java.lang.String yesterday, @org.jetbrains.annotations.NotNull()
    java.lang.String no_network_connection, @org.jetbrains.annotations.NotNull()
    java.lang.String please_check_your_network, @org.jetbrains.annotations.NotNull()
    java.lang.String connection_lost, @org.jetbrains.annotations.NotNull()
    java.lang.String reconnecting, @org.jetbrains.annotations.NotNull()
    java.lang.String unknown, @org.jetbrains.annotations.NotNull()
    java.lang.String sources, @org.jetbrains.annotations.NotNull()
    java.lang.String has_transfered_the_chat_to, @org.jetbrains.annotations.NotNull()
    java.lang.String has_joined_the_chat, @org.jetbrains.annotations.NotNull()
    java.lang.String has_left_the_chat, @org.jetbrains.annotations.NotNull()
    java.lang.String service_unavailable, @org.jetbrains.annotations.NotNull()
    java.lang.String request_timed_out, @org.jetbrains.annotations.NotNull()
    java.lang.String settings, @org.jetbrains.annotations.NotNull()
    java.lang.String quick_menu, @org.jetbrains.annotations.NotNull()
    java.lang.String successfully_signed_in, @org.jetbrains.annotations.NotNull()
    java.lang.String queue_position) {
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