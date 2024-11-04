package com.netomi.chat.model.theme

data class Theme(
    val color: String,
    val current: String,
    val globalColorTheme: Boolean,
    val gradient: Boolean,
    val gradientColors: List<String>,
    val gradientDirection: Int
)