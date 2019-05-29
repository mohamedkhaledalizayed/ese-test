package com.neqabty.presentation.ui.chooseArea

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAllAreas
import com.neqabty.domain.usecases.GetAllGoverns
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.AreaEntityUIMapper
import com.neqabty.presentation.mappers.GovernEntityUIMapper

import javax.inject.Inject


class ChooseAreaViewModel @Inject constructor(val getAllGoverns: GetAllGoverns, val getAllAreas: GetAllAreas) : BaseViewModel() {
    private val areaEntityUIMapper = AreaEntityUIMapper()
    private val governEntityUIMapper = GovernEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<ChooseAreaViewState> = MutableLiveData()

    init {
        viewState.value = ChooseAreaViewState(isLoading = true)
    }

    fun getAllContent1() {
        viewState.value = viewState.value?.copy(isLoading = true)
        val governsDisposable = getAllGoverns.observable()
                .flatMap {
                    it.let {
                        governEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(governs = it)
                            onContent1Received()
                        },
                        { errorState.value = it }
                )

        val areasDisposable = getAllAreas.observable()
                .flatMap {
                    it.let {
                        areaEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(areas = it)
                            onContent1Received()
                        },
                        { errorState.value = it }
                )

        viewState.value?.areas?.let {
            onContent1Received()
        } ?: addDisposable(areasDisposable)

        viewState.value?.governs?.let {
            onContent1Received()
        } ?: addDisposable(governsDisposable)
    }

    private fun onContent1Received() {
        if (viewState.value?.governs != null && viewState.value?.areas != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }
}
