package com.effective.networkmodule.api

import android.content.Context
import android.util.Log
import com.effective.networkmodule.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class NetworkClient {

    private fun provideGson(): Gson = GsonBuilder().serializeNulls().setLenient().create()

    private fun provideHttpClient(context: Context): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .cache(Cache(File(context.cacheDir, "http-cache"), 10 * 1024 * 1024))


        val logInterceptor = HttpLoggingInterceptor { message ->
            Log.e("REQUEST INFO", message)
        }
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(logInterceptor)

        return clientBuilder.build()
    }

    fun provideApiService(context: Context): ApiService {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(provideHttpClient(context))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(ApiService::class.java)
    }
}