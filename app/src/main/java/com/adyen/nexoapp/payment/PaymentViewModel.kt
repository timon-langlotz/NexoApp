package com.adyen.nexoapp.payment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adyen.nexoapp.*
import com.adyen.nexoapp.lib.TerminalCommunicator
import com.adyen.nexoapp.lib.model.api.body.payment.PaymentResponse
import com.adyen.nexoapp.lib.model.transactions.PaymentModel
import com.adyen.nexoapp.util.runOnUiThread
import com.adyen.nexoapp.terminal.SelectedTerminalInfo

class PaymentViewModel(val app: Application) : AndroidViewModel(app) {
    private val terminalCommunicator by lazy { TerminalCommunicator(app, SelectedTerminalInfo.ipAddress) }

    private val _paymentStateLiveData = MutableLiveData<TransactionState>().apply { value =
        IdleState
    }

    val transactionStateLiveData: LiveData<TransactionState> = _paymentStateLiveData

    fun startPayment(currency: String, amount: Double) {
        _paymentStateLiveData.postValue(InProgressState)
        val poiId = SelectedTerminalInfo.poiId
        val payment = PaymentModel(
            app,
            poiId,
            currency,
            amount
        )
        terminalCommunicator.startTransaction(payment, callback = object : TerminalCommunicator.Callback<PaymentResponse> {
            override fun onResponse(response: PaymentResponse) {
                _paymentStateLiveData.postValue(
                    CompleteState(
                        response
                    )
                )
                runOnUiThread(IDLE_DELAY) { _paymentStateLiveData.value =
                    IdleState
                }
            }

            override fun onError(throwable: Throwable) {
                postError(throwable.message ?: "")
            }
        })
    }

    fun postError(message: String) {
        _paymentStateLiveData.postValue(ErrorState(message))
        runOnUiThread(IDLE_DELAY) { _paymentStateLiveData.value =
            IdleState
        }
    }

    companion object {
        private const val IDLE_DELAY = 3000
    }

    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PaymentViewModel(application) as T
        }
    }
}
