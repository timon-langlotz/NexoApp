package com.adyen.nexoapp.model

import com.squareup.moshi.Json

data class ResponseWrapper(
    @Json(name = "SaleToPOIResponse") val saleToPoiResponse: SaleToPoiResponse
)
