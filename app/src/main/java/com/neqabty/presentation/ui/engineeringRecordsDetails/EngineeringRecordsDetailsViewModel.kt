package com.neqabty.presentation.ui.engineeringRecordsDetails

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.SendEngineeringRecordsInquiry
import com.neqabty.domain.usecases.SendEngineeringRecordsRequest
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.RegisteryEntityUIMapper
import java.io.File

import javax.inject.Inject

class EngineeringRecordsDetailsViewModel @Inject constructor(
    val sendEngineeringRecordsInquiry: SendEngineeringRecordsInquiry,
    val sendEngineeringRecordsRequest: SendEngineeringRecordsRequest
) : BaseViewModel() {
    private val registeryEntityUIMapper = RegisteryEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<EngineeringRecordsDetailsViewState> = MutableLiveData()

    init {
        viewState.value = EngineeringRecordsDetailsViewState()
    }

    fun requestEngineeringRecords(
        name: String,
        phone: String,
        typeId: String,
        mainSyndicate: String,
        userNumber: String,
        lastRenewYear: String,
        statusID: Int,
        isOwner: Int,
        docsNumber: Int,
        doc1: File?,
        doc2: File?,
        doc3: File?,
        doc4: File?,
        doc5: File?
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)

        addDisposable(sendEngineeringRecordsRequest.requestEngineeringRecords(name, phone, typeId, mainSyndicate, userNumber, lastRenewYear, statusID, isOwner, docsNumber, doc1, doc2, doc3, doc4, doc5)
                .subscribe(
                        {
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = true)
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                ))
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
                        { errorState.value = handleError(it) }
                ))
    }

    private fun onDataReceived() {
        if (viewState.value?.memberItem != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }
}
