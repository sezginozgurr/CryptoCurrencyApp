package com.example.cryptocurrencyapp.data.repository

import com.example.cryptocurrencyapp.data.service.ApiService

class CoinRepository(private val apiService: ApiService) {

    suspend fun getCoinList() = apiService.getCoinList()

    suspend fun getCoinDetail(id: String) = apiService.getCoinDetail(id)
}