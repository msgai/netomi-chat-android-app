package com.netomi.chat.ui.view;

/**
 * Activity responsible for displaying the chat interface and handling user interactions.
 *
 * This activity is part of the Chat SDK and serves as the entry point for users to interact
 * with the chat. It uses **ViewModel** to manage UI-related data in a lifecycle-aware manner.
 *
 * ## Responsibilities:
 * Display chat messages and update the chat log in real-time.
 * Allow the user to send new messages through a UI form.
 * Observe the **`NCWChatViewModel`** for LiveData updates to keep the UI synchronized.
 *
 * ## Architecture:
 * **View Layer (Activity):** Displays the UI and handles user events.
 *
 * ## Usage:
 * This activity is intended to be launched by the host application or as part of the Chat SDK.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00c2\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J(\u0010\u0081\u0001\u001a\u00030\u0082\u00012\t\u0010\u0083\u0001\u001a\u0004\u0018\u00010.2\u0007\u0010\u0084\u0001\u001a\u00020\u00102\b\u0010\u0085\u0001\u001a\u00030\u0086\u0001H\u0002J\n\u0010\u0087\u0001\u001a\u00030\u0082\u0001H\u0002J(\u0010\u0088\u0001\u001a\u00030\u0082\u00012\t\u0010\u0083\u0001\u001a\u0004\u0018\u00010.2\u0007\u0010\u0089\u0001\u001a\u00020\u00102\b\u0010\u0085\u0001\u001a\u00030\u0086\u0001H\u0002J\u0019\u0010\u008a\u0001\u001a\u00030\u0082\u00012\r\u0010\u008b\u0001\u001a\b\u0012\u0004\u0012\u00020_0XH\u0002J\u0013\u0010\u008c\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u008d\u0001\u001a\u00020_H\u0002J&\u0010\u008e\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u008b\u0001\u001a\u00020_2\b\u0010\u008f\u0001\u001a\u00030\u0090\u00012\u0007\u0010\u0091\u0001\u001a\u00020\u0006H\u0002J\u0014\u0010\u0092\u0001\u001a\u00030\u0082\u00012\b\u0010w\u001a\u0004\u0018\u00010xH\u0002J\b\u0010\u0093\u0001\u001a\u00030\u0082\u0001J\n\u0010\u0094\u0001\u001a\u00030\u0082\u0001H\u0002J\u0016\u0010\u0095\u0001\u001a\u00030\u0082\u00012\n\u0010\u0096\u0001\u001a\u0005\u0018\u00010\u0097\u0001H\u0016J\n\u0010\u0098\u0001\u001a\u00030\u0082\u0001H\u0002J\n\u0010\u0099\u0001\u001a\u00030\u0082\u0001H\u0002J\u0014\u0010\u009a\u0001\u001a\u00020G2\t\u0010\u009b\u0001\u001a\u0004\u0018\u00010\u0006H\u0002J\n\u0010\u009c\u0001\u001a\u00030\u0082\u0001H\u0002J\t\u0010\u009d\u0001\u001a\u00020GH\u0002J\t\u0010\u009e\u0001\u001a\u00020GH\u0002J\n\u0010\u009f\u0001\u001a\u00030\u0082\u0001H\u0002J]\u0010\u00a0\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u00a1\u0001\u001a\u00020\u00062\n\u0010\u00a2\u0001\u001a\u0005\u0018\u00010\u00a3\u00012\u0007\u0010\u00a4\u0001\u001a\u00020\u00062\u0016\u0010\u00a5\u0001\u001a\u0011\u0012\u0005\u0012\u00030\u00a7\u0001\u0012\u0005\u0012\u00030\u0082\u00010\u00a6\u00012\u001b\u0010\u00a8\u0001\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0012\u0005\u0012\u00030\u0082\u00010\u00a9\u0001H\u0002J\u000b\u0010\u00aa\u0001\u001a\u0004\u0018\u00010.H\u0002JR\u0010\u00ab\u0001\u001a\u00030\u00ac\u00012\u0007\u0010\u00ad\u0001\u001a\u00020\u00062\u000b\b\u0002\u0010\u00ae\u0001\u001a\u0004\u0018\u00010\u00062\u000b\b\u0002\u0010\u00af\u0001\u001a\u0004\u0018\u00010?2\u001f\b\u0002\u0010\u00b0\u0001\u001a\u0018\u0012\u0005\u0012\u00030\u00b1\u0001\u0018\u00010&j\u000b\u0012\u0005\u0012\u00030\u00b1\u0001\u0018\u0001`(\u00a2\u0006\u0003\u0010\u00b2\u0001J\u001b\u0010\u00b3\u0001\u001a\u00020_2\u0007\u0010\u00b4\u0001\u001a\u00020\u00062\u0007\u0010\u00b5\u0001\u001a\u00020?H\u0002J\n\u0010\u00b6\u0001\u001a\u00030\u0082\u0001H\u0002J\u001e\u0010\u00b7\u0001\u001a\u00030\u0082\u00012\t\u0010\u0085\u0001\u001a\u0004\u0018\u00010\u00062\u0007\u0010\u00b8\u0001\u001a\u00020\u0006H\u0002J\n\u0010\u00b9\u0001\u001a\u00030\u0082\u0001H\u0002J\u001b\u0010\u00ba\u0001\u001a\u00030\u0082\u00012\u000f\u0010\u00bb\u0001\u001a\n\u0012\u0005\u0012\u00030\u00bd\u00010\u00bc\u0001H\u0002J+\u0010\u00be\u0001\u001a\u00030\u0082\u00012\t\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u00102\t\u0010\u00bf\u0001\u001a\u0004\u0018\u00010\u00062\t\b\u0002\u0010\u00c0\u0001\u001a\u00020GH\u0002J+\u0010\u00c1\u0001\u001a\u00030\u0082\u00012\t\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u00102\t\u0010\u00bf\u0001\u001a\u0004\u0018\u00010\u00062\t\b\u0002\u0010\u00c0\u0001\u001a\u00020GH\u0002J\u0013\u0010\u00c2\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u00b4\u0001\u001a\u00020_H\u0002J,\u0010\u00c3\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u00c4\u0001\u001a\u00020\u00062\u0007\u0010\u00c5\u0001\u001a\u00020\u00062\u0007\u0010\u00c6\u0001\u001a\u00020\u00062\u0007\u0010\u00a4\u0001\u001a\u00020\u0006J\u0014\u0010\u00c7\u0001\u001a\u00030\u0082\u00012\b\u0010\u00bb\u0001\u001a\u00030\u00c8\u0001H\u0002J\n\u0010\u00c9\u0001\u001a\u00030\u0082\u0001H\u0002J\u0011\u0010\u00ca\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u00cb\u0001\u001a\u00020\u0006J&\u0010\u00cc\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u00a1\u0001\u001a\u00020\u00062\u0007\u0010\u00cd\u0001\u001a\u00020\u00062\b\u0010\u00ce\u0001\u001a\u00030\u0090\u0001H\u0002J\n\u0010\u00cf\u0001\u001a\u00030\u0082\u0001H\u0002J\n\u0010\u00d0\u0001\u001a\u00030\u0082\u0001H\u0002J\n\u0010\u00d1\u0001\u001a\u00030\u0082\u0001H\u0002J\t\u0010\u00d2\u0001\u001a\u00020GH\u0002J\u0013\u0010\u00d3\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u00d4\u0001\u001a\u00020\u0006H\u0002J\n\u0010\u00d5\u0001\u001a\u00030\u0082\u0001H\u0002J=\u0010\u00d6\u0001\u001a\u0004\u0018\u00010_2\b\u0010\u00d7\u0001\u001a\u00030\u00d8\u00012\u0007\u0010\u00a1\u0001\u001a\u00020\u00062\u0007\u0010\u0085\u0001\u001a\u00020\u00062\b\u0010\u00d9\u0001\u001a\u00030\u0090\u00012\n\u0010\u00da\u0001\u001a\u0005\u0018\u00010\u00db\u0001H\u0002J\n\u0010\u00dc\u0001\u001a\u00030\u0082\u0001H\u0002J\u001d\u0010\u00dd\u0001\u001a\u00030\u0082\u00012\b\u0010\u00de\u0001\u001a\u00030\u00bd\u00012\u0007\u0010\u00df\u0001\u001a\u00020\u0006H\u0002J\u0016\u0010\u00e0\u0001\u001a\u00030\u0082\u00012\n\u0010\u00e1\u0001\u001a\u0005\u0018\u00010\u00e2\u0001H\u0014J\n\u0010\u00e3\u0001\u001a\u00030\u0082\u0001H\u0014J\u0013\u0010\u00e4\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u00b4\u0001\u001a\u00020_H\u0016J\u0014\u0010\u00e5\u0001\u001a\u00030\u0082\u00012\b\u0010\u00e6\u0001\u001a\u00030\u0097\u0001H\u0002J \u0010\u00e7\u0001\u001a\u00030\u0082\u00012\n\u0010\u00e6\u0001\u001a\u0005\u0018\u00010\u00e8\u00012\b\u0010\u00e9\u0001\u001a\u00030\u0090\u0001H\u0016J\u0016\u0010\u00ea\u0001\u001a\u00030\u0082\u00012\n\u0010\u00e6\u0001\u001a\u0005\u0018\u00010\u00e8\u0001H\u0002J\n\u0010\u00eb\u0001\u001a\u00030\u0082\u0001H\u0002J\n\u0010\u00ec\u0001\u001a\u00030\u0082\u0001H\u0014J\u0013\u0010\u00ed\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u00b4\u0001\u001a\u00020_H\u0016J\u0013\u0010\u00ee\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u00ef\u0001\u001a\u00020GH\u0016J\u0014\u0010\u00f0\u0001\u001a\u00030\u0082\u00012\b\u0010\u00f1\u0001\u001a\u00030\u00f2\u0001H\u0016J\'\u0010\u00f3\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u00a1\u0001\u001a\u00020\u00062\b\u0010\u00e9\u0001\u001a\u00030\u0090\u00012\b\u0010\u00ce\u0001\u001a\u00030\u0090\u0001H\u0016J\'\u0010\u00f4\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u00a1\u0001\u001a\u00020\u00062\b\u0010\u00e9\u0001\u001a\u00030\u0090\u00012\b\u0010\u00ce\u0001\u001a\u00030\u0090\u0001H\u0016J\n\u0010\u00f5\u0001\u001a\u00030\u0082\u0001H\u0016J\n\u0010\u00f6\u0001\u001a\u00030\u0082\u0001H\u0002J\n\u0010\u00f7\u0001\u001a\u00030\u0082\u0001H\u0002J\u0013\u0010\u00f8\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u00f9\u0001\u001a\u000201H\u0002J\n\u0010\u00fa\u0001\u001a\u00030\u0082\u0001H\u0002J\n\u0010\u00fb\u0001\u001a\u00030\u0082\u0001H\u0002J%\u0010\u00fc\u0001\u001a\u00030\u0082\u00012\u0019\u0010\u00fd\u0001\u001a\u0014\u0012\u0005\u0012\u00030\u00c8\u00010&j\t\u0012\u0005\u0012\u00030\u00c8\u0001`(H\u0002J\n\u0010\u00fe\u0001\u001a\u00030\u0082\u0001H\u0002J\n\u0010\u00ff\u0001\u001a\u00030\u0082\u0001H\u0002J\u0014\u0010\u0080\u0002\u001a\u00030\u0082\u00012\b\u0010\u0081\u0002\u001a\u00030\u0082\u0002H\u0002J\n\u0010\u0083\u0002\u001a\u00030\u0082\u0001H\u0002J\n\u0010\u0084\u0002\u001a\u00030\u0082\u0001H\u0002J\n\u0010\u0085\u0002\u001a\u00030\u0082\u0001H\u0002J\u0014\u0010\u0086\u0002\u001a\u00030\u0082\u00012\b\u0010\u00bb\u0001\u001a\u00030\u00c8\u0001H\u0002J\u001f\u0010\u0087\u0002\u001a\u00030\u0082\u00012\n\u0010\u0081\u0002\u001a\u0005\u0018\u00010\u0082\u00022\u0007\u0010\u00b5\u0001\u001a\u00020?H\u0002J\u0016\u0010\u0088\u0002\u001a\u00030\u0082\u00012\n\u0010\u00bb\u0001\u001a\u0005\u0018\u00010\u00c8\u0001H\u0002J\u0014\u0010\u0089\u0002\u001a\u00030\u0082\u00012\b\u0010\u00bb\u0001\u001a\u00030\u00c8\u0001H\u0002J\u0016\u0010\u008a\u0002\u001a\u00030\u0082\u00012\n\u0010\u00bb\u0001\u001a\u0005\u0018\u00010\u00c8\u0001H\u0002J\n\u0010\u008b\u0002\u001a\u00030\u0082\u0001H\u0002J\u0013\u0010\u008c\u0002\u001a\u00030\u0082\u00012\u0007\u0010\u00b4\u0001\u001a\u00020_H\u0002J\u0019\u0010\u008d\u0002\u001a\u00030\u0082\u00012\r\u0010\u008b\u0001\u001a\b\u0012\u0004\u0012\u00020_0XH\u0002J\u0014\u0010\u008e\u0002\u001a\u00030\u0082\u00012\b\u0010\u008f\u0002\u001a\u00030\u0090\u0002H\u0002J\u0013\u0010\u0091\u0002\u001a\u00030\u0082\u00012\u0007\u0010\u00ad\u0001\u001a\u00020\u0006H\u0002J\u0012\u0010\u0092\u0002\u001a\u00030\u0082\u00012\b\u0010\u0093\u0002\u001a\u00030\u00ac\u0001J\n\u0010\u0094\u0002\u001a\u00030\u0082\u0001H\u0002J\u001e\u0010\u0095\u0002\u001a\u00030\u0082\u00012\t\u0010\u00a4\u0001\u001a\u0004\u0018\u00010\u00062\u0007\u0010\u0096\u0002\u001a\u00020\u0006H\u0002J\n\u0010\u0097\u0002\u001a\u00030\u0082\u0001H\u0002J\u0013\u0010\u0098\u0002\u001a\u00030\u0082\u00012\u0007\u0010\u0099\u0002\u001a\u00020GH\u0002J\n\u0010\u009a\u0002\u001a\u00030\u0082\u0001H\u0002J\b\u0010\u009b\u0002\u001a\u00030\u0082\u0001J\n\u0010\u009c\u0002\u001a\u00030\u0082\u0001H\u0002J\n\u0010\u009d\u0002\u001a\u00030\u0082\u0001H\u0002J\n\u0010\u009e\u0002\u001a\u00030\u0082\u0001H\u0002J\n\u0010\u009f\u0002\u001a\u00030\u0082\u0001H\u0002J\t\u0010\u00a0\u0002\u001a\u00020GH\u0002J\n\u0010\u00a1\u0002\u001a\u00030\u0082\u0001H\u0002J\u0013\u0010\u00a2\u0002\u001a\u00030\u0082\u00012\u0007\u0010\u00a3\u0002\u001a\u00020\u0006H\u0002J\b\u0010\u00a4\u0002\u001a\u00030\u0082\u0001J\n\u0010\u00a5\u0002\u001a\u00030\u0082\u0001H\u0016J\n\u0010\u00a6\u0002\u001a\u00030\u0082\u0001H\u0002J\u0014\u0010\u00a7\u0002\u001a\u00030\u0082\u00012\b\u0010\u00a8\u0002\u001a\u00030\u00a9\u0002H\u0002J\u0013\u0010\u00aa\u0002\u001a\u00030\u0082\u00012\u0007\u0010\u00ab\u0002\u001a\u00020_H\u0002J\n\u0010\u00ac\u0002\u001a\u00030\u0082\u0001H\u0002J\n\u0010\u00ad\u0002\u001a\u00030\u0082\u0001H\u0002JO\u0010\u00ae\u0002\u001a\u00030\u0082\u00012\u0007\u0010\u00af\u0002\u001a\u00020\u000624\u0010\u00b0\u0002\u001a\u001b\u0012\u0016\b\u0001\u0012\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0007\u0012\u0005\u0018\u00010\u00bd\u00010\u00b2\u00020\u00b1\u0002\"\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0007\u0012\u0005\u0018\u00010\u00bd\u00010\u00b2\u0002H\u0002\u00a2\u0006\u0003\u0010\u00b3\u0002J\n\u0010\u00b4\u0002\u001a\u00030\u0082\u0001H\u0002J\u0014\u0010\u00b5\u0002\u001a\u00030\u0082\u00012\b\u0010\u00b6\u0002\u001a\u00030\u00b7\u0002H\u0002J\n\u0010\u00b8\u0002\u001a\u00030\u0082\u0001H\u0002J\u0013\u0010\u00b9\u0002\u001a\u00030\u0082\u00012\u0007\u0010\u008d\u0001\u001a\u00020_H\u0002J*\u0010\u00b9\u0002\u001a\u00030\u0082\u00012\r\u0010\u008b\u0001\u001a\b\u0012\u0004\u0012\u00020_0X2\b\u0010\u00bb\u0001\u001a\u00030\u00c8\u0001H\u0082@\u00a2\u0006\u0003\u0010\u00ba\u0002J&\u0010\u00bb\u0002\u001a\u00030\u0082\u00012\u0007\u0010\u00bc\u0002\u001a\u00020_2\b\u0010\u008f\u0001\u001a\u00030\u0090\u00012\u0007\u0010\u0091\u0001\u001a\u00020\u0006H\u0002J\u0014\u0010\u00bd\u0002\u001a\u00020G2\t\u0010\u0083\u0001\u001a\u0004\u0018\u00010.H\u0002J\u0014\u0010\u00be\u0002\u001a\u00020G2\t\u0010\u0083\u0001\u001a\u0004\u0018\u00010.H\u0002J\u0017\u0010\u00be\u0002\u001a\u00020G2\f\u0010U\u001a\b\u0012\u0004\u0012\u00020V0XH\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0017\u001a\u00020\u00188FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001d\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082.\u00a2\u0006\u0002\n\u0000R\"\u0010%\u001a\u0016\u0012\u0004\u0012\u00020\'\u0018\u00010&j\n\u0012\u0004\u0012\u00020\'\u0018\u0001`(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020+0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u0004\u0018\u00010.X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u00100\u001a\u0004\u0018\u000101X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u00102\u001a\b\u0012\u0004\u0012\u00020+0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u00103\u001a\b\u0012\u0004\u0012\u00020+0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u00104\u001a\u000205X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u000e\u0010:\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u001fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020=X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020?X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010@\u001a\u00020AX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u000e\u0010F\u001a\u00020GX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020GX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020GX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020GX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020GX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020GX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020GX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020GX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020GX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020RX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020?X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010U\u001a\u0012\u0012\u0004\u0012\u00020V0&j\b\u0012\u0004\u0012\u00020V`(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010W\u001a\n\u0012\u0004\u0012\u00020Y\u0018\u00010XX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010Z\u001a\u00020[X\u0082.\u00a2\u0006\u0002\n\u0000R \u0010\\\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020_0^0]X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010`\u001a\u00020aX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010b\u001a\b\u0012\u0004\u0012\u00020_0^X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010c\u001a\u0004\u0018\u00010dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010e\u001a\u00020f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\bi\u0010\u001c\u001a\u0004\bg\u0010hR\u0010\u0010j\u001a\u0004\u0018\u00010kX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010l\u001a\u00020GX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010m\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010n\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010o\u001a\u00020pX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010q\u001a\u00020rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010s\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010t\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010u\u001a\u0004\u0018\u00010vX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010w\u001a\u0004\u0018\u00010xX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\by\u0010z\"\u0004\b{\u0010|R\u000e\u0010}\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010~\u001a\u00020\u001fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u007f\u001a\u00020\u001fX\u0082.\u00a2\u0006\u0002\n\u0000R\u0015\u0010\u0080\u0001\u001a\b\u0012\u0004\u0012\u00020+0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u00bf\u0002"}, d2 = {"Lcom/netomi/chat/ui/view/NCWChatActivity;", "Lcom/netomi/chat/ui/view/NCWBaseActivity;", "Lcom/netomi/chat/utils/NCWChatActionCallback;", "Lcom/netomi/chat/utils/NCWFeedbackActionCallback;", "()V", "agentAvatar", "", "agentName", "attachmentIcon", "Landroid/widget/ImageView;", "attachmentType", "awsMessageObserver", "Landroidx/lifecycle/Observer;", "botRefId", "cameraLauncherMain", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/net/Uri;", "cardToday", "Landroidx/constraintlayout/widget/ConstraintLayout;", "cardViewInputBox", "Landroidx/cardview/widget/CardView;", "chatRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "chatViewModel", "Lcom/netomi/chat/ui/viewmodel/NCWChatViewModel;", "getChatViewModel", "()Lcom/netomi/chat/ui/viewmodel/NCWChatViewModel;", "chatViewModel$delegate", "Lkotlin/Lazy;", "closeIcon", "connectionHeader", "Landroid/widget/TextView;", "connectionStatus", "constProgressBar", "conversationID", "customTabsIntent", "Landroidx/browser/customtabs/CustomTabsIntent;", "deviceInfo", "Ljava/util/ArrayList;", "Lcom/netomi/chat/model/messages/NCWCustomAttribute;", "Lkotlin/collections/ArrayList;", "externalId", "fileLauncher", "Landroid/content/Intent;", "fileMultipleLauncher", "fileSend", "Ljava/io/File;", "footerContainer", "formComponent", "Lcom/netomi/chat/model/messages/Component;", "galleryLauncher", "galleryMultipleLauncher", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "setHandler", "(Landroid/os/Handler;)V", "headerContainer", "headerTextView", "idleRunnable", "Ljava/lang/Runnable;", "idleTimeInMillis", "", "idleTimeoutManager", "LNCWIdleTimeoutManager;", "getIdleTimeoutManager", "()LNCWIdleTimeoutManager;", "setIdleTimeoutManager", "(LNCWIdleTimeoutManager;)V", "isDisableInput", "", "isEventSessionExpire", "isHistoryChatAvialbale", "isHistoryDisableInput", "isIdle", "isLoaderActive", "isMultipleFile", "isStreamingText", "isSurveyRule", "ivMenu", "ivMenuOption", "Landroidx/appcompat/widget/AppCompatImageView;", "loaderAddedTime", "logoIcon", "mMultipleFile", "Lcom/netomi/chat/model/media_payload/MultiFileModel;", "mSurveyRule", "", "Lcom/netomi/chat/model/survey_rule/SurveyRule;", "messageAdapter", "Lcom/netomi/chat/ui/view/NCWChatAdapter;", "messageChunksMap", "", "", "Lcom/netomi/chat/model/NCWMessage;", "messageInputField", "Landroid/widget/EditText;", "messageList", "messageSoundPlayer", "Lcom/netomi/chat/utils/MessageSoundPlayer;", "ncwAwsCredentialsViewModel", "Lcom/netomi/chat/ui/viewmodel/NCWAwsCredentialsViewModel;", "getNcwAwsCredentialsViewModel", "()Lcom/netomi/chat/ui/viewmodel/NCWAwsCredentialsViewModel;", "ncwAwsCredentialsViewModel$delegate", "ncwSdkConfig", "Lcom/netomi/chat/model/theme/light_theme/NCWHeaderConfig;", "onRestart", "ownerType", "photoUri", "progressBar", "Landroid/widget/ProgressBar;", "rootView", "Landroid/view/View;", "sendMessageIcon", "sendTranscriptMail", "streamingJob", "Lkotlinx/coroutines/Job;", "themeData", "Lcom/netomi/chat/model/theme/NCWThemeResponse;", "getThemeData", "()Lcom/netomi/chat/model/theme/NCWThemeResponse;", "setThemeData", "(Lcom/netomi/chat/model/theme/NCWThemeResponse;)V", "topic", "tvBrandName", "tvToday", "videoLauncher", "addDocMessage", "", "file", "selectedMediaUri", "type", "Lcom/netomi/chat/model/MessageType;", "addLoader", "addMediaMessage", "uri", "addMessages", "newMessages", "addSingleMessage", "newMessage", "addStreamMessages", "chunkIndex", "", "chunkStatus", "applyTheme", "backClicked", "callBackToBot", "carouselButtonAction", "it", "Lcom/netomi/chat/model/messages/NCWCarouselButton;", "checkAndReconnect", "checkForInitialMessage", "checkForLogoutAction", "content", "checkForPreviousQuickReply", "checkLoaderRunning", "checkProactiveMessageAvailable", "checkSupportedFilesForm", "createAndShowSurveyBottomSheet", "requestId", "surveyField", "Lcom/netomi/chat/model/messages/SurveyField;", "from", "onSubmit", "Lkotlin/Function1;", "Lcom/netomi/chat/survey/SubmitSurveyRequest;", "onSkipSurvey", "Lkotlin/Function2;", "createImageFile", "createPayload", "Lcom/netomi/chat/model/messages/NCWWebhookPayload;", "messageContent", "label", "timeStamp", "attachmentList", "Lcom/netomi/chat/model/messages/NCWAttachmentList;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/util/ArrayList;)Lcom/netomi/chat/model/messages/NCWWebhookPayload;", "createPillsMessage", "message", "timestamp", "getChatHistory", "getPreSignedUrl", "uploadKeyPrefix", "getTranscriptUrl", "handleApiCallback", "response", "Lcom/netomi/chat/utils/NCWState;", "", "handleFileSelection", "mimeType", "isGallery", "handleFormFileSelection", "handleMediaMessage", "handleSessionTimeout", "title", "subtitle", "submitText", "handleSoundStreaming", "Lcom/netomi/chat/model/messages/NCWGenericChannelResponse;", "hideProgressBar", "hitEndChatAPI", "subType", "hitFeedbackAPI", "feedbackValue", "attachmentIndex", "hitIdealTimeOutEvent", "hitLogoutAPI", "initViews", "isRecyclerViewFull", "launchCustomChromeTab", "url", "loadInitialMessages", "mapAttachmentToMessage", "attachment", "Lcom/netomi/chat/model/messages/NCWAttachment;", "index", "customPayload", "Lcom/netomi/chat/model/messages/NCWCustomPayload;", "observeChatMessages", "onApiSuccess", "apiResponse", "apiConstant", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onMediaClick", "onPostBackClicked", "option", "onQuickReply", "Lcom/netomi/chat/model/messages/NCWQuickReplyOption;", "position", "onQuickReplyClicked", "onRestartAction", "onResume", "onRetryClicked", "onScrollToPosition", "isScrollToPosition", "onSourceClicked", "multipleSourceDetail", "Lcom/netomi/chat/model/messages/MultipleSourceDetail;", "onThumbDownClick", "onThumbUpClick", "onUserInteraction", "openCamera", "openFile", "openFormPicker", "component", "openGallery", "openVideoCamera", "parseHistoryItems", "responses", "playBotSound", "playUserSound", "refreshChat", "eventData", "Lcom/netomi/chat/survey/EventData;", "removeLoader", "removeStreamLoader", "removeStreamLoaderMissed", "renderEventAndCustomFields", "renderPillsMessage", "renderTheFormMessage", "renderTheNormalMessage", "renderTheSurveyMessage", "resetIdleTimer", "retryAttachmentMessage", "safelyRemoveLoader", "saveAwsCredentials", "credentials", "Lcom/netomi/chat/model/mqtt/NCWCredentials;", "sendMessage", "sendMessageToBot", "payload", "sendProactiveMessage", "sendTranscriptApI", "email", "setIdealSurveyAgain", "setUIState", "enabled", "setUIStrings", "setUpLanguageOption", "setUpQuickReplyOption", "setUpSettingOption", "setupKeyboardListener", "setupMessageList", "shouldAutoScroll", "showCameraVideoOption", "showLimitExceedPopup", "messageIssue", "showMedia", "showMediaOptions", "showProgressBar", "showRestartPopUp", "ncwShowWarning", "Lcom/netomi/chat/model/theme/NCWShowWarning;", "showSubmittedSurvey", "ncwMessage", "smoothScrollToBottom", "stopIdleSurvey", "trackEvent", "eventName", "pairs", "", "Lkotlin/Pair;", "(Ljava/lang/String;[Lkotlin/Pair;)V", "updateFormSchema", "updateLanguageStrings", "languageResponse", "Lcom/netomi/chat/model/language/LanguageResponse;", "updateLoaderTime", "updateMessageList", "(Ljava/util/List;Lcom/netomi/chat/model/messages/NCWGenericChannelResponse;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateStreamMessage", "streamMessage", "validateFile", "validateFormAttachment", "netomichatsdk_debug"})
public final class NCWChatActivity extends com.netomi.chat.ui.view.NCWBaseActivity implements com.netomi.chat.utils.NCWChatActionCallback, com.netomi.chat.utils.NCWFeedbackActionCallback {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy chatViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy ncwAwsCredentialsViewModel$delegate = null;
    private android.widget.EditText messageInputField;
    private android.widget.ImageView sendMessageIcon;
    private android.widget.ImageView logoIcon;
    private android.widget.TextView headerTextView;
    private android.widget.ImageView closeIcon;
    private com.netomi.chat.ui.view.NCWChatAdapter messageAdapter;
    private java.util.List<com.netomi.chat.model.NCWMessage> messageList;
    private androidx.recyclerview.widget.RecyclerView chatRecyclerView;
    private android.view.View rootView;
    private android.widget.ImageView attachmentIcon;
    private androidx.constraintlayout.widget.ConstraintLayout headerContainer;
    private androidx.constraintlayout.widget.ConstraintLayout footerContainer;
    private androidx.constraintlayout.widget.ConstraintLayout constProgressBar;
    private androidx.appcompat.widget.AppCompatImageView ivMenuOption;
    private android.widget.ImageView ivMenu;
    private android.widget.TextView connectionHeader;
    private android.widget.ProgressBar progressBar;
    private androidx.constraintlayout.widget.ConstraintLayout cardToday;
    private android.widget.TextView tvToday;
    @org.jetbrains.annotations.Nullable()
    private android.net.Uri photoUri;
    @org.jetbrains.annotations.Nullable()
    private com.netomi.chat.model.theme.light_theme.NCWHeaderConfig ncwSdkConfig;
    @org.jetbrains.annotations.Nullable()
    private com.netomi.chat.model.theme.NCWThemeResponse themeData;
    private androidx.cardview.widget.CardView cardViewInputBox;
    private androidx.browser.customtabs.CustomTabsIntent customTabsIntent;
    private java.lang.String topic;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String conversationID;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String externalId;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String botRefId;
    public NCWIdleTimeoutManager idleTimeoutManager;
    @org.jetbrains.annotations.Nullable()
    private java.io.File fileSend;
    private long loaderAddedTime = 0L;
    private boolean isLoaderActive = false;
    private android.widget.TextView tvBrandName;
    @org.jetbrains.annotations.Nullable()
    private com.netomi.chat.model.messages.Component formComponent;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String attachmentType = "attachment";
    @org.jetbrains.annotations.Nullable()
    private java.util.ArrayList<com.netomi.chat.model.messages.NCWCustomAttribute> deviceInfo;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String connectionStatus = "";
    @org.jetbrains.annotations.Nullable()
    private java.lang.String agentName = "";
    @org.jetbrains.annotations.Nullable()
    private java.lang.String agentAvatar;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String ownerType = "BOT";
    private boolean isHistoryDisableInput = true;
    private boolean isDisableInput = true;
    @org.jetbrains.annotations.Nullable()
    private java.util.List<com.netomi.chat.model.survey_rule.SurveyRule> mSurveyRule;
    private boolean isSurveyRule = false;
    private long idleTimeInMillis = 0L;
    @org.jetbrains.annotations.NotNull()
    private android.os.Handler handler;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.util.List<com.netomi.chat.model.NCWMessage>> messageChunksMap = null;
    @org.jetbrains.annotations.Nullable()
    private com.netomi.chat.utils.MessageSoundPlayer messageSoundPlayer;
    private boolean onRestart = false;
    private boolean isIdle = false;
    private boolean isHistoryChatAvialbale = false;
    private boolean isMultipleFile = false;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.netomi.chat.model.media_payload.MultiFileModel> mMultipleFile;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String sendTranscriptMail = "";
    private boolean isStreamingText = false;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job streamingJob;
    private boolean isEventSessionExpire = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Runnable idleRunnable = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.Observer<java.lang.String> awsMessageObserver = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> videoLauncher = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.net.Uri> cameraLauncherMain = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> galleryMultipleLauncher = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> fileMultipleLauncher = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> galleryLauncher = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> fileLauncher = null;
    
    public NCWChatActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.ui.viewmodel.NCWChatViewModel getChatViewModel() {
        return null;
    }
    
    private final com.netomi.chat.ui.viewmodel.NCWAwsCredentialsViewModel getNcwAwsCredentialsViewModel() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.netomi.chat.model.theme.NCWThemeResponse getThemeData() {
        return null;
    }
    
    public final void setThemeData(@org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.theme.NCWThemeResponse p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final NCWIdleTimeoutManager getIdleTimeoutManager() {
        return null;
    }
    
    public final void setIdleTimeoutManager(@org.jetbrains.annotations.NotNull()
    NCWIdleTimeoutManager p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.os.Handler getHandler() {
        return null;
    }
    
    public final void setHandler(@org.jetbrains.annotations.NotNull()
    android.os.Handler p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupKeyboardListener() {
    }
    
    private final void setUpQuickReplyOption() {
    }
    
    public final void setUpLanguageOption() {
    }
    
    private final void setUpSettingOption() {
    }
    
    private final void setIdealSurveyAgain() {
    }
    
    private final void showRestartPopUp(com.netomi.chat.model.theme.NCWShowWarning ncwShowWarning) {
    }
    
    private final void onRestartAction() {
    }
    
    private final void hitIdealTimeOutEvent() {
    }
    
    private final void resetIdleTimer() {
    }
    
    private final void stopIdleSurvey() {
    }
    
    public final void showMedia() {
    }
    
    public final void backClicked() {
    }
    
    private final void callBackToBot() {
    }
    
    private final void hitLogoutAPI() {
    }
    
    public final void hitEndChatAPI(@org.jetbrains.annotations.NotNull()
    java.lang.String subType) {
    }
    
    private final void hitFeedbackAPI(java.lang.String requestId, java.lang.String feedbackValue, int attachmentIndex) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    private final void checkAndReconnect() {
    }
    
    @java.lang.Override()
    public void onUserInteraction() {
    }
    
    /**
     * Handles the session timeout logic.
     */
    public final void handleSessionTimeout(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull()
    java.lang.String submitText, @org.jetbrains.annotations.NotNull()
    java.lang.String from) {
    }
    
    private final void getPreSignedUrl(java.lang.String type, java.lang.String uploadKeyPrefix) {
    }
    
    /**
     * Sends a user message in the chat.
     * Triggered when the user presses the send icon. Retrieves the current input
     * from `messageInputField`, clears the input field, and posts the message.
     */
    private final void sendMessage(java.lang.String messageContent) {
    }
    
    public final void sendMessageToBot(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.messages.NCWWebhookPayload payload) {
    }
    
    private final boolean checkForLogoutAction(java.lang.String content) {
        return false;
    }
    
    private final void checkForInitialMessage() {
    }
    
    /**
     * Checks the last message in the list to determine if it has visible quick replies.
     * If quick replies are visible, it updates the visibility flag to `false`.
     */
    private final void checkForPreviousQuickReply() {
    }
    
    /**
     * Creates a WebhookPayload for sending messages.
     *
     * @param messageContent The content of the message to be sent.
     * @param label Optional label for the message, default is null.
     * @return The constructed WebhookPayload.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.netomi.chat.model.messages.NCWWebhookPayload createPayload(@org.jetbrains.annotations.NotNull()
    java.lang.String messageContent, @org.jetbrains.annotations.Nullable()
    java.lang.String label, @org.jetbrains.annotations.Nullable()
    java.lang.Long timeStamp, @org.jetbrains.annotations.Nullable()
    java.util.ArrayList<com.netomi.chat.model.messages.NCWAttachmentList> attachmentList) {
        return null;
    }
    
    /**
     * This function adds a predefined message to the chat, such as the bot’s
     * initial greeting or information, allowing the user to see context when
     * they first open the chat.
     */
    private final void loadInitialMessages() {
    }
    
    /**
     * Applies theme styling to the chat UI based on the provided theme data.
     * @param themeData The theme settings to be applied, containing gradient
     * configuration, colors, and title.
     */
    private final void applyTheme(com.netomi.chat.model.theme.NCWThemeResponse themeData) {
    }
    
    /**
     * Initializes and binds UI components in the chat activity layout.
     */
    private final void initViews() {
    }
    
    /**
     * Sets up the message list in the chat UI by initializing the adapter and layout manager.
     */
    private final void setupMessageList() {
    }
    
    private final void showSubmittedSurvey(com.netomi.chat.model.NCWMessage ncwMessage) {
    }
    
    @java.lang.Override()
    public void onQuickReply(@org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWQuickReplyOption option, int position) {
    }
    
    @java.lang.Override()
    public void onMediaClick(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.NCWMessage message) {
    }
    
    @java.lang.Override()
    public void onRetryClicked(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.NCWMessage message) {
    }
    
    @java.lang.Override()
    public void onSourceClicked(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.model.messages.MultipleSourceDetail multipleSourceDetail) {
    }
    
    @java.lang.Override()
    public void onScrollToPosition(boolean isScrollToPosition) {
    }
    
    private final void retryAttachmentMessage(com.netomi.chat.model.NCWMessage message) {
    }
    
    private final void handleMediaMessage(com.netomi.chat.model.NCWMessage message) {
    }
    
    private final void launchCustomChromeTab(java.lang.String url) {
    }
    
    @java.lang.Override()
    public void carouselButtonAction(@org.jetbrains.annotations.Nullable()
    com.netomi.chat.model.messages.NCWCarouselButton it) {
    }
    
    private final void onPostBackClicked(com.netomi.chat.model.messages.NCWCarouselButton option) {
    }
    
    /**
     * Handles quick reply option click by sending a message based on the option selected.
     *
     * @param option The selected quick reply option.
     */
    private final void onQuickReplyClicked(com.netomi.chat.model.messages.NCWQuickReplyOption option) {
    }
    
    /**
     * Observes LiveData from the ViewModel for various chat-related events.
     * This function handles different chat message states, including new messages,
     * conversation ID, AWS credentials, and other chat-related updates.
     *
     * It ensures that the UI is updated accordingly whenever a change occurs
     * in the chat data or configuration.
     */
    private final void observeChatMessages() {
    }
    
    private final void sendProactiveMessage() {
    }
    
    private final boolean checkProactiveMessageAvailable() {
        return false;
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    private final void refreshChat(com.netomi.chat.survey.EventData eventData) {
    }
    
    private final void renderPillsMessage(com.netomi.chat.survey.EventData eventData, long timestamp) {
    }
    
    private final com.netomi.chat.model.NCWMessage createPillsMessage(java.lang.String message, long timestamp) {
        return null;
    }
    
    private final void renderTheSurveyMessage(com.netomi.chat.model.messages.NCWGenericChannelResponse response) {
    }
    
    private final void createAndShowSurveyBottomSheet(java.lang.String requestId, com.netomi.chat.model.messages.SurveyField surveyField, java.lang.String from, kotlin.jvm.functions.Function1<? super com.netomi.chat.survey.SubmitSurveyRequest, kotlin.Unit> onSubmit, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSkipSurvey) {
    }
    
    private final void renderTheFormMessage(com.netomi.chat.model.messages.NCWGenericChannelResponse response) {
    }
    
    private final void renderTheNormalMessage(com.netomi.chat.model.messages.NCWGenericChannelResponse response) {
    }
    
    private final void handleSoundStreaming(com.netomi.chat.model.messages.NCWGenericChannelResponse response) {
    }
    
    private final void renderEventAndCustomFields(com.netomi.chat.model.messages.NCWGenericChannelResponse response) {
    }
    
    private final void setUIState(boolean enabled) {
    }
    
    private final com.netomi.chat.model.NCWMessage mapAttachmentToMessage(com.netomi.chat.model.messages.NCWAttachment attachment, java.lang.String requestId, java.lang.String type, int index, com.netomi.chat.model.messages.NCWCustomPayload customPayload) {
        return null;
    }
    
    private final void updateMessageList(com.netomi.chat.model.NCWMessage newMessage) {
    }
    
    private final void addSingleMessage(com.netomi.chat.model.NCWMessage newMessage) {
    }
    
    private final void updateStreamMessage(com.netomi.chat.model.NCWMessage streamMessage, int chunkIndex, java.lang.String chunkStatus) {
    }
    
    private final java.lang.Object updateMessageList(java.util.List<com.netomi.chat.model.NCWMessage> newMessages, com.netomi.chat.model.messages.NCWGenericChannelResponse response, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void smoothScrollToBottom() {
    }
    
    private final boolean shouldAutoScroll() {
        return false;
    }
    
    private final void addMessages(java.util.List<com.netomi.chat.model.NCWMessage> newMessages) {
    }
    
    private final void addStreamMessages(com.netomi.chat.model.NCWMessage newMessages, int chunkIndex, java.lang.String chunkStatus) {
    }
    
    private final boolean isRecyclerViewFull() {
        return false;
    }
    
    private final void safelyRemoveLoader(java.util.List<com.netomi.chat.model.NCWMessage> newMessages) {
    }
    
    private final void removeLoader() {
    }
    
    private final void addLoader() {
    }
    
    private final boolean checkLoaderRunning() {
        return false;
    }
    
    private final void updateLoaderTime() {
    }
    
    private final void removeStreamLoader() {
    }
    
    private final void removeStreamLoaderMissed() {
    }
    
    private final void handleApiCallback(com.netomi.chat.utils.NCWState<java.lang.Object> response) {
    }
    
    @java.lang.Override()
    public void showMediaOptions() {
    }
    
    private final void showCameraVideoOption() {
    }
    
    private final void openVideoCamera() {
    }
    
    private final void openCamera() {
    }
    
    private final void showLimitExceedPopup(java.lang.String messageIssue) {
    }
    
    private final java.io.File createImageFile() {
        return null;
    }
    
    private final boolean validateFormAttachment(java.io.File file) {
        return false;
    }
    
    private final boolean validateFile(java.io.File file) {
        return false;
    }
    
    private final void handleFileSelection(android.net.Uri selectedMediaUri, java.lang.String mimeType, boolean isGallery) {
    }
    
    private final void openGallery() {
    }
    
    private final void checkSupportedFilesForm() {
    }
    
    private final boolean validateFormAttachment(java.util.List<com.netomi.chat.model.media_payload.MultiFileModel> mMultipleFile) {
        return false;
    }
    
    private final void updateFormSchema() {
    }
    
    private final void openFile() {
    }
    
    private final void openFormPicker(com.netomi.chat.model.messages.Component component) {
    }
    
    private final void handleFormFileSelection(android.net.Uri selectedMediaUri, java.lang.String mimeType, boolean isGallery) {
    }
    
    private final void addDocMessage(java.io.File file, android.net.Uri selectedMediaUri, com.netomi.chat.model.MessageType type) {
    }
    
    private final void addMediaMessage(java.io.File file, android.net.Uri uri, com.netomi.chat.model.MessageType type) {
    }
    
    private final void onApiSuccess(java.lang.Object apiResponse, java.lang.String apiConstant) {
    }
    
    private final void parseHistoryItems(java.util.ArrayList<com.netomi.chat.model.messages.NCWGenericChannelResponse> responses) {
    }
    
    private final void getChatHistory() {
    }
    
    /**
     * Save Aws Credentials in data store
     */
    private final void saveAwsCredentials(com.netomi.chat.model.mqtt.NCWCredentials credentials) {
    }
    
    private final void showProgressBar() {
    }
    
    private final void hideProgressBar() {
    }
    
    @java.lang.Override()
    public void onThumbUpClick(@org.jetbrains.annotations.NotNull()
    java.lang.String requestId, int position, int attachmentIndex) {
    }
    
    @java.lang.Override()
    public void onThumbDownClick(@org.jetbrains.annotations.NotNull()
    java.lang.String requestId, int position, int attachmentIndex) {
    }
    
    private final void playUserSound() {
    }
    
    private final void playBotSound() {
    }
    
    private final void sendTranscriptApI(java.lang.String from, java.lang.String email) {
    }
    
    private final void updateLanguageStrings(com.netomi.chat.model.language.LanguageResponse languageResponse) {
    }
    
    private final void setUIStrings() {
    }
    
    private final void getTranscriptUrl() {
    }
    
    /**
     * Tracks an event with optional properties.
     * @param eventName Name of the event to track.
     * @param pairs Optional key-value pairs for additional event properties.
     */
    private final void trackEvent(java.lang.String eventName, kotlin.Pair<java.lang.String, ? extends java.lang.Object>... pairs) {
    }
}