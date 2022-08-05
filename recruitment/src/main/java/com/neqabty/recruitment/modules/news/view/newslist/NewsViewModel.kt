package com.neqabty.recruitment.modules.news.view.newslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.recruitment.core.utils.AppUtils
import com.neqabty.recruitment.core.utils.Resource
import com.neqabty.recruitment.modules.news.domain.entity.NewsEntity
import com.neqabty.recruitment.modules.news.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsUseCase: NewsUseCase): ViewModel() {

    val news = MutableLiveData<Resource<List<NewsEntity>>>()
    fun getNews(){
        viewModelScope.launch(Dispatchers.IO){
            news.postValue(Resource.loading(data = null))
            try {
                newsUseCase.build().collect(){
                    news.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                news.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }
}