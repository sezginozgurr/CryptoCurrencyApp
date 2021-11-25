package com.example.cryptocurrencyapp.ui.home.homecoin

import com.example.cryptocurrencyapp.data.model.CoinListResponse
import java.text.NumberFormat

data class CoinListItemViewState(val coins: CoinListResponse) {
    fun getName() = coins.name

    fun getImageUrl() = coins.image

    fun getCurrentPrice() = coins.current_price.toString() + " TRY"

    fun getPriceChange(): String {
        val nf: NumberFormat = NumberFormat.getInstance()
        nf.maximumFractionDigits = 2
        return nf.format(coins.price_change_24h) + "%"
    }

}