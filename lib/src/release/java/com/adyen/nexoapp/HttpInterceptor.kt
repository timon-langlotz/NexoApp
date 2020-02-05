package com.adyen.nexoapp

import okhttp3.Interceptor
import okhttp3.Response

object HttpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}
