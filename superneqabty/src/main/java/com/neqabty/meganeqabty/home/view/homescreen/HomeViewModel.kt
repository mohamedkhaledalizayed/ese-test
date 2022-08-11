package com.neqabty.meganeqabty.home.view.homescreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.core.utils.AppUtils
import com.neqabty.core.utils.Resource
import com.neqabty.meganeqabty.home.domain.entity.AdEntity
import com.neqabty.meganeqabty.home.domain.interactors.GetAllAdsUseCase
import com.neqabty.news.modules.home.domain.entity.NewsEntity
import com.neqabty.news.modules.home.domain.interactors.GetNewsUseCase
import com.neqabty.news.modules.home.domain.interactors.GetSyndicateNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getAllAdsUseCase: GetAllAdsUseCase,
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
                getAllAdsUseCase.build().collect {
                    ads.postValue(it)
                }
            } catch (e: Throwable) {
                Log.e("", e.toString())
            }
        }
    }
}