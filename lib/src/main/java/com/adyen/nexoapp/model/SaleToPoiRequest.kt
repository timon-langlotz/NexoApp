package com.adyen.nexoapp.model

import com.adyen.nexoapp.model.body.payment.PaymentRequest
import com.adyen.nexoapp.model.header.MessageHeader
import com.squareup.moshi.Json

data class SaleToPoiRequest(
    @Json(name = "MessageHeader")   val messageHeader: MessageHeader,
    @Json(name = "PaymentRequest")  val paymentRequest: PaymentRequest
)
