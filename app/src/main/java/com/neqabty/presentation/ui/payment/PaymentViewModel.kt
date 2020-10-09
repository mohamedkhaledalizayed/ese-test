package com.neqabty.presentation.ui.payment

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetTransactionHash
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.presentation.mappers.SyndicateEntityUIMapper

import javax.inject.Inject

class PaymentViewModel @Inject constructor(private val getTransactionHash: GetTransactionHash) :
    BaseViewModel() {

    private val syndicateEntityUIMapper = SyndicateEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<PaymentViewState> = MutableLiveData()

    init {
        viewState.value = PaymentViewState()
    }

    fun getSyndicates() {
        viewState.value?.hash?.let {
//            onTransactionHashReceived(it)
        } ?: addDisposable(getTransactionHash.observable()
            .flatMap {
                it.let {
                    syndicateEntityUIMapper.observable(it)
                }
            }.subscribe(
                { onTransactionHashReceived(it) },
                {
                    viewState.value = viewState.value?.copy(isLoading = false)
                    errorState.value = handleError(it)
                }
            )
        )
    }

    private fun onTransactionHashReceived(syndicates: List<SyndicateUI>) {
//        val newViewState = viewState.value?.copy(
//            isLoading = false,
//            syndicates = syndicates
//        )
//        viewState.value = newViewState
    }
}
