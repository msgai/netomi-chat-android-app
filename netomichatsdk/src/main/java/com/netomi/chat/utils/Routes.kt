package com.netomi.chat.utils

object Routes {
    private const val API = "api/"

    const val ROUTE_GET_CHAT =API + "webhook-history-v2"
    const val ROUTE_SEND_CHAT = API + "webhook-message"

    const val ROUTE_GET_CONVERSATION_ID = API + "conversationId"
    const val ROUTE_GET_MQTT_CREDENTIALS = API + "mqtt"

    const val ROUTE_GET_PRESIGNED_URL = API + "upload"

    const val ROUTE_UPLOAD_MEDIA = "upload_media"

    const val ROUTE_END_CHAT= API+"webhook-event"

    const val ROUTE_BOT_JSON_CONFIG =API + "webhook-history-v2"

}