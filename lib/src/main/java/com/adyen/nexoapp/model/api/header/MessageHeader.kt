package com.adyen.nexoapp.model.api.header

import com.squareup.moshi.Json

data class MessageHeader(
    @Json(name = "MessageCategory") val messageCategory: MessageCategory,
    @Json(name = "SaleID")          val saleId: String,
    @Json(name = "ServiceID")       val serviceId: String,
    @Json(name = "POIID")           val poiId: String,
    @Json(name = "ProtocolVersion") val protocolVersion: String = "3.0",
    @Json(name = "MessageClass")    val messageClass: MessageClass = MessageClass.SERVICE,
    @Json(name = "MessageType")     val messageType: MessageType = MessageType.REQUEST
)
