package com.netomi.chat.model.theme.light_theme

data class HeaderConfig(
    val backgroundColor: String?=null,
    val gradientColors: List<String>?=null,
    val gradientDirection: Int?=null,
    val iconBackgroundColor: String?=null,
    val isBackPressPopupEnabed: Boolean=true,
    val isGradientAppied: Boolean=false,
    val logoImage: String?=null,
    val tintColor: String?=null
)