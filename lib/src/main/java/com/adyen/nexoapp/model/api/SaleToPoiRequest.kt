package com.adyen.nexoapp.model.api

import com.adyen.nexoapp.model.api.body.payment.PaymentRequest
import com.adyen.nexoapp.model.api.header.MessageHeader
import com.squareup.moshi.Json

data class SaleToPoiRequest(
    @Json(name = "MessageHeader")   val messageHeader: MessageHeader,
    @Json(name = "PaymentRequest")  val paymentRequest: PaymentRequest
)
