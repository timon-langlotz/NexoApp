package com.adyen.nexoapp.lib

import android.app.Application
import android.content.Context
import com.adyen.nexoapp.lib.model.api.ResponseWrapper
import com.adyen.nexoapp.lib.model.api.SaleToPoiRequest
import com.adyen.nexoapp.lib.model.api.TerminalRequest
import com.adyen.nexoapp.lib.model.api.body.payment.AmountsReq
import com.adyen.nexoapp.lib.model.api.body.payment.PaymentRequest
import com.adyen.nexoapp.lib.model.api.body.payment.PaymentResponse
import com.adyen.nexoapp.lib.model.api.body.payment.PaymentTransaction
import com.adyen.nexoapp.lib.model.api.body.payment.SaleData
import com.adyen.nexoapp.lib.model.api.body.payment.SaleTransactionId
import com.adyen.nexoapp.lib.model.api.header.MessageCategory
import com.adyen.nexoapp.lib.model.api.header.MessageHeader
import com.adyen.nexoapp.lib.util.Random
import com.adyen.nexoapp.lib.util.toUtcTimeStamp
import retrofit2.Call
import retrofit2.Response
import java.util.Date
import java.util.UUID

class TerminalCommunicator(application: Application, val poiId: String, val ipAddress: String) {
    private val saleId by lazy {
        val sharedPrefs = application.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        sharedPrefs.getString(NAME_SALE_ID, null) ?: {
            val newSaleId = UUID.randomUUID().toString()
            sharedPrefs.edit().putString(NAME_SALE_ID, newSaleId).apply()
            newSaleId
        }.invoke()
    }

    private val terminalApiService by lazy { TerminalApiService.forIpAddress(application, ipAddress) }

    fun startPayment(
        currency: String,
        amount: Double,
        serviceId: String = Random.alphaNumeric(10),
        transactionId: String = Random.alphaNumeric(16),
        callback: Callback<PaymentResponse>
    ) {
        val header = MessageHeader(MessageCategory.PAYMENT, saleId, serviceId, poiId)
        val amountsReq = AmountsReq(currency, amount)
        val paymentTransaction = PaymentTransaction(amountsReq)
        val timeStamp = Date().toUtcTimeStamp()
        val saleTransactionId = SaleTransactionId(timeStamp, transactionId)
        val saleData = SaleData(saleTransactionId)
        val paymentRequest = PaymentRequest(paymentTransaction, saleData)
        val saleToPoiRequest = SaleToPoiRequest(header, paymentRequest)
        val terminalRequest = TerminalRequest(saleToPoiRequest)
        terminalApiService.startPayment(terminalRequest).enqueue(object : retrofit2.Callback<ResponseWrapper> {
            override fun onResponse(call: Call<ResponseWrapper>, response: Response<ResponseWrapper>) {
                val responseWrapper = response.body()

                if (response.isSuccessful && responseWrapper != null) {
                    callback.onResponse(responseWrapper.saleToPoiResponse.paymentResponse)
                } else {
                    val t = Throwable(response.errorBody()?.string() ?: "An unexpected error occurred.")
                    callback.onError(t)
                }
            }

            override fun onFailure(call: Call<ResponseWrapper>, t: Throwable) {
                callback.onError(t)
            }
        })
    }

    companion object {
        private const val SHARED_PREFS_NAME = "NexoCommunicator"
        private const val NAME_SALE_ID = "SALE_ID"
    }

    interface Callback<Res> {
        fun onResponse(response: Res)
        fun onError(throwable: Throwable)
    }
}
