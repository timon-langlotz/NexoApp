package com.adyen.nexoapp.lib.model.api.body.payment

import com.squareup.moshi.Json

data class PoiData(
    @Json(name = "POITransactionID") val poiTransactionId: PoiTransactionId
)
