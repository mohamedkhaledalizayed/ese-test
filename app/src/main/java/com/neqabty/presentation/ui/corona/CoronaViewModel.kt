package com.neqabty.presentation.ui.corona

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.CreateCoronaRequest
import com.neqabty.domain.usecases.GetAllSyndicates
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.presentation.mappers.SyndicateEntityUIMapper
import java.io.File
import javax.inject.Inject

class CoronaViewModel @Inject constructor(
    val createCoronaRequest: CreateCoronaRequest,
    private val getAllSyndicates: GetAllSyndicates
) : BaseViewModel() {

    private val syndicateEntityUIMapper = SyndicateEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<CoronaViewState> = MutableLiveData()

    init {
        viewState.value = CoronaViewState(isLoading = true)
        getSyndicates()
    }

    fun getSyndicates() {
        viewState.value?.syndicates?.let {
            onSyndicatesReceived(it)
        } ?: addDisposable(getAllSyndicates.observable()
                .flatMap {
                    it.let {
                        syndicateEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onSyndicatesReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
                        }
                )
        )
    }

    private fun onSyndicatesReceived(syndicates: List<SyndicateUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                syndicates = syndicates
        )
        viewState.value = newViewState
    }

    fun createRequest(
        userNumber: String,
        phone: String,
        syndicateID: Int,
        name: String,
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
        doc5: File?
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(createCoronaRequest.createCoronaRequest(userNumber, phone, syndicateID, name, type, job, work, treatmentDestination, treatmentDestinationAddress, family, injury, docsNumber, doc1, doc2, doc3, doc4, doc5)
                .subscribe(
                        { viewState.value = viewState.value?.copy(isLoading = false, message = "success") },
                        { errorState.value = it }
                )
        )
    }
}
