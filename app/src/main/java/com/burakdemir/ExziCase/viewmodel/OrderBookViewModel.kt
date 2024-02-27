package com.burakdemir.ExziCase.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.burakdemir.ExziCase.model.OrderBookResponse
import com.burakdemir.ExziCase.network.OrderBookSocketService
import com.burakdemir.ExziCase.repository.OrderBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OrderBookViewModel @Inject constructor(
    webSocketService: OrderBookSocketService,
    private val repository: OrderBookRepository
) : ViewModel() {

    private val _orderBookStream = MutableLiveData<OrderBookResponse>()
    val orderBookStream: LiveData<OrderBookResponse> = _orderBookStream

    val orderBookResponse = MutableLiveData<OrderBookResponse>()
    val errorMessage = MutableLiveData<String>()

    private val disposables = CompositeDisposable()

    init {
        disposables.add(
            webSocketService.observeOrderBook()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ orderBook ->
                    _orderBookStream.postValue(orderBook)
                }, { throwable ->
                    Log.e("OrderBookViewModel", "WebSocket error", throwable)
                })
        )
    }

    fun fetchOrderBook() {
        disposables.add(
            repository.getOrderBook()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    orderBookResponse.value = response
                }, { error ->
                    error.printStackTrace()
                    errorMessage.value = error.message
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
