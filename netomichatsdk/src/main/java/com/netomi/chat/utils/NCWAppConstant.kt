package com.netomi.chat.utils

object NCWAppConstant {

    const val SESSION_TOKEN = "SESSION_TOKEN"


    // Store AWS Credentials Constant
    const val SECURE_CREDENTIAL="secure_credentials"
    const val AWS_ACCESS_KEY="ACCESS_KEY"
    const val AWS_SECRET_KEY="SECRET_KEY"
    const val AWS_SESSION_TOKEN="SESSION_TOKEN"
    const val AWS_IOT_ENDPOINT="IOT_ENDPOINT"
    const val BOT_REFERENCE_ID="botRefId"

    const val TYPE_INDICATOR="INDICATOR"
    const val CHAT_WIDGET="chat_widget"
    const val EXPIRE_TIME="EXPIRE_TIME"

    const val TYPE_RESPONSE="RESPONSE"
    const val TYPE_REQUEST="REQUEST"
    const val TYPE_INITIAL="INITIAL"

    const val TYPE_EVENT="EVENT"
    const val SESSION="session"
    const val SIZE_LIMIT="limit"

    const val TYPE_FORM="FORM"

    const val FORM_DATE_FORMAT="MM/dd/yyyy"

    const val TYPE_ATTACHMENT="attachment"
    const val TYPE_FORM_ATTACHMENT="form_attachment"


    const val BOT="BOT"

    const val TIME_AM_PM="hh:mm a"
    const val ARG_MEDIA_URL = "media_url"
    const val MEDIA_TYPE = "media_type"
    const val ARG_FILE_URI = "file_uri"

    const val TYPE_IMAGE="ai.msg.domain.responses.core.Image"
    const val TYPE_VIDEO="ai.msg.domain.responses.core.Video"
    const val TYPE_TEXT="ai.msg.domain.responses.core.Text"
    const val TYPE_FILE="ai.msg.domain.responses.core.GenericFileAttachment"
    const val TYPE_CAROUSEL="ai.msg.domain.responses.core.Carousel"

    const val DATE_FORMAT="yyyyMMdd_HHmmss"

    object RatingType {
        const val STAR = "STAR"
        const val NUM_10 = "NUM_10"
        const val NUM_5 = "NUM_5"
        const val EMOJI = "EMOJI"
        const val THUMBS_UP_DOWN = "THUMBS_UP_DOWN"

        const val STAR_VALUE = 0
        const val NUM_VALUE = 1
        const val EMOJI_VALUE = 2
        const val THUMBS_UP_DOWN_VALUE = 3
    }




}