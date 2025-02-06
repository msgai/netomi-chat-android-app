package com.netomi.chat.utils

object NCWRoutes {
    private const val API = "api/"

    const val ROUTE_GET_CHAT =API + "webhook-history-v2"
    const val ROUTE_SEND_CHAT = API + "webhook-message"

    const val ROUTE_GET_CONVERSATION_ID = API + "getConversationId"
    const val ROUTE_GET_MQTT_CREDENTIALS = API + "mqtt"

    const val ROUTE_GET_PRESIGNED_URL = API + "upload"

    const val ROUTE_UPLOAD_MEDIA = "upload_media"

    const val ROUTE_END_CHAT= API+"webhook-event"

    const val ROUTE_BOT_JSON_CONFIG =API + "webhook-history-v2"



    const val ROUTE_SURVEY= "ROUTE_SURVEY"

    const val LOGIN= API+"login"

    const val LOGOUT= API+"logout"

    const val ROUTE_GET_SURVEY_RULE = API + "survey_rule"

    const val ROUTE_WEBHOOK_EVENT= API + "webhook-event"

    const val WEBHOOK_EVENT="webhook-event"

    const val ROUTE_SEND_TRANSCRIPT=API +"sendTranscript"






}