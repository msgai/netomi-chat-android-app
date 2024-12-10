package com.netomi.chat.model.theme.light_theme

data class NCWHeaderConfig(
    val backgroundColor: String="#ff0099cc",
    val gradientColors: List<String>?=listOf(
        "#00A0AA",
        "#AE0000",
        "#8C949B",
        "#B98C00",
        "#000000"
    ),
    val gradientDirection: Int=8,
    val iconBackgroundColor: String="#FFFFFF",
    val isBackPressPopupEnabed: Boolean=true,
    val isGradientAppied: Boolean=false,
    val logoImage: String?=null,
    val tintColor: String= "#000000"
)