package com.neqabty.healthcare.commen.onboarding.contact.view


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.CheckMemberEntity
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.CreateOCREntity
import com.neqabty.healthcare.commen.onboarding.contact.domain.interactors.CheckMemberUseCase
import com.neqabty.healthcare.commen.onboarding.contact.domain.interactors.CreateOCRUseCase
import com.neqabty.healthcare.core.ui.BaseViewModel
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class UploadIdBackViewModel @Inject constructor(
    private val createOCRUseCase: CreateOCRUseCase,
    private val checkMemberUseCase: CheckMemberUseCase
) :
    BaseViewModel() {

    val createOcrStatus = MutableLiveData<Resource<CreateOCREntity>>()
    fun createOcr(frontPoto: MultipartBody.Part?, backPhoto: MultipartBody.Part?, nationalId: String, mobile: String) {
        createOcrStatus.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                createOCRUseCase.build(frontPoto, backPhoto, nationalId, mobile).collect {
                    createOcrStatus.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                createOcrStatus.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }

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