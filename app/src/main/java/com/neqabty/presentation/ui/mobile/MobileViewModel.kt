package com.neqabty.presentation.ui.mobile

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAllDoctors
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.DoctorEntityUIMapper
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class MobileViewModel @Inject constructor(val getAllDoctors: GetAllDoctors) : BaseViewModel() {

    private val doctorEntityUIMapper = DoctorEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<MobileViewState> = MutableLiveData()

    init {
        viewState.value = MobileViewState()
    }

    fun getAllContent2(type:String) {

//        val doctorsDisposable = getAllDoctors.observable()
//                .flatMap {
//                    it.let {
//                        doctorEntityUIMapper.observable(it)
//                    } ?: run {
//                        throw Throwable("Something went wrong :(")
//                    }
//                }.subscribe(
//                        {
//                            viewState.value = viewState.value?.copy(doctors = it)
//                            onContent1Received()
//                        },
//                        { errorState.value = it }
//                )
//
//        addDisposable(doctorsDisposable)
    }
//    private fun onContent2Received() {
//        if (viewState.value?.providers != null && viewState.value?.areas != null)
//            viewState.value = viewState.value?.copy(isLoading = false)
//    }
}
