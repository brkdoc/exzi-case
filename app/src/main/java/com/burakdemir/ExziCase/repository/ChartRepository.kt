package com.burakdemir.ExziCase.repository

import com.burakdemir.ExziCase.model.MarketResponse
import com.burakdemir.ExziCase.model.OrderBookResponse
import com.burakdemir.ExziCase.network.ApiService
import com.burakdemir.ExziCase.network.WebSocketService
import com.tinder.scarlet.WebSocket
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class ChartRepository @Inject constructor(
    @Named("WebSocketService") private val webSocketService: WebSocketService,
    @Named("TickerService") private val apiService: ApiService) {
    fun getOrderBook(): Single<OrderBookResponse> = webSocketService.getOrderBook()
    fun getTicker(): Single<MarketResponse> = apiService.getTicker()

}