package com.binar.melif.uimodel

import androidx.annotation.StringRes
import com.binar.melif.data.network.model.TvShowResultModel

const val TV_SHOW_TYPE_HEADER = 0
const val TV_SHOW_TYPE_SECTION = 1

sealed class TvShowItem(val type: Int){
    class TvShowHeaderItem(val data: TvShowResultModel?): TvShowItem(TV_SHOW_TYPE_HEADER)
    class TvShowSectionItem(@StringRes val sectionName: Int, val data:
    List<TvShowResultModel>?): TvShowItem(
        TV_SHOW_TYPE_SECTION)
}