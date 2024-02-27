package com.burakdemir.ExziCase.model

import com.squareup.moshi.JsonClass
import java.util.UUID

@JsonClass(generateAdapter = true)
data class OrderBookResponse(
    val buy: List<OrderBookEntry>,
    val sell: List<OrderBookEntry>
)

@JsonClass(generateAdapter = true)
data class OrderBookEntry(
    val uniqueId: String = UUID.randomUUID().toString(),
    val volume: Long,
    val count: Int,
    val rate: Long,
    val price: Int,
    val rate_f: String,
    val volume_f: String
)