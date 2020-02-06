package com.adyen.nexoapp.payment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adyen.nexoapp.lib.TerminalCommunicator
import com.adyen.nexoapp.lib.model.api.body.payment.PaymentResponse

class PaymentViewModel(application: Application, poiId: String, ipAddress: String) : AndroidViewModel(application) {
    private val terminalCommunicator by lazy { TerminalCommunicator(application, poiId, ipAddress) }

    fun sendPayment(currency: String, amount: Double) {
        terminalCommunicator.sendPayment(currency, amount, callback = object : TerminalCommunicator.Callback<PaymentResponse> {
            override fun onResponse(response: PaymentResponse) {
                println(response)
            }

            override fun onError(throwable: Throwable) {
                println(throwable)
            }
        })
    }

    class Factory(
        private val application: Application,
        private val poiId: String,
        private val ipAddress: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PaymentViewModel(application, poiId, ipAddress) as T
        }
    }
}
