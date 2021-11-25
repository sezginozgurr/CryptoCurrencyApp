package com.example.cryptocurrencyapp.di

import com.example.cryptocurrencyapp.BuildConfig
import com.example.cryptocurrencyapp.data.service.ApiService
import com.example.cryptocurrencyapp.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { retrofitClient<ApiService>(get()) }
    single { getRetrofit(get()) }
    single { httpClient() }

}

inline fun <reified T> retrofitClient(retrofit: Retrofit): T = retrofit.create(T::class.java)

fun httpClient(): OkHttpClient {
    return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = when {
            BuildConfig.DEBUG -> {
                HttpLoggingInterceptor.Level.BODY
            }
            else -> {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
    ).build()
}

fun getRetrofit(httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
}