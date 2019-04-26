package com.watermark.community_app.communityapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.contentful.java.cda.CDAArray
import com.watermark.community_app.communityapp.ContentManager

class HomeFragmentViewModel : ViewModel() {

//    private val tableCdaArrayLiveData = MutableLiveData<CDAArray>()
//    private val questionsCdaArrayLiveData = MutableLiveData<CDAArray>()
//    private val pantryCdaArrayLiveData = MutableLiveData<CDAArray>()
//    private val shelfCdaArrayLiveData = MutableLiveData<CDAArray>()

    init {

    }

}

class HomeFragmentViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeFragmentViewModel::class.java)) {
            HomeFragmentViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}