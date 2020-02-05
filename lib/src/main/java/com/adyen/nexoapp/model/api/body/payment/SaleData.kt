package com.adyen.nexoapp.model.api.body.payment

import com.squareup.moshi.Json

data class SaleData(
    @Json(name = "SaleTransactionID") val saleTransactionId: SaleTransactionId
)
