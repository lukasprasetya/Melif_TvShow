package com.binar.melif.data.repository

import com.binar.melif.base.BaseRepository
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.data.network.api.datasource.TvShowApiDataSource
import com.binar.melif.data.network.model.TvShowModel

interface TvShowRepository {
    suspend fun getTopRatedTvList(page:Int = 1) : Resource<TvShowModel>

    suspend fun getPopularTvList(page:Int = 1) : Resource<TvShowModel>

    suspend fun getAiringTvList() : Resource<TvShowModel>
}

class TvShowRepositoryImpl(private val networkDataSource: TvShowApiDataSource): TvShowRepository,BaseRepository(){
    override suspend fun getTopRatedTvList(page: Int): Resource<TvShowModel> {
        return doNetworkCall { networkDataSource.getTopRatedTv(page)  }
    }

    override suspend fun getPopularTvList(page: Int): Resource<TvShowModel> {
        return doNetworkCall { networkDataSource.getPopularTv(page)  }
    }

    override suspend fun getAiringTvList(): Resource<TvShowModel> {
        return doNetworkCall { networkDataSource.getAiringTv()  }
    }
}