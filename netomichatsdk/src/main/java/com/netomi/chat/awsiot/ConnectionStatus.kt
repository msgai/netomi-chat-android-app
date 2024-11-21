package com.netomi.chat.awsiot

enum class ConnectionStatus {
    CONNECTING,
    CONNECTED,
    CONNECTION_LOST,
    RE_CONNECTED,
    UNKNOWN
}