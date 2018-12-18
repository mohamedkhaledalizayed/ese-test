package com.neqabty.presentation.ui.subsyndicates

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetSubSyndicates
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.presentation.mappers.SyndicateEntityUIMapper
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class SubSyndicatesViewModel @Inject constructor(private val getSubSyndicates: GetSubSyndicates) : BaseViewModel() {

    private val syndicateEntityUIMapper = SyndicateEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<SubSyndicatesViewState> = MutableLiveData()

    init {
        viewState.value = SubSyndicatesViewState()
    }

    fun getSubSyndicates(id : String) {
        addDisposable(getSubSyndicates.getSubSyndicateById(id)
                .flatMap {
                    it.let {
                        syndicateEntityUIMapper.observable(it)
                    } ?: run {
                        throw Throwable("Something went wrong :(")
                    }
                }.subscribe(
                        { onSubsyndicatesReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
                        }
                )
        )
    }


    private fun onSubsyndicatesReceived(subSyndicates: List<SyndicateUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                subSyndicates = subSyndicates)
        viewState.value = newViewState
    }
}
