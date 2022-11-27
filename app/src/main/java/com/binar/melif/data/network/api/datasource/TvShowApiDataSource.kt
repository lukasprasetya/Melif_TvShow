package com.binar.melif.data.network.api.datasource

import com.binar.melif.BuildConfig
import com.binar.melif.data.network.model.TvShowModel
import com.binar.melif.data.network.service.TvShowApiService

interface TvShowApiDataSource {
    suspend fun getTopRatedTv(page:Int = 1) : TvShowModel

    suspend fun getPopularTv(page:Int = 1) : TvShowModel

    suspend fun getAiringTv() :TvShowModel
}

class TvShowDataSourceImpl(private val service: TvShowApiService): TvShowApiDataSource{
    override suspend fun getTopRatedTv(page: Int): TvShowModel {
        return service.getTopRatedTv(BuildConfig.API_KEY, page)
    }

    override suspend fun getPopularTv(page: Int): TvShowModel {
        return service.getPopularTv(BuildConfig.API_KEY, page)
    }

    override suspend fun getAiringTv(): TvShowModel{
        return service.getAiringTv(BuildConfig.API_KEY,1)
    }

}