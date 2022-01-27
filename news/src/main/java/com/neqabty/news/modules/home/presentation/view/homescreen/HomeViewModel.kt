package com.neqabty.news.modules.home.presentation.view.homescreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.news.modules.home.domain.entity.NewsEntity
import com.neqabty.news.modules.home.domain.interactors.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getNewsUseCase: GetNewsUseCase) :
    ViewModel() {
    val news = MutableLiveData<List<NewsEntity>>()
    fun getNews() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getNewsUseCase.build().collect {
                    news.postValue(it)
                }
            }catch (e:Throwable){
                Log.e("",e.toString())
            }
        }
    }
}