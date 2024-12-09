package com.netomi.chat.model.theme

data class NCWCustomHandoffConfig(
    val outsideOperatingHoursHandoffEnabled: List<String>,
    val unsuccessfulConnectionMessage_: String,
    val withinOperatingHoursHandoffEnabled: List<String>
)