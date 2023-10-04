package com.neqabty.healthcare.news.view.newsdetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.domain.interactors.GetNewsDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val getNewsDetailsUseCase: GetNewsDetailsUseCase
) : ViewModel() {
    val newsDetails = MutableLiveData<Resource<NewsEntity>>()
    fun getNewsDetails(newsId: Int) {
        newsDetails.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getNewsDetailsUseCase.build(newsId).collect {
                    newsDetails.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                newsDetails.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
//                Log.e("", e.toString())
            }
        }
    }
}