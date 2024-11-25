package com.netomi.chat.utils

object Routes {
    /* API END POINT*/
    private const val VERSION_V1 = "v1/"
    private const val CONTENTS = "contents/"
    private const val API = "api/"

    const val ROUTE_GET_CHAT =API + "webhook-history-v2"
    const val ROUTE_SEND_CHAT = API + "webhook-message"

    const val ROUTE_GET_CONVERSATION_ID = API + "conversationId"
    const val ROUTE_GET_MQTT_CREDENTIALS = API + "mqtt"

}