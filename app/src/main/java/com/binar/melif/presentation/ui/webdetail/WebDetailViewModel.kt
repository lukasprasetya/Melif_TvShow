package com.binar.melif.presentation.ui.webdetail

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.melif.base.wrapper.Resource

class WebDetailViewModel(private val intentData: Bundle) : ViewModel() {

    val urlResult = MutableLiveData<Resource<String>>()

    fun loadUrl() {
        urlResult.postValue(Resource.Loading())
        val url = intentData.getString(WebDetailActivity.EXTRAS_TV_SHOW_EPS_URL)
        url?.let {
            urlResult.postValue(Resource.Success(it))
        } ?: urlResult.postValue(Resource.Empty())
    }
}