package com.adyen.nexoapp.lib.model.api.body.payment

import com.squareup.moshi.Json

data class Response(
    @Json(name = "AdditionalResponse")  val additionalResponse: String,
    @Json(name = "Result")              val result: String
)
