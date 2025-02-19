package com.example.getupdated.data.network

import com.example.getupdated.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetUpdatedApiService {

    @GET("svc/mostpopular/v2/viewed/{period}.json")
    suspend fun getRecentArticles(
        @Path("period") period: Int,
        @Query("api-key") apiKey: String = BuildConfig.NY_TIMES_API_KEY
    ): Response<GetUpdatedResponse>
}
