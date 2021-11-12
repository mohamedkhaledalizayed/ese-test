package com.neqabty.presentation.ui.payment

import androidx.lifecycle.MutableLiveData
import com.neqabty.data.api.WebService
import com.neqabty.data.api.requests.SyndicateRequest
import com.neqabty.domain.usecases.GetTransactionHash
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.di.DI
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.presentation.mappers.SyndicateEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PaymentViewModel @Inject constructor(
        private val getTransactionHash: GetTransactionHash) : BaseViewModel() {

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

//    fun setPaid(username: String) {
//        api.setPaid(SyndicateRequest(username)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
//                {
//
//                    viewState.value = viewState.value?.copy(isLoading = false, hash = "123")
//                },
//                { errorState.value = handleError(it) }
//        )
//    }

    private fun onTransactionHashReceived(syndicates: List<SyndicateUI>) {
//        val newViewState = viewState.value?.copy(
//            isLoading = false,
//            syndicates = syndicates
//        )
//        viewState.value = newViewState
    }
}
