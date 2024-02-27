package com.burakdemir.ExziCase.repository

import com.burakdemir.ExziCase.model.OrderBookResponse
import com.burakdemir.ExziCase.network.ApiService
import com.burakdemir.ExziCase.network.WebSocketService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class OrderBookRepository @Inject constructor(@Named("WebSocketService") private val webSocketService: WebSocketService) {
    fun getOrderBook(): Single<OrderBookResponse> = webSocketService.getOrderBook()
}