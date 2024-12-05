package com.netomi.chat.model.theme.light_theme

data class FooterConfig(
    val backgroundColor: String?=null,
    val inputBoxTextColor: String?=null,
    val isFooterHidden: Boolean=false,
    val isNetomiBrandingEnabled: Boolean=false,
    val netomiBrandingText: String?=null,
    val netomiBrandingTextColor: String?=null,
    val tintColor: String?=null
)