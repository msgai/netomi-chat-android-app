package com.netomi.chat.config

import android.graphics.Color
import android.graphics.Typeface

data class NCWSdkConfig(val chatLogStyle: TextStyleConfig = TextStyleConfig(),
                        val inputFieldStyle: TextStyleConfig = TextStyleConfig(),
                        val sendButtonStyle: ButtonStyleConfig = ButtonStyleConfig()
){
    /**
     * Configuration class for customizing text views (e.g., chat logs, input fields).
     */
    data class TextStyleConfig(
        val textColor: Int = Color.BLACK,
        val fontFamily: Typeface? = null,
        val fontSize: Float = 16f
    )

    /**
     * Configuration class for customizing buttons (e.g., send button).
     */
    data class ButtonStyleConfig(
        val backgroundColor: Int = Color.BLACK,
        val textColor: Int = Color.WHITE,
        val fontFamily: Typeface? = null,
        val fontSize: Float = 16f
    )
}
