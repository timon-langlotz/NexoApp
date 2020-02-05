package com.adyen.nexoapp

import android.content.Context
import com.adyen.nexoapp.model.api.RequestWrapper
import com.adyen.nexoapp.model.api.ResponseWrapper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


interface NexoService {
    @POST("nexo")
    fun sendPayment(@Body request: RequestWrapper): Call<ResponseWrapper>

    companion object {
        fun forIpAddress(context: Context, ipAddress: String): NexoService {
            val okHttpClient = getOkHttpClient(context, ipAddress)
            val converterFactory = MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
            val retrofit = Retrofit.Builder()
                .baseUrl("https://$ipAddress:8443/")
                .addConverterFactory(converterFactory)
                .client(okHttpClient)
                .build()
            return retrofit.create(NexoService::class.java)
        }

        private fun getOkHttpClient(context: Context, ipAddress: String): OkHttpClient {
            val trustManagers = getTrustManagersWithTerminalCert(context)
            val socketFactory = SSLContext.getInstance("TLSv1.2")
                .apply { init(null, trustManagers, null) }
                .socketFactory
            return OkHttpClient.Builder()
                .sslSocketFactory(socketFactory, trustManagers.first { it is X509TrustManager } as X509TrustManager)
                .hostnameVerifier(HostnameVerifier { hostname, _ -> hostname == ipAddress })
                .addInterceptor(HttpInterceptor)
                .build()
        }

        private fun getTrustManagersWithTerminalCert(context: Context): Array<out TrustManager> {
            val terminalCert = CertificateFactory
                .getInstance("X.509")
                .generateCertificate(context.resources.openRawResource(R.raw.terminal_cert))
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null, null)
            keyStore.setCertificateEntry("adyen-terminalfleet", terminalCert)
            return TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm())
                .apply { init(keyStore) }
                .trustManagers
        }
    }
}
