package com.example.cryptocurrencyapp.ui.home.coindetail

import androidx.lifecycle.liveData
import com.example.cryptocurrencyapp.data.repository.CoinRepository
import com.example.cryptocurrencyapp.ui.base.BaseViewModel
import com.example.cryptocurrencyapp.util.Constants
import com.example.cryptocurrencyapp.util.Resource
import kotlinx.coroutines.Dispatchers

class CoinDetailViewModel(private val repository: CoinRepository) : BaseViewModel() {

    fun getCoinDetail(id:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getCoinDetail(id)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Failure!"))
        }
    }

}