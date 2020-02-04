package com.adyen.nexoapp.model

import com.squareup.moshi.Json

data class RequestWrapper(
    @Json(name = "SaleToPOIRequest") val saleToPoiRequest: SaleToPoiRequest
)
