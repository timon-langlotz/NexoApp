package com.adyen.nexoapp.util

import android.os.Handler
import android.os.Looper

private val handler by lazy { Handler(Looper.getMainLooper()) }

fun runOnUiThread(delay: Int = 0, callback: () -> Unit) {
    if (delay <= 0) {
        handler.post(callback)
    } else {
        handler.postDelayed(callback, delay.toLong())
    }
}
