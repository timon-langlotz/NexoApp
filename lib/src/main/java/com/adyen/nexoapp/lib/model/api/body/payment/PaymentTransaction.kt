package com.adyen.nexoapp.lib.model.api.body.payment

import com.squareup.moshi.Json

data class PaymentTransaction(
    @Json(name = "AmountsReq") val amountsReq: AmountsReq
)
