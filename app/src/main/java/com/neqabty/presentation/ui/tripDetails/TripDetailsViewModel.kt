package com.neqabty.presentation.ui.tripDetails

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetTripDetails
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.TripUI
import com.neqabty.presentation.mappers.TripsEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TripDetailsViewModel @Inject constructor(private val getTripDetails: GetTripDetails) : BaseViewModel() {

    private val tripEntityUIMapper = TripsEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<TripDetailsViewState> = MutableLiveData()

    init {
        viewState.value = TripDetailsViewState()
    }

    fun getTripDetails(id: String) {
        viewState.value?.trip?.let {
            onTripDetailsReceived(it)
        } ?: addDisposable(getTripDetails.getTripDetails(id)
                .map {
                    it.let {
                        tripEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onTripDetailsReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onTripDetailsReceived(trip: TripUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                trip = trip)
        viewState.value = newViewState
    }
}
