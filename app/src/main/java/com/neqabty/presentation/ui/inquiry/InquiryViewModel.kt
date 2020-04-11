package com.neqabty.presentation.ui.inquiry

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAllServices
import com.neqabty.domain.usecases.PaymentInquiry
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.entities.ServiceUI
import com.neqabty.presentation.mappers.MemberEntityUIMapper
import com.neqabty.presentation.mappers.ServiceEntityUIMapper

import javax.inject.Inject

class InquiryViewModel @Inject constructor(private val getAllServices: GetAllServices, private val paymentInquiry: PaymentInquiry) : BaseViewModel() {

    private val memberEntityUIMapper = MemberEntityUIMapper()
    private val serviceEntityUIMapper = ServiceEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<InquiryViewState> = MutableLiveData()

    init {
        viewState.value = InquiryViewState()
    }


    fun getAllServices() {
        viewState.value?.services?.let {
            onServicesReceived(it)
        } ?: getAllServices.observable()
                .flatMap {
                    it.let {
                        serviceEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            onServicesReceived(it)
                        },
                        { errorState.value = it }
                )
    }

    fun paymentInquiry(number: String, serviceID: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(paymentInquiry.paymentInquiry(number, serviceID, "", "")
                .map {
                    it.let {
                        memberEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onInquiryReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
                        }
                )
        )
    }

    private fun onServicesReceived(services: List<ServiceUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                services = services)
        viewState.value = newViewState
    }

    private fun onInquiryReceived(member: MemberUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                member = member)
        viewState.value = newViewState
    }
}
