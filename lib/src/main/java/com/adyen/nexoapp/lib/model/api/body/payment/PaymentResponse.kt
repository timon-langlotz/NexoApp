package com.adyen.nexoapp.lib.model.api.body.payment

import com.squareup.moshi.Json

data class PaymentResponse(
    @Json(name = "POIData") val poiData: PoiData,
    @Json(name = "Response") val response: Response
)
