package com.netomi.chat.model.theme

data class CustomHandoffConfig(
    val outsideOperatingHoursHandoffEnabled: List<String>,
    val unsuccessfulConnectionMessage_: String,
    val withinOperatingHoursHandoffEnabled: List<String>
)