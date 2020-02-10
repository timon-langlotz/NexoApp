package com.adyen.nexoapp.payment

import com.adyen.nexoapp.lib.model.api.body.payment.PaymentResponse

sealed class PaymentState

object IdleState : PaymentState()
object InProgressState : PaymentState()
data class CompleteState(val result: PaymentResponse) : PaymentState()
data class ErrorState(val message: String) : PaymentState()
