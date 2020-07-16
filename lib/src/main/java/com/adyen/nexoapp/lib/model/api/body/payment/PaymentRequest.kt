package com.adyen.nexoapp.lib.model.api.body.payment

import com.squareup.moshi.Json

data class PaymentRequest(
    @Json(name = "PaymentTransaction")  val paymentTransaction: PaymentTransaction,
    @Json(name = "SaleData")            val saleData: SaleData,
    @Json(name = "PaymentData")         val paymentData: PaymentData? = null
)
