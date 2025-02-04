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
    const val TYPE_PILLS="PILLS"
    const val SUB_TYPE_WAIT="WAIT"
    const val SUB_TYPE_JOIN="JOIN"
    const val SUB_TYPE_LEAVE="LEAVE"
    const val TYPE_AGENT_EVENT="AGENT_EVENT"
    const val TYPE_QUEUE_POSITION="QUEUE_POSITION"
    const val OAUTH="OAUTH"
    const val SUB_TYPE_OAUTH="SIGN_IN"
    const val SUB_TYPE_TRANSFER="TRANSFER_AND_JOIN"
    const val SUB_TYPING="TYPING"
    const val RULE_EVENT_CHAT_END="WIDGET_EVENT_CHAT_END"
    const val RULE_EVENT_IDLE_USER="WIDGET_EVENT_IDLE_USER"
    const val CHANNEL_ID="NETOMI_WEB_WIDGET"
    const val SUB_TYPE_IDLE_USER="IDLE_USER"
    const val INFO_EVENT="INFO"
    const val NETOMI="NETOMI"
    const val EVENT_WIDGET="WIDGET_EVENT"



    const val SESSION="session"
    const val SIZE_LIMIT="limit"

    const val LOGOUT="logout"


    const val TYPE_FORM="FORM"

    const val FORM_DATE_FORMAT="mm/dd/yyyy"

    const val SHOW_FORM_DATE_FORMAT="dd/mm/yyyy"

    const val TYPE_ATTACHMENT="attachment"
    const val TYPE_FORM_ATTACHMENT="form_attachment"


    const val TYPE_BOT="BOT"
    const val TYPE_AGENT="AGENT"


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


    const val TYPE_SUBMITTED_SURVEY="submittedSurvey"
    const val TYPE_SHOW_SURVEY="showSurvey"
    const val NORMAL="normal"
    const val STREAMING="streaming"
    const val PROACTIVE_GREETING="PROACTIVE_GREETING"
    const val UPLOAD_FILE_MULTIPLE="Multiple"
    const val SKIP_LABEL="SKIP"

    const val MESSAGE_BACK_TO_BOT="back to bot"


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