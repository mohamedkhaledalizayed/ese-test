package com.neqabty.presentation.ui.complaint

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.CreateComplaint
import com.neqabty.domain.usecases.GetComplaintSubTypes
import com.neqabty.domain.usecases.GetComplaintTypes
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.ComplaintTypeEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ComplaintViewModel @Inject constructor(
        val getComplaintTypes: GetComplaintTypes,
        val getComplaintSubTypes: GetComplaintSubTypes,
    val createComplaint: CreateComplaint
) : BaseViewModel() {
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
                            getSubTypes()
                        },
                        { errorState.value = handleError(it) }
                )
    }

    fun getSubTypes() {
        viewState.value = viewState.value?.copy(isLoading = true)
        val subTypesDisposable = getComplaintSubTypes.getComplaintsSubTypes(viewState.value?.types!![0].id.toString())
                .flatMap {
                    it.let {
                        complaintTypeEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(subTypes = it)
                            onSubTypesReceived()
                        },
                        { errorState.value = handleError(it) }
                )

        viewState.value?.subTypes?.let {
            onSubTypesReceived()
        } ?: addDisposable(subTypesDisposable)
    }

    private fun onSubTypesReceived() {
        if (viewState.value?.types != null && viewState.value?.subTypes != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }

    fun createComplaint(name: String, phone: String, catId: String, subCatId: String, body: String, token: String, memberNumber: String,
                        docsNumber: Int,
                        doc1: File?,
                        doc2: File?,
                        doc3: File?,
                        doc4: File?) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(createComplaint.createComplaint(name, phone, catId, subCatId, body, token, memberNumber, docsNumber, doc1, doc2, doc3, doc4)
                .subscribe(
                        { viewState.value = viewState.value?.copy(isLoading = false, message = "success") },
                        { errorState.value = handleError(it) }
                )
        )
    }
}
