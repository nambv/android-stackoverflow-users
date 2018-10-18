package com.nambv.android_stackoverflow.data.remote

import com.google.gson.GsonBuilder
import com.nambv.android_stackoverflow.service.ApiEndpoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private val httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request())
                        .newBuilder()
                        .header("Accept", "application/json;charset=utf-8")
                        .header("Accept-Language", "en")
                        .build()
            }
            .build()

    private val loggingClient = OkHttpClient.Builder()
            .addInterceptor(
                    HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    private val gson = GsonBuilder().create()

    val apiClient: Retrofit = Retrofit.Builder()
            .baseUrl(ApiEndpoint.BASE_URL)
            .client(httpClient)
            .client(loggingClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
}
