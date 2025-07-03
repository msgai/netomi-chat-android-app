package com.netomi.sampleapplication

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.sampleapplication.utils.AppLifecycleObserver


class SampleApplication: Application() {

    companion object{
        lateinit var appLifecycleObserver: AppLifecycleObserver
    }

    override fun onCreate() {
        super.onCreate()
        appLifecycleObserver= AppLifecycleObserver()
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
    }
}