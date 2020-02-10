package com.adyen.nexoapp.lib.model.api.body.payment

import com.squareup.moshi.Json

data class PoiTransactionId(
    @Json(name = "TransactionID") val transactionId: String
)
