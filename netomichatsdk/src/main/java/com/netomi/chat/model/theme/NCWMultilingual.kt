package com.netomi.chat.model.theme

data class NCWMultilingual(
    val enabled: Boolean,
    val isAutoLocalisationEnabled: Boolean,
    val languages: List<NCWLanguage>
)