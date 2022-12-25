package com.neqabty.recruitment.modules.personalinfo.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.recruitment.core.utils.AppUtils
import com.neqabty.recruitment.core.utils.Resource
import com.neqabty.recruitment.modules.engineer.domain.entity.engineerdata.EngineerEntity
import com.neqabty.recruitment.modules.engineer.domain.usecases.GetEngineer
import com.neqabty.recruitment.modules.engineer.domain.usecases.UpdateEngineer
import com.neqabty.recruitment.modules.personalinfo.domain.entity.maritalstatus.MaritalStatusEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.nationalities.NationalityEntity
import com.neqabty.recruitment.modules.personalinfo.domain.usecases.PersonalInfoUseCase
import com.neqabty.recruitment.modules.personalinfo.view.mappers.*
import com.neqabty.recruitment.modules.personalinfo.view.model.ItemUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(private val personalInfoUseCase: PersonalInfoUseCase,
private val getEngineer: GetEngineer, private val updateEngineer: UpdateEngineer): ViewModel() {

    val engineer = MutableLiveData<Resource<EngineerEntity>>()

    fun getEngineerInfo(id: String){
        viewModelScope.launch(Dispatchers.IO){
            engineer.postValue(Resource.loading(data = null))

            try {
                getEngineer.build().collect(){
                    engineer.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                engineer.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    fun updateEngineerInfo(id: String, body: Any){
        viewModelScope.launch(Dispatchers.IO){
            engineer.postValue(Resource.loading(data = null))

            try {
                updateEngineer.build(id, body).collect(){
                    engineer.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                engineer.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val maritalStatus = MutableLiveData<Resource<List<ItemUi>>>()

    fun getMaritalStatus(){
        viewModelScope.launch(Dispatchers.IO){
            maritalStatus.postValue(Resource.loading(data = null))

            try {
                personalInfoUseCase.getMaritalStatus().collect(){
                    maritalStatus.postValue(Resource.success(data = it.map { it.toMaritalStatusItemUi() }))
                }
            }catch (t: Throwable){
                maritalStatus.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val nationalities = MutableLiveData<Resource<List<ItemUi>>>()

    fun getNationalities(){
        viewModelScope.launch(Dispatchers.IO){
            nationalities.postValue(Resource.loading(data = null))

            try {
                personalInfoUseCase.getNationalities().collect(){
                    nationalities.postValue(Resource.success(data = it.map { it.toNationalityItemUi() }))
                }
            }catch (t: Throwable){
                nationalities.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }
    val countries = MutableLiveData<Resource<List<ItemUi>>>()

    fun getCountries(){
        viewModelScope.launch(Dispatchers.IO){
            countries.postValue(Resource.loading(data = null))

            try {
                personalInfoUseCase.getCountries().collect(){
                    countries.postValue(Resource.success(data = it.map { it.toCountryItemUi() }))
                }
            }catch (t: Throwable){
                countries.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }
    val governorates = MutableLiveData<Resource<List<ItemUi>>>()

    fun getGovernorates(){
        viewModelScope.launch(Dispatchers.IO){
            governorates.postValue(Resource.loading(data = null))

            try {
                personalInfoUseCase.getGovernorates().collect(){
                    governorates.postValue(Resource.success(data = it.map { it.toGovernmentItemUi() }))
                }
            }catch (t: Throwable){
                governorates.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }
    val universities = MutableLiveData<Resource<List<ItemUi>>>()

    fun getUniversities(){
        viewModelScope.launch(Dispatchers.IO){
            universities.postValue(Resource.loading(data = null))

            try {
                personalInfoUseCase.getUniversities().collect(){
                    universities.postValue(Resource.success(data = it.map { it.toUniversityItemUi() }))
                }
            }catch (t: Throwable){
                universities.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }
}