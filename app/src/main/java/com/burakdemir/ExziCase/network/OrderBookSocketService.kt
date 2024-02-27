package com.burakdemir.ExziCase.network

import com.burakdemir.ExziCase.model.OrderBookResponse
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface OrderBookSocketService {
    @Receive
    fun observeWebSocketEvent(): Flowable<WebSocket.Event>

    @Send
    fun sendSubscribeMessage(message: String)

    @Receive
    fun observeOrderBook(): Flowable<OrderBookResponse>
}