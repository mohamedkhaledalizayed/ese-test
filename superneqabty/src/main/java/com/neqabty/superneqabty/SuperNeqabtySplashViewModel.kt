package com.neqabty.superneqabty

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.ads.modules.home.domain.entity.AdEntity
import com.neqabty.ads.modules.home.domain.interactors.GetAllAdsUseCase
import com.neqabty.ads.modules.home.domain.interactors.GetSyndicateAds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuperNeqabtySplashViewModel @Inject constructor(
    private val getAllAdsUseCase: GetAllAdsUseCase,
    private val getSyndicateAds: GetSyndicateAds
) : ViewModel() {
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

    fun getSyndicateAds(syndicateId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getSyndicateAds.build(syndicateId).collect {
                    ads.postValue(it)
                }
            }catch (e:Throwable){
                Log.e("", e.toString())
            }
        }
    }
}