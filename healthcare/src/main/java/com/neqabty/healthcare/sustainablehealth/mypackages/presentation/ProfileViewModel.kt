package com.neqabty.healthcare.sustainablehealth.mypackages.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.AddFollowerBody
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.addfollower.AddFollowerEntity
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.profile.ProfileEntity
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.relations.RelationEntityList
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.usecases.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
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
                userData.postValue(Resource.error(data = null, message = handleError(e)))
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

    val addFollower = MutableLiveData<Resource<AddFollowerEntity>>()

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


    fun handleError(throwable: Throwable): String {
        return if (throwable is HttpException) {
            when (throwable.code()) {
                400 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                401 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                403 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                404 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
                500 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
                else -> {
                    throwable.message()
                }
            }
        } else {
            throwable.message!!
        }
    }
}