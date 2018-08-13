package com.reigndesign.test.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private var retrofit: Retrofit? = null
    private const val BASE_URL = "http://hn.algolia.com/api/v1/"

    val instance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(MoshiConverterFactory.create())
                        .build()
            }
            return retrofit
        }
}