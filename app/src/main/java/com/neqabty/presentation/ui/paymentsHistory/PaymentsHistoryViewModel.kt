package com.neqabty.presentation.ui.paymentsHistory

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetPaymentHistory
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.PaymentHistoryUI
import com.neqabty.presentation.mappers.PaymentHistoryEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentsHistoryViewModel @Inject constructor(private val getPaymentHistory: GetPaymentHistory) : BaseViewModel() {

    private val paymentHistoryEntityUIMapper = PaymentHistoryEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<PaymentsHistoryViewState> = MutableLiveData()

    init {
        viewState.value = PaymentsHistoryViewState()
    }

    fun getHistory(userNumber: String) {
        viewState.value?.payments?.let {
            onPaymentsReceived(it)
        } ?: addDisposable(getPaymentHistory.getPaymentHistory(userNumber)
                .flatMap {
                    it.let {
                        paymentHistoryEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onPaymentsReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onPaymentsReceived(payments: List<PaymentHistoryUI>) {
        val paymentsViewState = viewState.value?.copy(
                isLoading = false,
                payments = payments)
        viewState.value = paymentsViewState
    }
}
