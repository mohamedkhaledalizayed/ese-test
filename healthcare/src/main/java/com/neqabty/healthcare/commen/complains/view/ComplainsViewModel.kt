package com.neqabty.healthcare.commen.complains.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.commen.complains.domain.entity.getcomplain.ComplainEntity
import com.neqabty.healthcare.commen.complains.domain.usecases.AddComplainUseCase
import com.neqabty.healthcare.commen.complains.domain.usecases.GetComplainsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComplainsViewModel @Inject constructor(
    private val addComplainUseCase: AddComplainUseCase,
    private val complainsUseCase: GetComplainsUseCase
): ViewModel() {

    val complains = MutableLiveData<Resource<String>>()
    fun addComplain(mobile: String, email: String, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            complains.postValue(Resource.loading(data = null))
            try {
                addComplainUseCase.build(mobile, email, message).collect {
                    complains.postValue(Resource.success(it))
                }
            } catch (e: Throwable) {
                complains.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

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