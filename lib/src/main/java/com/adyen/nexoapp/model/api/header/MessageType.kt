package com.adyen.nexoapp.model.api.header

import com.squareup.moshi.Json

enum class MessageType {
    @Json(name = "Request")     REQUEST,
    @Json(name = "Response")    RESPONSE
}
