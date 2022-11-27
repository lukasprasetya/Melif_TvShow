package com.binar.melif.data.network.model

import com.google.gson.annotations.SerializedName

data class TvShowModel(
    @SerializedName("results")
    val results: List<TvShowResultModel>
)

data class TvShowResultModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("name")
    val name: String
)