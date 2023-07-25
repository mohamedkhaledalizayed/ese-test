package com.neqabty.healthcare.news.view.newslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.domain.interactors.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase) : ViewModel() {

    val news = MutableLiveData<Resource<List<NewsEntity>>>()
    fun getAllNews(code: String) {
        news.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getNewsUseCase.build(code).collect {
                    news.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                news.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}