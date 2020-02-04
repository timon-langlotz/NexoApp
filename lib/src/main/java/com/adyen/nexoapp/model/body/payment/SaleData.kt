package com.adyen.nexoapp.model.body.payment

import com.squareup.moshi.Json

data class SaleData(
    @Json(name = "SaleTransactionID") val saleTransactionId: SaleTransactionId
)
