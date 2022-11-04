package com.neqabty.healthcare.commen.landing.view


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.commen.ads.domain.entity.AdEntity
import com.neqabty.healthcare.commen.ads.domain.interactors.AdsUseCase
import com.neqabty.healthcare.commen.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.commen.syndicates.domain.interactors.GetSyndicateUseCase
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.domain.interactors.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val adsUseCase: AdsUseCase,
    private val getSyndicateUseCase: GetSyndicateUseCase
) :
    ViewModel() {

    val allNews = MutableLiveData<Resource<List<NewsEntity>>>()
    fun getAllNews() {
        allNews.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getNewsUseCase.build().collect {
                    allNews.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                allNews.postValue(
                    Resource.error(data = null, message = AppUtils()
                        .handleError(e)))
            }
        }
    }

    val ads = MutableLiveData<List<AdEntity>>()
    fun getAds() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                adsUseCase.build().collect {
                    ads.postValue( it)
                }
            } catch (e: Throwable) {
            }
        }
    }

    val syndicates = MutableLiveData<Resource<List<SyndicateEntity>>>()
    fun getSyndicates() {
        syndicates.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getSyndicateUseCase.build().collect {
                    syndicates.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                syndicates.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

}