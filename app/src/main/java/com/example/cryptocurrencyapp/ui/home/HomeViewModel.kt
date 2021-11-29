package com.example.cryptocurrencyapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.cryptocurrencyapp.data.repository.CoinRepository
import com.example.cryptocurrencyapp.util.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repository: CoinRepository) : ViewModel() {

    fun getCoinList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getCoinList()))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Failure!"))
        }
    }
}