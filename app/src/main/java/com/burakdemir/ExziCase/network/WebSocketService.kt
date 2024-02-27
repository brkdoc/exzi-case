package com.burakdemir.ExziCase.network

import com.burakdemir.ExziCase.model.OrderBookResponse
import io.reactivex.Single
import retrofit2.http.GET

interface WebSocketService {
    @GET("book/list?pair_id=1041&buy=1&sell=1")
    fun getOrderBook(): Single<OrderBookResponse>
}