package com.neqabty.presentation.ui.syndicateServicesDetails

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.AddSyndicateServicesPaymentRequest
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.SyndicateServicesPaymentRequestUI
import com.neqabty.presentation.mappers.SyndicateServicesPaymentRequestEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SyndicateServicesDetailsViewModel @Inject constructor(
    private val addSyndicateServicesRequest: AddSyndicateServicesPaymentRequest
) : BaseViewModel() {

    private val syndicateServicesPaymentRequestEntityUIMapper = SyndicateServicesPaymentRequestEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<SyndicateServicesDetailsViewState> = MutableLiveData()

    init {
        viewState.value = SyndicateServicesDetailsViewState()
    }



    fun addSyndicateServicesPaymentRequest(mobileNumber: String, userNumber: String, userName: String, serviceID: Int, countryID: Int, paymentType: String, paymentGatewayId: Int, locationType: Int, address: String, mobile: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(addSyndicateServicesRequest.addSyndicateServicesPaymentRequest(mobileNumber, userNumber, userName, serviceID, countryID, paymentType, paymentGatewayId, locationType, address, mobile)
            .map {
                it.let {
                    syndicateServicesPaymentRequestEntityUIMapper.mapFrom(it)
                }
            }.subscribe(
                {
                    onSyndicateServicesPaymentReceived(it)
                },
                {
                    viewState.value = viewState.value?.copy(isLoading = false)
                    errorState.value = handleError(it)
                }
            )
        )
    }

    private fun onSyndicateServicesPaymentReceived(syndicateServicesPaymentRequestUI: SyndicateServicesPaymentRequestUI) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            syndicateServicesPaymentRequestUI = syndicateServicesPaymentRequestUI)
        viewState.value = newViewState
    }
}
