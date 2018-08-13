package com.reigndesign.test.network

import com.reigndesign.test.models.Result

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("search_by_date?query=android")
    fun getProducts(): Call<Result>
}