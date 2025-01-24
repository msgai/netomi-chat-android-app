package com.netomi.chat.model.media_payload

import android.net.Uri
import java.io.File

data class MultiFileModel(
    val mimeType:String,
    val uri: Uri?=null,
     val file: File,
    val fileName:String,

)
