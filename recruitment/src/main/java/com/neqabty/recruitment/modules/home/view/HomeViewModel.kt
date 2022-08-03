package com.neqabty.recruitment.modules.home.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.recruitment.core.utils.AppUtils
import com.neqabty.recruitment.core.utils.Resource
import com.neqabty.recruitment.modules.home.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeUseCase: HomeUseCase): ViewModel() {

    val res = MutableLiveData<Resource<String>>()
    fun recruitment(){
        viewModelScope.launch(Dispatchers.IO){
            res.postValue(Resource.loading(data = null))

            try {
                homeUseCase.recruitment().collect(){
                    res.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                res.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }
}