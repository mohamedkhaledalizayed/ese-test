package com.neqabty.presentation.ui.trips

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAllTrips
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.TripUI
import com.neqabty.presentation.mappers.TripsEntityUIMapper
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class TripsViewModel @Inject constructor(private val getAllTrips: GetAllTrips) : BaseViewModel() {

    private val tripsEntityUIMapper = TripsEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<TripsViewState> = MutableLiveData()

    init {
        viewState.value = TripsViewState()
    }

    fun getTrips() {
        addDisposable(getAllTrips.observable()
                .flatMap {
                    it.let {
                        tripsEntityUIMapper.observable(it)
                    } ?: run {
                        throw Throwable("Something went wrong :(")
                    }
                }.subscribe(
                        { onTripsReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
                        }
                )
        )
    }


    private fun onTripsReceived(trips: List<TripUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                trips = trips)
        viewState.value = newViewState
    }
}

