package com.netomi.chat

class NativeLib {

    /**
     * A native method that is implemented by the 'chat' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'chat' library on application startup.
        init {
            System.loadLibrary("chat")
        }
    }
}