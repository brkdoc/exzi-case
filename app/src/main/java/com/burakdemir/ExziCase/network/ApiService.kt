package com.burakdemir.ExziCase.network

import com.burakdemir.ExziCase.model.MarketResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
    @GET("api/default/ticker")
    fun getTicker(): Single<MarketResponse>
}