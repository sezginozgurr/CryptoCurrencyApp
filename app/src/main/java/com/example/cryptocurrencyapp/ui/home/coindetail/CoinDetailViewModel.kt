package com.example.cryptocurrencyapp.ui.home.coindetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.cryptocurrencyapp.data.repository.CoinRepository
import com.example.cryptocurrencyapp.util.Resource
import kotlinx.coroutines.Dispatchers

class CoinDetailViewModel(private val repository: CoinRepository) : ViewModel() {

    private var _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private var _addFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val addFavorite: LiveData<Boolean> = _addFavorite

    private var _deleteFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val deleteFavorite: LiveData<Boolean> = _deleteFavorite


    var id = ""
    fun getCoinDetail() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getCoinDetail(id)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Failure!"))
        }
    }

}