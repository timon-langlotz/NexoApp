package com.adyen.nexoapp.lib.model.api

import com.adyen.nexoapp.lib.model.api.body.payment.PaymentRequest
import com.adyen.nexoapp.lib.model.api.header.MessageHeader
import com.squareup.moshi.Json

data class SaleToPoiRequest(
    @Json(name = "MessageHeader")   val messageHeader: MessageHeader,
    @Json(name = "PaymentRequest")  val paymentRequest: PaymentRequest
)
