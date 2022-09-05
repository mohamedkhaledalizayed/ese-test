package com.neqabty.recruitment.modules.personalinfo.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.recruitment.core.utils.AppUtils
import com.neqabty.recruitment.core.utils.Resource
import com.neqabty.recruitment.modules.personalinfo.domain.entity.engineerdata.EngineerEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.maritalstatus.MaritalStatusEntity
import com.neqabty.recruitment.modules.personalinfo.domain.usecases.PersonalInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(private val personalInfoUseCase: PersonalInfoUseCase): ViewModel() {

    val engineer = MutableLiveData<Resource<EngineerEntity>>()

    fun getEngineerInfo(id: String){
        viewModelScope.launch(Dispatchers.IO){
            engineer.postValue(Resource.loading(data = null))

            try {
                personalInfoUseCase.getEngineerData().collect(){
                    engineer.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                engineer.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val maritalStatus = MutableLiveData<Resource<List<MaritalStatusEntity>>>()

    fun getMaritalStatus(){
        viewModelScope.launch(Dispatchers.IO){
            maritalStatus.postValue(Resource.loading(data = null))

            try {
                personalInfoUseCase.getMaritalStatus().collect(){
                    maritalStatus.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                maritalStatus.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }
}