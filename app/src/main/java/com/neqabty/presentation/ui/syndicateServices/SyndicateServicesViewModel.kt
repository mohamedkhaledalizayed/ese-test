package com.neqabty.presentation.ui.syndicateServices

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.*
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.*
import com.neqabty.presentation.mappers.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SyndicateServicesViewModel @Inject constructor(
    private val getSyndicateServices: GetSyndicateServices,
    private val getAllServices: GetAllServices,
    private val inquireSyndicateServicesPayment: InquireSyndicateServicesPayment
) : BaseViewModel() {

    private val serviceEntityUIMapper = ServiceEntityUIMapper()
    private val serviceTypeEntityUIMapper = SyndicateServicesEntityUIMapper()
    private val syndicateServicesPaymentEntityUIMapper = SyndicateServicesPaymentEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<SyndicateServicesViewState> = MutableLiveData()

    init {
        viewState.value = SyndicateServicesViewState()
    }

    fun getSyndicateServices(userNumber: String) {
        getSyndicateServices.getSyndicateServices(userNumber)
            .flatMap {
                it.let {
                    serviceTypeEntityUIMapper.observable(it)
                }
            }.subscribe(
                {
                    onSyndicateServicesReceived(it)
                },
                { errorState.value = handleError(it) }
            )
    }

    fun getAllServices(typeID :Int) {
        getAllServices.getAllServices(typeID)
            .flatMap {
                it.let {
                    serviceEntityUIMapper.observable(it)
                }
            }.subscribe(
                {
//                            onServicesReceived(it.)
                },
                { errorState.value = handleError(it) }
            )
    }

    fun inquireSyndicateServicesPayment(mobileNumber: String, userNumber: String, userName: String, serviceID: Int, countryID: Int, paymentType: String, locationType: Int, address: String, mobile: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(inquireSyndicateServicesPayment.inquireSyndicateServicesPayment(mobileNumber, userNumber, userName, serviceID, countryID, paymentType, locationType, address, mobile)
            .map {
                it.let {
                    syndicateServicesPaymentEntityUIMapper.mapFrom(it)
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

    private fun onSyndicateServicesReceived(serviceTypes: SyndicateServicesUI) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            serviceTypes = serviceTypes.typesList,
            services = serviceTypes.servicesList)
        viewState.value = newViewState
    }

    private fun onSyndicateServicesPaymentReceived(syndicateServicesPaymentUI: SyndicateServicesPaymentUI) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            syndicateServicesPaymentUI = syndicateServicesPaymentUI)
        viewState.value = newViewState
    }
}
