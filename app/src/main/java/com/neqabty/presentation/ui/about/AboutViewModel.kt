package com.neqabty.presentation.ui.about

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAllSyndicateBranches
import com.neqabty.domain.usecases.GetSyndicate
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.SyndicateBranchEntityUIMapper
import com.neqabty.presentation.mappers.SyndicateEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(private val getSyndicate: GetSyndicate, private val getAllSyndicateBranches: GetAllSyndicateBranches) : BaseViewModel() {

    private val syndicateEntityUIMapper = SyndicateEntityUIMapper()
    private val syndicateBranchEntityUIMapper = SyndicateBranchEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<AboutViewState> = MutableLiveData()

    init {
        viewState.value = AboutViewState()
    }

    fun getSyndicate(id: String) {
        viewState.value?.syndicate?.let {
            onSyndicateReceived()
        } ?: addDisposable(getSyndicate.getSyndicateById(id)
            .map {
                it.let {
                    syndicateEntityUIMapper.mapFrom(it)
                }
            }.subscribe(
                {
                    viewState.value = viewState.value?.copy(syndicate = it)
                    onSyndicateReceived()
                },
                {
                    viewState.value = viewState.value?.copy(isLoading = false)
                    errorState.value = handleError(it)
                }
            )
        )
    }

        fun getSyndicateBranches() {
            viewState.value?.branches?.let {
                onSyndicateReceived()
            } ?: addDisposable(getAllSyndicateBranches.observable()
                .flatMap {
                    it.let {
                        syndicateBranchEntityUIMapper.observable(it)
                    }
                }.subscribe(
                    {
                        viewState.value = viewState.value?.copy(branches = it)
                        onSyndicateReceived()
                    },
                    {
                        viewState.value = viewState.value?.copy(isLoading = false)
                        errorState.value = handleError(it)
                    }
                )
            )
    }

    private fun onSyndicateReceived() {
        if (viewState.value?.syndicate != null && viewState.value?.branches != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }
}
