package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.auth.logout.domain.interactors.LogoutUseCase
import com.neqabty.healthcare.commen.ads.domain.entity.AdEntity
import com.neqabty.healthcare.commen.ads.domain.interactors.AdsUseCase
import com.neqabty.healthcare.commen.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.commen.syndicates.domain.interactors.GetSyndicateUseCase
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.mega.home.domain.interactors.HomeUseCase
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.AboutEntity
import com.neqabty.healthcare.sustainablehealth.home.domain.interactors.GetHomeUseCase
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.domain.interactors.GetNewsUseCase
import com.neqabty.healthcare.sustainablehealth.home.domain.interactors.GetPackagesUseCase
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.packages.PackagesEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getPackagesUseCase: GetPackagesUseCase,
    private val adsUseCase: AdsUseCase,
    private val getSyndicateUseCase: GetSyndicateUseCase,
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    val aboutList = MutableLiveData<Resource<List<AboutEntity>>>()
    fun getAboutList() {
        viewModelScope.launch(Dispatchers.IO) {
            aboutList.postValue(Resource.loading(data = null))
            try {
                getHomeUseCase.build().collect {
                    aboutList.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                aboutList.postValue(
                    Resource.error(
                        data = null,
                        message = AppUtils().handleError(e)
                    )
                )
            }
        }
    }

    val logoutStatus = MutableLiveData<Resource<Boolean>>()
    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutStatus.postValue(Resource.loading(data = null))
            try {
                logoutUseCase.build().collect {
                    logoutStatus.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                logoutStatus.postValue(
                    Resource.error(
                        data = null,
                        message = AppUtils().handleError(e)
                    )
                )
            }
        }
    }

    val packages = MutableLiveData<Resource<List<PackagesEntity>>>()
    fun getPackages(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            packages.postValue(Resource.loading(data = null))
            try {
                getPackagesUseCase.build(code).collect {
                    packages.postValue(Resource.success(data = it))
                }
            }catch (e:Throwable){
                packages.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
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

}