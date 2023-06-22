package com.neqabty.healthcare.mega.home.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.auth.logout.domain.interactors.LogoutUseCase
import com.neqabty.healthcare.commen.ads.domain.entity.AdEntity
import com.neqabty.healthcare.commen.ads.domain.interactors.AdsUseCase
import com.neqabty.healthcare.commen.clinido.domain.entity.ClinidoEntity
import com.neqabty.healthcare.commen.clinido.domain.usecases.ClinidoUseCase
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.domain.interactors.GetNewsUseCase
import com.neqabty.healthcare.news.domain.interactors.GetSyndicateNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getSyndicateNewsUseCase: GetSyndicateNewsUseCase,
    private val adsUseCase: AdsUseCase,
    private val clinidoUseCase: ClinidoUseCase,
    private val logoutUseCase: LogoutUseCase
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
                adsUseCase.build().collect {
                    ads.postValue(it)
                }
            } catch (e: Throwable) {
                Log.e("", e.toString())
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

    val clinidoUrl = MutableLiveData<Resource<ClinidoEntity>>()
    fun getUrl(phone: String, type: String, name: String, entityCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            clinidoUrl.postValue(Resource.loading(data = null))
            try {
                clinidoUseCase.build(phone, type, name, entityCode).collect {
                    clinidoUrl.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                clinidoUrl.postValue(
                    Resource.error(
                        data = null,
                        message = handleError(e)
                    )
                )
            }
        }
    }

    fun handleError(throwable: Throwable): String {
        return if (throwable is HttpException) {
            when (throwable.code()) {
                400 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                401 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                403 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                404 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
                500 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
                else -> {
                    throwable.message!!
                }
            }
        } else {
            throwable.message!!
        }
    }
}