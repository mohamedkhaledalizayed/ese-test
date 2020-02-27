package com.neqabty.presentation.ui.complaint

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.CreateComplaint
import com.neqabty.domain.usecases.GetAllAreas
import com.neqabty.domain.usecases.GetAllGoverns
import com.neqabty.domain.usecases.GetComplaintTypes
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.AreaEntityUIMapper
import com.neqabty.presentation.mappers.ComplaintTypeEntityUIMapper
import com.neqabty.presentation.mappers.GovernEntityUIMapper

import javax.inject.Inject

class ComplaintViewModel @Inject constructor(val getComplaintTypes: GetComplaintTypes, val createComplaint: CreateComplaint) : BaseViewModel() {
    private val complaintTypeEntityUIMapper = ComplaintTypeEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<ComplaintViewState> = MutableLiveData()

    init {
        viewState.value = ComplaintViewState(isLoading = true)
    }

    fun getTypes() {
        viewState.value = viewState.value?.copy(isLoading = true)
        val typesDisposable = getComplaintTypes.observable()
                .flatMap {
                    it.let {
                        complaintTypeEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(types = it)
                            onTypesReceived()
                        },
                        { errorState.value = it }
                )

        viewState.value?.types?.let {
            onTypesReceived()
        } ?: addDisposable(typesDisposable)

    }

    private fun onTypesReceived() {
        if (viewState.value?.types != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }

    fun createComplaint(name: String, phone: String, type: String, body: String, token: String){
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(createComplaint.createComplaint(name, phone, type, body, token)
                .subscribe(
                        { viewState.value = viewState.value?.copy(isLoading = false) },
                        { errorState.value = it }
                )
        )
    }
}
