package com.neqabty.recruitment.modules.home.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.recruitment.core.utils.AppUtils
import com.neqabty.recruitment.core.utils.Resource
import com.neqabty.recruitment.modules.home.domain.entity.ads.AdEntity
import com.neqabty.recruitment.modules.home.domain.entity.news.NewsEntity
import com.neqabty.recruitment.modules.home.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeUseCase: HomeUseCase): ViewModel() {

    val ads = MutableLiveData<Resource<List<AdEntity>>>()
    fun getAds(){
        viewModelScope.launch(Dispatchers.IO){
            ads.postValue(Resource.loading(data = null))
            try {
                homeUseCase.build().collect(){
                    ads.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                ads.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val news = MutableLiveData<Resource<List<NewsEntity>>>()
    fun getNews(){
        viewModelScope.launch(Dispatchers.IO){
            news.postValue(Resource.loading(data = null))
            try {
                homeUseCase.getNews().collect(){
                    news.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                news.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }
}