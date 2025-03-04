package com.netomi.chat.model.theme

data class NCWMultilingual(
    val enabled: Boolean,
    val isAutoLocalisationEnabled: Boolean,
    val languages: List<NCWLanguage>,
    var selectedCode: String="en",
    var selectLanguageLabel: String = "English",

)