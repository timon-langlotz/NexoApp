package com.adyen.nexoapp.lib

import android.app.Application
import com.adyen.nexoapp.lib.model.api.ResponseWrapper
import com.adyen.nexoapp.lib.model.api.body.payment.PaymentResponse
import com.adyen.nexoapp.lib.model.transactions.TransactionModel
import retrofit2.Call
import retrofit2.Response

class TerminalCommunicator(val application: Application, val ipAddress: String) {

    private val terminalApiService by lazy { TerminalApiService.forIpAddress(application, ipAddress) }

    fun startTransaction(terminalRequest: TransactionModel,
                         callback: Callback<PaymentResponse>) {

        terminalApiService.startTransaction(terminalRequest.createTerminalRequest()).enqueue(object : retrofit2.Callback<ResponseWrapper> {
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

    interface Callback<Res> {
        fun onResponse(response: Res)
        fun onError(throwable: Throwable)
    }

}
