package com.netomi.chat.model.theme

data class NCWFormField(
    val `field`: String,
    val fieldType: String,
    val label: String,
    val placeholder: String,
    val validations: List<String>
)