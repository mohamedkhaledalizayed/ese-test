package com.neqabty.presentation.ui.tripsReservation

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.entities.PersonEntity
import com.neqabty.domain.usecases.BookTrip
import com.neqabty.domain.usecases.PaymentInquiry
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.mappers.MemberEntityUIMapper
import java.io.File

import javax.inject.Inject

class TripReservationViewModel @Inject constructor(
    private val bookTrip: BookTrip,
    private val paymentInquiry: PaymentInquiry
) : BaseViewModel() {

    private val memberEntityUIMapper = MemberEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<TripReservationViewState> = MutableLiveData()

    init {
        viewState.value = TripReservationViewState(isLoading = false)
    }

//    fun paymentInquiry(number: String) {
//        viewState.value = viewState.value?.copy(isLoading = true)
//        addDisposable(paymentInquiry.paymentInquiry(number)
//                .map {
//                    it.let {
//                        memberEntityUIMapper.mapFrom(it)
//                    }
//                }.subscribe(
//                        { onValidationReceived(it) },
//                        {
//                            viewState.value = viewState.value?.copy(isLoading = false)
//                            errorState.value = it
//                        }
//                )
//        )
//    }

    private fun onValidationReceived(member: MemberUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                member = member)
        viewState.value = newViewState
    }

    fun bookTrip(
        mainSyndicateId: Int,
        userNumber: String,
        phone: String,
        tripID: Int,
        regimentID: Int,
        regimentDate: String,
        housingType: String,
        numChild: Int,
        ages: String,
        name: String,
        personsList: List<PersonEntity>,
        docsNumber: Int,
        peoplesNumber: Int,
        doc1: File?,
        doc2: File?,
        doc3: File?,
        doc4: File?
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(bookTrip.bookTrip(
                mainSyndicateId,
                userNumber,
                phone,
                tripID,
                regimentID,
                regimentDate,
                housingType,
                numChild,
                ages,
                name,
                personsList,
                docsNumber,
                peoplesNumber,
                doc1,
                doc2,
                doc3,
                doc4)
                .subscribe(
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                        },
                        { errorState.value = it }
                )
        )
    }
}
