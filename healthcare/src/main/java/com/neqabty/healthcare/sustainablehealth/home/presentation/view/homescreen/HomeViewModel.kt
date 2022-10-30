package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.AboutEntity
import com.neqabty.healthcare.sustainablehealth.home.domain.interactors.GetHomeUseCase
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.AdEntity
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.domain.interactors.GetNewsUseCase
import com.neqabty.healthcare.sustainablehealth.home.domain.interactors.GetAllAdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getHomeUseCase: GetHomeUseCase,
                                        private val getNewsUseCase: GetNewsUseCase,
                                        private val getAllAdsUseCase: GetAllAdsUseCase
) :
    ViewModel() {
    val aboutList = MutableLiveData<Resource<List<AboutEntity>>>()
    fun getAboutList() {
        viewModelScope.launch(Dispatchers.IO) {
            aboutList.postValue(Resource.loading(data = null))
            try {
                getHomeUseCase.build().collect {
                    aboutList.postValue(Resource.success(data = it))
                }
            }catch (e:Throwable){
                aboutList.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

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

    val ads = MutableLiveData<Resource<List<AdEntity>>>()
    fun getAds() {
        viewModelScope.launch(Dispatchers.IO) {
            ads.postValue(Resource.loading(data = null))
            try {
                getAllAdsUseCase.build().collect {
                    ads.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                ads.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

}