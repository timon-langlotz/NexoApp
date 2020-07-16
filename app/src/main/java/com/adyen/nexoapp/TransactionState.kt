package com.adyen.nexoapp

import com.adyen.nexoapp.lib.model.api.body.payment.PaymentResponse

sealed class TransactionState

object IdleState : TransactionState()
object InProgressState : TransactionState()
data class CompleteState(val result: PaymentResponse) : TransactionState()
data class ErrorState(val message: String) : TransactionState()
