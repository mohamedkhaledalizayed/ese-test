package com.neqabty.healthcare.commen.onboarding.contact.view


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.CheckMemberEntity
import com.neqabty.healthcare.commen.onboarding.contact.domain.interactors.CheckMemberUseCase
import com.neqabty.healthcare.core.ui.BaseViewModel
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninDoneViewModel @Inject constructor(
    private val checkMemberUseCase: CheckMemberUseCase
) :
    BaseViewModel() {

    val checkMemberStatus = MutableLiveData<Resource<CheckMemberEntity>>()
    fun checkMemberStatus(nationalId: String){
        viewModelScope.launch(Dispatchers.IO){
            checkMemberStatus.postValue(Resource.loading(data = null))

            try {
                checkMemberUseCase.build(nationalId).collect(){
                    checkMemberStatus.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                checkMemberStatus.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }
}