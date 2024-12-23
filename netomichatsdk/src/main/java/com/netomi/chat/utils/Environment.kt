package com.netomi.chat.utils

enum class Environment (val baseUrl: String) {
    us("https://chatapps-us.netomi.com"),
    sg("https://chatapps-sg.netomi.com"),
    eu("https://chatapps-eu.netomi.com"),
    qa("https://chatapps-qa.netomi.com"),
    qaint("https://chatapps-qaint.netomi.com"),
    dev("https://chatapps-dev.netomi.com"),
}