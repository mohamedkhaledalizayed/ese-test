package com.neqabty.presentation.ui.syndicates

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAllSyndicates
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.presentation.mappers.SyndicateEntityUIMapper
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class SyndicatesViewModel @Inject constructor(private val getAllSyndicates: GetAllSyndicates) :
    BaseViewModel() {

    private val syndicateEntityUIMapper = SyndicateEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<SyndicatesViewState> = MutableLiveData()

    init {
        viewState.value = SyndicatesViewState()
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
}
