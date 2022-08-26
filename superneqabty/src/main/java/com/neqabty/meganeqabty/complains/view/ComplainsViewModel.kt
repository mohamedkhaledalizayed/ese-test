package com.neqabty.meganeqabty.complains.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.core.utils.AppUtils
import com.neqabty.core.utils.Resource
import com.neqabty.meganeqabty.complains.domain.usecase.ComplainsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComplainsViewModel @Inject constructor(private val complainsUseCase: ComplainsUseCase): ViewModel() {

    val complainStatus = MutableLiveData<Resource<String>>()
    fun getAllComplains(){
        viewModelScope.launch(Dispatchers.IO){
            complainStatus.postValue(Resource.loading(data = null))

            try {
                complainsUseCase.build().collect(){
                    complainStatus.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                complainStatus.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }
}