package com.neqabty.presentation.ui.refundRequest

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.entities.AttachmentEntity
import com.neqabty.domain.usecases.SendRefundRequest
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.RefundEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RefundRequestViewModel @Inject constructor(
    val sendRefundRequest: SendRefundRequest
) : BaseViewModel() {
    private val refundEntityUIMapper = RefundEntityUIMapper()


    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<RefundRequestViewState> = MutableLiveData()

    init {
        viewState.value = RefundRequestViewState(isLoading = false)
    }

    fun sendRefundRequest(
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
        addDisposable(sendRefundRequest.sendRefundRequest(name, mobile, userNumber, benId, description, branchProfileId, mobileToken, serviceProviderId, letterTypeId, attachments)
            .map {
                refundEntityUIMapper.mapFrom(it)
            }
            .subscribe(
                {
                    viewState.value = viewState.value?.copy(isLoading = false, refundUI = it)
                },
                { errorState.value = handleError(it) }
            )
        )
    }
}
