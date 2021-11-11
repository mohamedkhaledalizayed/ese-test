package com.neqabty.presentation.ui.trackShipment

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.TrackShipment
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.TrackShipmentEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrackShipmentViewModel @Inject constructor(val trackShipment: TrackShipment) : BaseViewModel() {

    val trackShipmentEntityUIMapper = TrackShipmentEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<TrackShipmentViewState> = MutableLiveData()

    init {
        viewState.value = TrackShipmentViewState(isLoading = true)
    }


    fun trackShipment(userNumber: String) {
        viewState.value = viewState.value?.copy(isLoading = true)

        addDisposable(trackShipment.trackShipment(userNumber)
                .flatMap {
                    it.let {
                        trackShipmentEntityUIMapper.observable(it)
                    }
                }
                .subscribe(
                        {
                            var tmp = it.toMutableList()
//                            tmp.add(it[0])
//                            tmp.add(it[0])
//                            tmp.add(it[0])
                            viewState.value = viewState.value?.copy(isLoading = false, trackShipmentList = tmp)
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                ))
    }
}
