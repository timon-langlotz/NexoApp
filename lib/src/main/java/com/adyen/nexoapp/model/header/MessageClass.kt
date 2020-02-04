package com.adyen.nexoapp.model.header

import com.squareup.moshi.Json

enum class MessageClass {
    @Json(name = "Service") SERVICE,
    @Json(name = "Device")  DEVICE,
    @Json(name = "Event")   EVENT
}
