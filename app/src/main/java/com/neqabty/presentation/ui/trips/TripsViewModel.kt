package com.neqabty.presentation.ui.trips

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAllTrips
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.TripUI
import com.neqabty.presentation.mappers.TripsEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TripsViewModel @Inject constructor(private val getAllTrips: GetAllTrips) : BaseViewModel() {

    private val tripsEntityUIMapper = TripsEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<TripsViewState> = MutableLiveData()

    init {
        viewState.value = TripsViewState()
    }

    fun getTrips(id: String) {
        viewState.value?.trips?.let {
            onTripsReceived(it)
        } ?: addDisposable(getAllTrips.getAllTrips(id)
                .flatMap {
                    it.let {
                        tripsEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onTripsReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
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
