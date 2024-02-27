package com.burakdemir.ExziCase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.burakdemir.ExziCase.model.MarketResponse
import com.burakdemir.ExziCase.model.OrderBookResponse
import com.burakdemir.ExziCase.repository.ChartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val repository: ChartRepository
) : ViewModel() {

    val orderBookResponse = MutableLiveData<OrderBookResponse>()
    val errorMessage = MutableLiveData<String>()
    private val _tickerResponse = MutableLiveData<MarketResponse>()
    val tickerResponse: LiveData<MarketResponse> = _tickerResponse

    private val disposables = CompositeDisposable()

    init {
    }

    fun fetchTicker(){
        disposables.add(
            repository.getTicker()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _tickerResponse.value = response
                }, { error ->
                    errorMessage.value = error.message
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
