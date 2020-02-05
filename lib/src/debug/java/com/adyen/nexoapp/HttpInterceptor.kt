package com.adyen.nexoapp

import android.util.Log
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

private val bodyLoggingInterceptor by lazy {
    HttpLoggingInterceptor(
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d(HttpInterceptor.LOG_TAG, message)
            }
        }
    ).apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
}
object HttpInterceptor : Interceptor by bodyLoggingInterceptor {
    const val LOG_TAG = "HttpInterceptor"
}
