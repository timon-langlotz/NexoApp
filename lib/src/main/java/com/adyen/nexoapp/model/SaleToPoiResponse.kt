package com.adyen.nexoapp.model

import com.adyen.nexoapp.model.body.payment.PaymentResponse
import com.adyen.nexoapp.model.header.MessageHeader
import com.squareup.moshi.Json

data class SaleToPoiResponse(
    @Json(name = "MessageHeader")   val messageHeader: MessageHeader,
    @Json(name = "PaymentResponse") val paymentResponse: PaymentResponse
)
