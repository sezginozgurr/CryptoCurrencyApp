package com.example.cryptocurrencyapp.di

import com.example.cryptocurrencyapp.data.repository.CoinRepository
import org.koin.dsl.module

    val appModule = module {
        single { CoinRepository(get()) }
    }