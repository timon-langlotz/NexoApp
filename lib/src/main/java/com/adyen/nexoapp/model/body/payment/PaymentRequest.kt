package com.adyen.nexoapp.model.body.payment

import com.squareup.moshi.Json

data class PaymentRequest(
    @Json(name = "PaymentTransaction")  val paymentTransaction: PaymentTransaction,
    @Json(name = "SaleData")            val saleData: SaleData
)
