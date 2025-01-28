package com.netomi.chat.utils

import android.content.Context
import android.media.MediaPlayer
import com.netomi.chat.R

class MessageSoundPlayer(private val context: Context) {
    private var mediaPlayerUser: MediaPlayer? = null
    private var mediaPlayerBot: MediaPlayer? = null

    init {
        mediaPlayerUser = MediaPlayer.create(context, R.raw.request_sound)
        mediaPlayerBot = MediaPlayer.create(context, R.raw.bot_sound)
    }


    fun playUserSound() {
        if (!mediaPlayerUser?.isPlaying!!) {
            mediaPlayerUser?.start()
        }
        mediaPlayerUser?.setOnCompletionListener {
            it.seekTo(0)
        }

    }


    fun playBotSound() {
        if (!mediaPlayerBot?.isPlaying!!) {
            mediaPlayerBot?.start()
        }
        mediaPlayerBot?.setOnCompletionListener {
            it.seekTo(0)
        }

    }

    // Release MediaPlayer resources
    fun release() {
        mediaPlayerUser?.release()
        mediaPlayerBot?.release()
        mediaPlayerUser = null
        mediaPlayerBot = null
    }
}
