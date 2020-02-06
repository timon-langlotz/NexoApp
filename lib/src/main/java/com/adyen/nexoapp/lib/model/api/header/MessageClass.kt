package com.adyen.nexoapp.lib.model.api.header

import com.squareup.moshi.Json

enum class MessageClass {
    @Json(name = "Service") SERVICE,
    @Json(name = "Device")  DEVICE,
    @Json(name = "Event")   EVENT
}
