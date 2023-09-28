package com.neqabty.presentation.ui.refundInquiry

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.entities.AttachmentEntity
import com.neqabty.domain.usecases.*
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RefundInquiryViewModel @Inject constructor(
    private val getLiteFollowersListData: GetLiteFollowersListData,
    val getAllGoverns: GetAllGoverns,
    val getAllAreas: GetAllAreas,
    val getProvidersByType: GetProvidersByType,
    val getAllProvidersTypes: GetAllProvidersTypes
) : BaseViewModel() {
    private val liteFollowersListEntityUIMapper = LiteFollowersListEntityUIMapper()
    private val areaEntityUIMapper = AreaEntityUIMapper()
    private val governEntityUIMapper = GovernEntityUIMapper()
    private val providerTypeEntityUIMapper = ProviderTypeEntityUIMapper()
    private val providerEntityUIMapper = ProviderEntityUIMapper()
    private val refundEntityUIMapper = RefundEntityUIMapper()


    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<RefundInquiryViewState> = MutableLiveData()

    init {
        viewState.value = RefundInquiryViewState(isLoading = true)
    }


    fun getAllContent1(mobileNumber: String, number: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        val followersDisposable = getLiteFollowersListData.getLiteFollowersListData(mobileNumber, number)
            .flatMap {
                it.let {
                    liteFollowersListEntityUIMapper.observable(it)
                }
            }.subscribe(
                {
                    viewState.value = viewState.value?.copy(liteFollowersListUI = it)
                    onContent1Received()
                },
                {
                    viewState.value = viewState.value?.copy(isLoading = false)
                    errorState.value = handleError(it)
                }
            )

        val governsDisposable = getAllGoverns.observable()
            .flatMap {
                it.let {
                    governEntityUIMapper.observable(it)
                }
            }.subscribe(
                {
                    viewState.value = viewState.value?.copy(governs = it)
//                    onContent1Received()
                },
                { errorState.value = handleError(it) }
            )

        val areasDisposable = getAllAreas.observable()
            .flatMap {
                it.let {
                    areaEntityUIMapper.observable(it)
                }
            }.subscribe(
                {
                    viewState.value = viewState.value?.copy(areas = it)
//                    onContent1Received()
                },
                { errorState.value = handleError(it) }
            )

        viewState.value?.liteFollowersListUI?.let {
            onContent1Received()
        } ?: addDisposable(followersDisposable)

        viewState.value?.areas?.let {
//            onContent1Received()
        } ?: addDisposable(areasDisposable)

        viewState.value?.governs?.let {
//            onContent1Received()
        } ?: addDisposable(governsDisposable)
    }

    fun getProviderTypes(govId: String, areaId: String) {
        val providersTypesDisposable = getAllProvidersTypes.getProviderTypes("claiming")
            .flatMap {
                it.let {
                    providerTypeEntityUIMapper.observable(it)
                }
            }.subscribe(
                {
                    viewState.value = viewState.value?.copy(providerTypes = it)
                    onProviderTypesReceived()
                },
                { errorState.value = handleError(it) }
            )
    }

    fun getProvidersByType(typeId: String, govId: String, areaId: String, providerName: String?) {
        viewState.value = viewState.value?.copy(isLoading = true)
        val providersDisposable = getProvidersByType.getProvidersByType(typeId, govId, areaId, providerName)
            .flatMap {
                it.let {
                    providerEntityUIMapper.observable(it)
                } ?: run {
                    throw Throwable("Something went wrong :(")
                }
            }.subscribe(
                {
                    viewState.value = viewState.value?.copy(isLoading = false, providers = it)
                    onProvidersReceived()
                },
                { errorState.value = handleError(it) }
            )
    }



    private fun onContent1Received() {
        viewState.value = viewState.value?.copy(isLoading = false)
//        if (viewState.value?.liteFollowersListUI != null && viewState.value?.governs != null && viewState.value?.areas != null)
//            viewState.value = viewState.value?.copy(isLoading = false)
    }

    private fun onProviderTypesReceived() {
//        if (viewState.value?.providerTypes != null)
//            viewState.value = viewState.value?.copy(isLoading = false)
    }
    private fun onProvidersReceived() {
        if (viewState.value?.providers != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }
}
