package com.neqabty.presentation.ui.engineeringRecordsDetails

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetUserRegistered
import com.neqabty.domain.usecases.SendEngineeringRecordsRequest
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.DoctorEntityUIMapper
import com.neqabty.presentation.util.PreferencesHelper
import java.io.File

import javax.inject.Inject

class EngineeringRecordsDetailsViewModel @Inject constructor(val sendEngineeringRecordsRequest: SendEngineeringRecordsRequest) : BaseViewModel() {

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
            doc1: File?
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)

        addDisposable(sendEngineeringRecordsRequest.requestEngineeringRecords(name, phone, typeId, mainSyndicate, userNumber, lastRenewYear, statusID, isOwner, docsNumber, doc1)
                .subscribe(
                        {
                            viewState.value = viewState.value?.copy(isLoading = false, isSuccessful = true)
                        },
                        { requestEngineeringRecords(name, phone, typeId, mainSyndicate, userNumber, lastRenewYear, statusID, isOwner, docsNumber, doc1) }
                ))
    }
}
