package com.example.cryptocurrencyapp.di

import com.example.cryptocurrencyapp.data.repository.CoinRepository
import com.example.cryptocurrencyapp.ui.home.HomeViewModel
import com.example.cryptocurrencyapp.ui.home.coindetail.CoinDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

    val appModule = module {

        viewModel { HomeViewModel(get()) }
        viewModel { CoinDetailViewModel(get()) }

        single { CoinRepository(get()) }
    }