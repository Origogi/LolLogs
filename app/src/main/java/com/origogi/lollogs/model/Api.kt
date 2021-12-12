package com.origogi.lollogs.model

import com.origogi.lollogs.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface OPGGApi {
    @GET("summoner/{summoner}")
    suspend fun getSummoner(
        @Path("summoner") name: String,
    ): SummonerResponse

    @GET("summoner/{summoner}/matches")
    suspend fun getMatches(
        @Path("summoner") name: String,
    ): MatchesResponse
}


object RetrofitService {
    val opggApi : OPGGApi by lazy {
        val client = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val logger = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
                addInterceptor(logger)
            }
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://codingtest.op.gg/api/")
            .client(
                client
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(OPGGApi::class.java)
    }
}
