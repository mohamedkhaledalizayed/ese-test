package com.neqabty.healthcare.mega.home.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.mega.home.domain.entity.AdEntity
import com.neqabty.healthcare.mega.home.domain.interactors.HomeUseCase
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.domain.interactors.GetNewsUseCase
import com.neqabty.healthcare.news.domain.interactors.GetSyndicateNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val homeUseCase: HomeUseCase,
    private val getSyndicateNewsUseCase: GetSyndicateNewsUseCase
) : ViewModel() {

    val allNews = MutableLiveData<Resource<List<NewsEntity>>>()
    fun getAllNews() {
        allNews.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getNewsUseCase.build().collect {
                    allNews.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                allNews.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val syndicatesNews = MutableLiveData<Resource<List<NewsEntity>>>()
    fun getSyndicateNews(syndicateId: String) {
        syndicatesNews.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getSyndicateNewsUseCase.build(syndicateId).collect {
                    syndicatesNews.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                syndicatesNews.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val ads = MutableLiveData<List<AdEntity>>()
    fun getAds() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                homeUseCase.build().collect {
                    ads.postValue(it)
                }
            } catch (e: Throwable) {
                Log.e("", e.toString())
            }
        }
    }

    val complains = MutableLiveData<Resource<String>>()
    fun addComplain(mobile: String, email: String, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            complains.postValue(Resource.loading(data = null))
            try {
                homeUseCase.addComplain(mobile, email, message).collect {
                    complains.postValue(Resource.success(it))
                }
            } catch (e: Throwable) {
                complains.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}