package com.adyen.nexoapp.model.api.header

import com.squareup.moshi.Json

enum class MessageCategory {
    @Json(name = "Payment") PAYMENT
}
