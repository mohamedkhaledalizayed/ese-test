package com.neqabty.healthcare.modules.home.presentation.view.homescreen


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.modules.home.domain.entity.about.AboutEntity
import com.neqabty.healthcare.modules.home.domain.interactors.GetHomeUseCase
import com.neqabty.superneqabty.home.domain.entity.NewsEntity
import com.neqabty.superneqabty.home.domain.interactors.GetNewsUseCase
import com.neqabty.superneqabty.home.domain.interactors.GetSyndicateNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getHomeUseCase: GetHomeUseCase,
                                        private val getNewsUseCase: GetNewsUseCase,
                                        private val getSyndicateNewsUseCase: GetSyndicateNewsUseCase
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
                    Resource.error(data = null, message = com.neqabty.superneqabty.core.utils.AppUtils()
                        .handleError(e)))
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
                syndicatesNews.postValue(
                    Resource.error(data = null, message = com.neqabty.superneqabty.core.utils.AppUtils()
                        .handleError(e)))
            }
        }
    }

}