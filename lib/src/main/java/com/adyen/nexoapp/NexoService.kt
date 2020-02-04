package com.adyen.nexoapp

import com.adyen.nexoapp.model.RequestWrapper
import com.adyen.nexoapp.model.ResponseWrapper
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface NexoService {
    @POST("nexo")
    fun sendPayment(@Body request: RequestWrapper): Call<ResponseWrapper>

    companion object {
        fun forIpAddress(ipAddress: String): NexoService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://$ipAddress:8443/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            return retrofit.create(NexoService::class.java)
        }
    }
}
