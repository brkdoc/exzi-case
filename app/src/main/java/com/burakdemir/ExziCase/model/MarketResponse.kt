package com.burakdemir.ExziCase.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyInfo(
    val id: Int,
    val decimal: Int,
    val iso3: String,
    val name: String,
    val rateUsd: Long,
    val rateUsdt: Long,
    val rateBtc: Long,
    val rateEth: Long?
)

@JsonClass(generateAdapter = true)
data class VolumeInfo(
    val d: Long,
    val m: Long,
    val w: Long
)

@JsonClass(generateAdapter = true)
data class MarketPair(
    val _id: String,
    val id: Int,
    val mainCurrencyId: Int,
    val secondCurrencyId: Int,
    val timeCreate: Long,
    val timeUpdate: Long,
    val status: Int,
    val name: String,
    val type: Int,
    val open: Long,
    val volume: Long,
    val volumeWorld: String,
    val volumeSecondWorld: String,
    val close: Long,
    val high: Long,
    val low: Long,
    val percent: Double,
    val percentW: Double,
    val percentH: Double,
    val rate: Long,
    val logo: String?,
    val main: CurrencyInfo,
    val volumes: VolumeInfo,
    val second: CurrencyInfo,
    val commissionPercent: Double,
    val commissionPercentMarket: Double,
    val minAmount: Double,
    val fixed: String?,
    val commissionMin: String?,
    val lastRate24: Long,
    val lastRateW: Long,
    val lastRateH: Long,
    val typeUpdateTime: Long?,
    val pairType: String?,
    val filters: List<String>,
    val openF: String,
    val closeF: String,
    val highF: String,
    val lowF: String,
    val rateF: String,
    val lastRate24F: String,
    val lastRateWF: String,
    val lastRateHF: String,
    val volumeF: String,
    val volumeSecondWorldF: String,
    val volumeWorldF: String
)

@JsonClass(generateAdapter = true)
data class MarketResponse(
    val status: Boolean,
    val data: List<MarketPair>,
    val isLogin: Boolean
)
