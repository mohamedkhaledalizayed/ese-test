package com.neqabty.presentation.ui.engineeringRecordsInquiry

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.SendEngineeringRecordsInquiry
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.RegisteryEntityUIMapper
import javax.inject.Inject

class EngineeringRecordsInquiryViewModel @Inject constructor(
    val sendEngineeringRecordsInquiry: SendEngineeringRecordsInquiry
) : BaseViewModel() {

    private val registeryEntityUIMapper = RegisteryEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<EngineeringRecordsInquiryViewState> = MutableLiveData()

    init {
        viewState.value = EngineeringRecordsInquiryViewState()
    }

    fun sendEngineeringRecordsInquiry(
        userNumber: String
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)

        addDisposable(sendEngineeringRecordsInquiry.inquireEngineeringRecords(userNumber)
                .flatMap {
                    it.let {
                        registeryEntityUIMapper.observable(it)
                    }
                }
                .subscribe(
                        {
                            viewState.value = viewState.value?.copy(memberItem = it)
                            onDataReceived()
                        },
                        { errorState.value = it }
                ))
    }

    private fun onDataReceived() {
        if (viewState.value?.memberItem != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }
}
