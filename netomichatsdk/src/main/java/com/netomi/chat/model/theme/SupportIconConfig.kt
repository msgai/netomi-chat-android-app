package com.netomi.chat.model.theme

data class SupportIconConfig(
    val backgroundColor: String,
    val gradientColors: List<String>,
    val gradientDirection: Int,
    val icon: String,
    val isSupportTextEnabled: Boolean,
    val positionOfIcon: String,
    val radius: String,
    val supportTextValue: String
)