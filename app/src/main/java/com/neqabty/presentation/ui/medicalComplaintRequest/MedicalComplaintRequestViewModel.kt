package com.neqabty.presentation.ui.medicalComplaintRequest

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.entities.AttachmentEntity
import com.neqabty.domain.usecases.*
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicalComplaintRequestViewModel @Inject constructor(
    val sendMedicalComplaintRequest: SendMedicalComplaintRequest
) : BaseViewModel() {
    private val medicalComplaintEntityUIMapper = MedicalComplaintEntityUIMapper()


    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MedicalComplaintRequestViewState> = MutableLiveData()

    init {
        viewState.value = MedicalComplaintRequestViewState(isLoading = false)
    }

    fun sendMedicalComplaintRequest(
        name: String,
        mobile: String,
        userNumber: String,
        benId: String,
        description: String,
        branchProfileId: String,
        mobileToken: String,
        serviceProviderId: String,
        letterTypeId: String,
        attachments: List<AttachmentEntity>) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(sendMedicalComplaintRequest.sendMedicalComplaintRequest(name, mobile, userNumber, benId, description, branchProfileId, mobileToken, serviceProviderId, letterTypeId, attachments)
            .map {
                medicalComplaintEntityUIMapper.mapFrom(it)
            }
            .subscribe(
                {
                    viewState.value = viewState.value?.copy(isLoading = false, medicalComplaintUI = it)
                },
                { errorState.value = handleError(it) }
            )
        )
    }
}
