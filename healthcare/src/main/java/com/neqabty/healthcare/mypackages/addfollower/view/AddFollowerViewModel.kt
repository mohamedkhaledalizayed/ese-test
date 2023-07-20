package com.neqabty.healthcare.mypackages.addfollower.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.mypackages.addfollower.data.model.AddFollowerBody
import com.neqabty.healthcare.mypackages.addfollower.domain.entity.AddFollowerEntity
import com.neqabty.healthcare.mypackages.addfollower.domain.usecases.AddFollowerUseCase
import com.neqabty.healthcare.relation.domain.entity.RelationEntityList
import com.neqabty.healthcare.relation.domain.usecases.RelationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AddFollowerViewModel @Inject constructor(private val addFollowerUseCase: AddFollowerUseCase,
                                               private val relationUseCase: RelationUseCase
): ViewModel() {

    val relations = MutableLiveData<Resource<List<RelationEntityList>>>()
    fun getRelations(){
        viewModelScope.launch(Dispatchers.IO){
            relations.postValue(Resource.loading(data = null))

            try {
                relationUseCase.build()
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
                addFollowerUseCase.build(addFollowerBody).collect {
                    addFollower.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                addFollower.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
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