package com.neqabty.presentation.ui.committees

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetCommitteesLookups
import com.neqabty.domain.usecases.SendCommitteesRequest
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.CommitteesLookupsEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CommitteesViewModel @Inject constructor(
        val getCommitteesLookups: GetCommitteesLookups,
        val sendCommitteesRequest: SendCommitteesRequest
) : BaseViewModel() {
    private val committeesLookupsEntityUIMapper = CommitteesLookupsEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<CommitteesViewState> = MutableLiveData()

    init {
        viewState.value = CommitteesViewState(isLoading = true)
    }

    fun getLookups() {
        viewState.value?.lookups?.let {
            onLookupsReceived()
        } ?: addDisposable(getCommitteesLookups.observable()
                .flatMap {
                    it.let {
                        committeesLookupsEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(lookups = it)
                            onLookupsReceived()
                        },
                        { errorState.value = handleError(it) }
                )
        )
    }

    private fun onLookupsReceived() {
        if (viewState.value?.lookups != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }

    fun sendCommitteesRequest(
        name: String,
        userNumber: String,
        mobile: String,
        email: String,
        nationalId: String,
        address: String,
        university: String,
        degree: String,
        maritalStatus: String,
        committeesIds: List<Int>,
        sectionId: Int,
        syndicateId: Int,
        department: String,
        section: String,
        currentJob: String,
        details: String,
        docsNumber: Int,
        doc1: File?,
        doc2: File?,
        doc3: File?) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(sendCommitteesRequest.sendCommitteesRequest(name, userNumber, mobile, email, nationalId, address, university, degree, maritalStatus, committeesIds, sectionId, syndicateId, department, section, currentJob, details, docsNumber, doc1, doc2, doc3)
            .subscribe(
                {
                    viewState.value = viewState.value?.copy(isLoading = false, message = it)
                },
                { errorState.value = handleError(it) }
            )
        )
    }
}
