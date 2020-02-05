package com.adyen.nexoapp.model.api

import com.squareup.moshi.Json

data class RequestWrapper(
    @Json(name = "SaleToPOIRequest") val saleToPoiRequest: SaleToPoiRequest
)
