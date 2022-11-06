package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.commen.ads.domain.entity.AdEntity
import com.neqabty.healthcare.commen.ads.domain.interactors.AdsUseCase
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.mega.home.domain.interactors.HomeUseCase
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.AboutEntity
import com.neqabty.healthcare.sustainablehealth.home.domain.interactors.GetHomeUseCase
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.domain.interactors.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getHomeUseCase: GetHomeUseCase) : ViewModel() {
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

}