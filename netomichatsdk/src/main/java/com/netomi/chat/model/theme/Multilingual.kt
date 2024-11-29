package com.netomi.chat.model.theme

data class Multilingual(
    val enabled: Boolean,
    val isAutoLocalisationEnabled: Boolean,
    val languages: List<Language>
)