package com.netomi.chat.model.feedback.feedbackrequest

import com.netomi.chat.model.feedback.feedbackresponse.NCWRequestBody

data class NCWFeedbackRequest(
    val botRefId: String,
    val requestBody: NCWRequestBody
)