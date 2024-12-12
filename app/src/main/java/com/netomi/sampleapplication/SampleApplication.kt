package com.netomi.sampleapplication

import android.app.Application
import com.netomi.chat.ui.init.NCWChatSdk


class SampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        //Appinventiv BOT
        NCWChatSdk.initialize(this,"60e915d0-3eda-4fda-8c50-2da9dc036edf")

        //NCWChatSdk.initialize(this,"53e804cd-12ba-45cb-b41d-52413ead4dd4")
        // Aakash Bindal Bot
        //NCWChatSdk.initialize(this,"06cae34e-0738-4359-8347-a88b7d0c87d4")


    }
}