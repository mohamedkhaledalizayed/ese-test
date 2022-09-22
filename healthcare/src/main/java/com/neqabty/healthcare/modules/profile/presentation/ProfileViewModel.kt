package com.neqabty.healthcare.modules.profile.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.core.utils.AppUtils
import com.neqabty.core.utils.Resource
import com.neqabty.healthcare.modules.profile.data.model.AddFollowerBody
import com.neqabty.healthcare.modules.profile.domain.entity.profile.ProfileEntity
import com.neqabty.healthcare.modules.profile.domain.entity.relations.RelationEntityList
import com.neqabty.healthcare.modules.profile.domain.usecases.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getProfileUseCase: GetProfileUseCase): ViewModel() {

    val userData = MutableLiveData<Resource<ProfileEntity>>()

    fun getProfile(phone: String){
        viewModelScope.launch(Dispatchers.IO){
            userData.postValue(Resource.loading(data = null))

            try {
                getProfileUseCase.build(phone)
                    .collect { userData.postValue(Resource.success(data = it)) }
            }catch (e: Throwable){
                userData.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val relations = MutableLiveData<Resource<List<RelationEntityList>>>()

    fun getRelations(){
        viewModelScope.launch(Dispatchers.IO){
            relations.postValue(Resource.loading(data = null))

            try {
                getProfileUseCase.build()
                    .collect { relations.postValue(Resource.success(data = it)) }
            }catch (e: Throwable){
                relations.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val addFollower = MutableLiveData<Resource<String>>()

    fun addFollower(addFollowerBody: AddFollowerBody){
        viewModelScope.launch(Dispatchers.IO){
            addFollower.postValue(Resource.loading(data = null))

            try {
                getProfileUseCase.build(addFollowerBody).collect {
                    addFollower.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                addFollower.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val followerStatus = MutableLiveData<Resource<Boolean>>()

    fun deleteFollower(followerId: Int, subscriberId: String){
        viewModelScope.launch(Dispatchers.IO){
            followerStatus.postValue(Resource.loading(data = null))

            try {
                getProfileUseCase.build(followerId, subscriberId).collect {
                    followerStatus.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                followerStatus.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}