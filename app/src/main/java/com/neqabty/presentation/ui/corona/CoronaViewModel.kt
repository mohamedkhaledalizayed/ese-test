package com.neqabty.presentation.ui.corona

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.CreateCoronaRequest
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import java.io.File
import javax.inject.Inject

class CoronaViewModel @Inject constructor(val createCoronaRequest: CreateCoronaRequest) : BaseViewModel() {
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<CoronaViewState> = MutableLiveData()

    init {
        viewState.value = CoronaViewState(isLoading = false)
    }

    fun createRequest(userNumber: String,
                      phone: String,
                      type: String,
                      job: String,
                      work: String,
                      treatmentDestination: String,
                      treatmentDestinationAddress: String,
                      family: Int,
                      injury: String,
                      docsNumber: Int,
                      doc1: File?,
                      doc2: File?,
                      doc3: File?,
                      doc4: File?,
                      doc5: File?) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(createCoronaRequest.createCoronaRequest(userNumber, phone, type, job, work, treatmentDestination, treatmentDestinationAddress, family, injury, docsNumber, doc1, doc2, doc3, doc4, doc5)
                .subscribe(
                        { viewState.value = viewState.value?.copy(isLoading = false, message = "success") },
                        { errorState.value = it }
                )
        )
    }
}
