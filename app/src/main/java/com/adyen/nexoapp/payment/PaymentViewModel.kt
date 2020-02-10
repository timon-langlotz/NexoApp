package com.adyen.nexoapp.payment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adyen.nexoapp.lib.TerminalCommunicator
import com.adyen.nexoapp.lib.model.api.body.payment.PaymentResponse
import com.adyen.nexoapp.util.runOnUiThread

class PaymentViewModel(application: Application, poiId: String, ipAddress: String) : AndroidViewModel(application) {
    private val terminalCommunicator by lazy { TerminalCommunicator(application, poiId, ipAddress) }

    private val _paymentStateLiveData = MutableLiveData<PaymentState>().apply { value = IdleState }

    val paymentStateLiveData: LiveData<PaymentState> = _paymentStateLiveData

    fun startPayment(currency: String, amount: Double) {
        _paymentStateLiveData.postValue(InProgressState)
        terminalCommunicator.startPayment(currency, amount, callback = object : TerminalCommunicator.Callback<PaymentResponse> {
            override fun onResponse(response: PaymentResponse) {
                _paymentStateLiveData.postValue(CompleteState(response))
                runOnUiThread(IDLE_DELAY) { _paymentStateLiveData.value = IdleState }
            }

            override fun onError(throwable: Throwable) {
                postError(throwable.message ?: "")
            }
        })
    }

    fun postError(message: String) {
        _paymentStateLiveData.postValue(ErrorState(message))
        runOnUiThread(IDLE_DELAY) { _paymentStateLiveData.value = IdleState }
    }

    companion object {
        private const val IDLE_DELAY = 3000
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
