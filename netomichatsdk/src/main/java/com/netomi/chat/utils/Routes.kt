package com.netomi.chat.utils

object Routes {
    /* API END POINT*/
    private const val VERSION_V1 = "v1/"
    private const val CONTENTS = "contents/"
    private const val API = "api/"

    const val ROUTE_APP_CONFIG = CONTENTS + VERSION_V1 + "app-config"
    const val ROUTE_GET_CHAT = CONTENTS + VERSION_V1 + "app_chat"
    const val ROUTE_SEND_CHAT = CONTENTS + VERSION_V1 + "send_chat"

    const val ROUTE_GET_CONVERSATION_ID = API + "conversationId"
}