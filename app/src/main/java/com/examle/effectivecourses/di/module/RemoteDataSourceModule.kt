package com.examle.effectivecourses.di.module

import android.content.Context
import com.effective.networkmodule.api.ApiService
import com.effective.networkmodule.api.NetworkClient
import org.koin.dsl.module

val remoteDataSourceModule = module {

    fun provideNetworkClient() =  NetworkClient()
    fun provideApiService(context: Context, networkClient: NetworkClient): ApiService {
        return networkClient.provideApiService(context)
    }

    single { provideNetworkClient() }
    single { provideApiService(get(), get()) }
}