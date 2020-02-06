package com.adyen.nexoapp.lib.model.api.body.payment

import com.squareup.moshi.Json

data class AmountsReq(
    @Json(name = "Currency")        val currency: String,
    @Json(name = "RequestedAmount") val requestedAmount: Double
)
