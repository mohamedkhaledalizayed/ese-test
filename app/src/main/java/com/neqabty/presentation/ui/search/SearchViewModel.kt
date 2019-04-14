package com.neqabty.presentation.ui.search

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.*
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.DegreeUI
import com.neqabty.presentation.mappers.*
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class SearchViewModel @Inject constructor(val getAllGoverns: GetAllGoverns, val getAllAreas: GetAllAreas, val getAllProvidersTypes: GetAllProvidersTypes, val getAllDegrees: GetAllDegrees, val getAllSpecializations: GetAllSpecializations) : BaseViewModel() {
    private val areaEntityUIMapper = AreaEntityUIMapper()
    private val governEntityUIMapper = GovernEntityUIMapper()
    private val providerTypeEntityUIMapper = ProviderTypeEntityUIMapper()
    private val degreeEntityUIMapper = DegreeEntityUIMapper()
    private val specializationEntityUIMapper = SpecializationEntityUIMapper()


    var govIndex = 0;
    var areaIndex = 0;
    var providerTypeIndex = 0;
    var professionIndex = 0;
    var degreeIndex = 0;
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<SearchViewState> = MutableLiveData()

    init {
        viewState.value = SearchViewState(isLoading = true)
    }

    fun getAllContent() {
        viewState.value = viewState.value?.copy(isLoading = true)
        val governsDisposable = getAllGoverns.observable()
                .flatMap {
                    it.let {
                        governEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(governs = it)
                            onContentReceived()
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
                            onContentReceived()
                        },
                        { errorState.value = it }
                )

        val providerTypesDisposable = getAllProvidersTypes.getProviderTypes("directory")
                .flatMap {
                    it.let {
                        providerTypeEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(providerTypes = it)
                            onContentReceived()
                        },
                        { errorState.value = it }
                )

        val professionsDisposable = getAllSpecializations.observable()
                .flatMap {
                    it.let {
                        specializationEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(professions = it)
                            onContentReceived()
                        },
                        { errorState.value = it }
                )

        val degreesDisposable = getAllDegrees.observable()
                .flatMap {
                    it.let {
                        degreeEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        {
                            val edited = listOf(DegreeUI(0)) +it
                            viewState.value = viewState.value?.copy(degrees = edited)
                            onContentReceived()
                        },
                        { errorState.value = it }
                )

        viewState.value?.areas?.let {
            onContentReceived()
        } ?: addDisposable(areasDisposable)

        viewState.value?.governs?.let {
            onContentReceived()
        } ?: addDisposable(governsDisposable)

        viewState.value?.providerTypes?.let {
            onContentReceived()
        } ?: addDisposable(providerTypesDisposable)

        viewState.value?.professions?.let {
            onContentReceived()
        } ?: addDisposable(professionsDisposable)

        viewState.value?.degrees?.let {
            onContentReceived()
        } ?: addDisposable(degreesDisposable)

    }

    private fun onContentReceived() {
        if (viewState.value?.governs != null && viewState.value?.areas != null && viewState.value?.providerTypes != null && viewState.value?.professions != null && viewState.value?.degrees != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }
}
