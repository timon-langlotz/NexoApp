package com.adyen.nexoapp.lib.model.api

import com.squareup.moshi.Json

data class ResponseWrapper(
    @Json(name = "SaleToPOIResponse") val saleToPoiResponse: SaleToPoiResponse
)
