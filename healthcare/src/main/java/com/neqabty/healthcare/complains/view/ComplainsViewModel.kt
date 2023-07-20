package com.neqabty.healthcare.complains.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.complains.domain.entity.ComplainEntity
import com.neqabty.healthcare.complains.domain.usecase.ComplainsUseCase
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ComplainsViewModel @Inject constructor(private val complainsUseCase: ComplainsUseCase): ViewModel() {

    val complainStatus = MutableLiveData<Resource<List<ComplainEntity>>>()
    fun getAllComplains(){
        complainStatus.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO){
            try {
                complainsUseCase.build().collect{
                    complainStatus.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                complainStatus.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }
}