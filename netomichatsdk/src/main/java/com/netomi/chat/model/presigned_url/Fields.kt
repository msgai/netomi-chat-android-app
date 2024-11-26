package com.netomi.chat.model.presigned_url

import com.google.gson.annotations.SerializedName

data class Fields(
    @SerializedName("key"              ) var key              : String? = null,
    @SerializedName("bucket"           ) var bucket           : String? = null,
    @SerializedName("X-Amz-Algorithm"  ) var XAmzAlgorithm  : String? = null,
    @SerializedName("X-Amz-Credential" ) var XAmzCredential : String? = null,
    @SerializedName("X-Amz-Date"       ) var XAmzDate       : String? = null,
    @SerializedName("Policy"           ) var Policy           : String? = null,
    @SerializedName("X-Amz-Signature"  ) var XAmzSignature  : String? = null
)