package com.binar.melif.data.network.service

import com.binar.melif.BuildConfig
import com.binar.melif.data.network.model.TvShowModel
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface TvShowApiService {

    @GET("tv/top_rated")
    suspend fun getTopRatedTv(
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1
        ): TvShowModel

    @GET("tv/popular")
    suspend fun getPopularTv(
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1
        ): TvShowModel

    @GET("tv/airing_today")
    suspend fun getAiringTv(
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1):TvShowModel

    companion object {
        @JvmStatic
        operator fun invoke(chuckerInterceptor: ChuckerInterceptor): TvShowApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chuckerInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_MELIF)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(TvShowApiService::class.java)
        }
    }
}