package com.adyen.nexoapp.model.api

import com.adyen.nexoapp.model.api.body.payment.PaymentResponse
import com.adyen.nexoapp.model.api.header.MessageHeader
import com.squareup.moshi.Json

data class SaleToPoiResponse(
    @Json(name = "MessageHeader")   val messageHeader: MessageHeader,
    @Json(name = "PaymentResponse") val paymentResponse: PaymentResponse
)
