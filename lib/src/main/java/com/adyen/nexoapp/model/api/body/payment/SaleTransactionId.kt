package com.adyen.nexoapp.model.api.body.payment

import com.squareup.moshi.Json

data class SaleTransactionId(
    @Json(name = "TimeStamp")       val timeStamp: String,
    @Json(name = "TransactionID")   val transactionId: String
)
