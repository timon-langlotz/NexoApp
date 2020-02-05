package com.adyen.nexoapp.model.body.payment

import com.squareup.moshi.Json

data class AmountsReq(
    @Json(name = "Currency")        val currency: String,
    @Json(name = "RequestedAmount") val requestedAmount: Double
)
