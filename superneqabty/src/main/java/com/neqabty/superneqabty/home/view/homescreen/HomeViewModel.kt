package com.neqabty.superneqabty.home.view.homescreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.ads.modules.home.domain.entity.AdEntity
import com.neqabty.ads.modules.home.domain.interactors.GetAllAdsUseCase
import com.neqabty.superneqabty.core.utils.AppUtils
import com.neqabty.superneqabty.core.utils.Resource
import com.neqabty.superneqabty.home.data.model.VerifyUserBody
import com.neqabty.superneqabty.home.data.model.valify.GetToken
import com.neqabty.superneqabty.home.data.model.verifyuser.VerifyUserResponse
import com.neqabty.superneqabty.home.domain.entity.NewsEntity
import com.neqabty.superneqabty.home.domain.interactors.GetNewsUseCase
import com.neqabty.superneqabty.home.domain.interactors.GetSyndicateNewsUseCase
import com.neqabty.superneqabty.home.domain.interactors.GetTokenUseCase
import com.neqabty.superneqabty.home.domain.interactors.VerifyUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getAllAdsUseCase: GetAllAdsUseCase,
    private val getSyndicateNewsUseCase: GetSyndicateNewsUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val verifyUserUseCase: VerifyUserUseCase
) : ViewModel() {
    val token = MutableLiveData<GetToken>()
    val news = MutableLiveData<List<NewsEntity>>()
    fun getToken() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getTokenUseCase.build().collect {
                    token.postValue(it)
                }
            } catch (e: Throwable) {
                Log.e("", e.toString())
            }
        }
    }

    val user = MutableLiveData<Resource<VerifyUserResponse>>()
    fun verifyUser(verifyUserBody: VerifyUserBody) {
        viewModelScope.launch(Dispatchers.IO) {
            user.postValue(Resource.loading(data = null))
            try {
                verifyUserUseCase.build(verifyUserBody).collect {
                    user.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                user.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
                Log.e("", e.toString())
            }
        }
    }
    fun getSyndicateNews(syndicateId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getSyndicateNewsUseCase.build(syndicateId).collect {
                    news.postValue(it)
                }
            } catch (e: Throwable) {
                Log.e("", e.toString())
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