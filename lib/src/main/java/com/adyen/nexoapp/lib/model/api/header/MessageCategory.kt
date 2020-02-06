package com.adyen.nexoapp.lib.model.api.header

import com.squareup.moshi.Json

enum class MessageCategory {
    @Json(name = "Payment") PAYMENT
}
