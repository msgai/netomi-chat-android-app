package com.netomi.chat
import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class NCWApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}