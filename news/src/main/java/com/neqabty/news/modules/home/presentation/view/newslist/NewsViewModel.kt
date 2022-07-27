package com.neqabty.news.modules.home.presentation.view.newslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.news.core.utils.AppUtils
import com.neqabty.news.core.utils.Resource
import com.neqabty.news.modules.home.domain.entity.NewsEntity
import com.neqabty.news.modules.home.domain.interactors.GetNewsUseCase
import com.neqabty.news.modules.home.domain.interactors.GetSyndicateNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getSyndicateNewsUseCase: GetSyndicateNewsUseCase,
) : ViewModel() {

    val news = MutableLiveData<Resource<List<NewsEntity>>>()
    fun getAllNews() {
        news.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getNewsUseCase.build().collect {
                    news.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                news.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    fun getSyndicateNews(syndicateId: String) {
        news.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getSyndicateNewsUseCase.build(syndicateId).collect {
                    news.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                news.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}