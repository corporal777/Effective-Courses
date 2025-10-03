package com.examle.data.di

import android.content.Context
import com.examle.data.api.ApiService
import com.examle.data.api.NetworkClient
import org.koin.dsl.module

val remoteDataSourceModule = module {

    fun provideNetworkClient() = NetworkClient()
    fun provideApiService(context: Context, client: NetworkClient): ApiService = client.provideApi(context)

    single { provideNetworkClient() }
    single { provideApiService(get(), get()) }
}