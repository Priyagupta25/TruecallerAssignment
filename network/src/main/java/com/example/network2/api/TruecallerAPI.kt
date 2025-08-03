package com.example.network2.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TruecallerAPI {

    @GET("life-at-truecaller/{articleName}")
    suspend fun fetchWebContent(@Path("articleName") articleName: String = "life-as-an-android-engineer"): Response<String>
}