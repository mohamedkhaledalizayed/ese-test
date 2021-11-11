package com.neqabty.presentation.ui.claiming

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.*
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.ClaimingValidationUI
import com.neqabty.presentation.mappers.*
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ClaimingViewModel @Inject constructor(
    val getAllGoverns: GetAllGoverns,
    val getAllAreas: GetAllAreas,
    val getProvidersByType: GetProvidersByType,
    val getAllProvidersTypes: GetAllProvidersTypes,
    val sendMedicalRequest: SendMedicalRequest,
    private val validateUserForClaiming: ValidateUserForClaiming,
    private val getLiteFollowersListData: GetLiteFollowersListData
) : BaseViewModel() {
    private val areaEntityUIMapper = AreaEntityUIMapper()
    private val governEntityUIMapper = GovernEntityUIMapper()
    private val providerTypeEntityUIMapper = ProviderTypeEntityUIMapper()
    private val providerEntityUIMapper = ProviderEntityUIMapper()
    private val claimingValidationEntityUIMapper = ClaimingValidationEntityUIMapper()
    private val liteFollowersListEntityUIMapper = LiteFollowersListEntityUIMapper()

    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<ClaimingViewState> = MutableLiveData()

    init {
        viewState.value = ClaimingViewState(isLoading = true)
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
                            onContent1Received()
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
                            onContent1Received()
                        },
                        { errorState.value = handleError(it) }
                )

        viewState.value?.liteFollowersListUI?.let {
            onContent1Received()
        } ?: addDisposable(followersDisposable)

        viewState.value?.areas?.let {
            onContent1Received()
        } ?: addDisposable(areasDisposable)

        viewState.value?.governs?.let {
            onContent1Received()
        } ?: addDisposable(governsDisposable)
    }

    fun getProviderTypes(govId: String, areaId: String) {
        val providersTypesDisposable = getAllProvidersTypes.getProviderTypes("claiming")
                .flatMap {
                    it.let {
                        providerTypeEntityUIMapper.observable(it)
                    } ?: run {
                        throw Throwable("Something went wrong :(")
                    }
                }.subscribe(
                        {
                            viewState.value = viewState.value?.copy(providerTypes = it)
                            onProviderTypesReceived()
                        },
                        { errorState.value = handleError(it) }
                )

//        viewState.value?.providerTypes?.let {
//            onProviderTypesReceived()
//        } ?: addDisposable(providersTypesDisposable)
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
//        viewState.value?.providers?.let {
//            onProvidersReceived()
//        } ?: addDisposable(providersDisposable)
    }

    fun sendMedicalRequest(
        mainSyndicateId: Int,
        subSyndicateId: Int,
        userNumber: String,
        email: String,
        phone: String,
        profession: Int,
        degree: Int,
        gov: Int,
        area: Int,
        doctor: Int,
        providerType: Int,
        provider: Int,
        name: String,
        oldbenid: String,
        details: String,
        followerName: String,
        followerRelation: String,
        docsNumber: Int,
        doc1: File?,
        doc2: File?,
        doc3: File?,
        doc4: File?,
        doc5: File?
    ) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(sendMedicalRequest.sendMedicalRequest(mainSyndicateId, subSyndicateId, userNumber, email, phone, profession, degree, gov, area, doctor, providerType, provider, name, oldbenid, details, followerName, followerRelation, docsNumber, doc1, doc2, doc3, doc4, doc5)
                .subscribe(
                        { viewState.value = viewState.value?.copy(isLoading = false) },
                        { errorState.value = handleError(it) }
                )
        )
    }

    fun validateUser(number: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(validateUserForClaiming.validateUser(number)
                .map {
                    it.let {
                        claimingValidationEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onValidationReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onValidationReceived(member: ClaimingValidationUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                member = member)
        viewState.value = newViewState
    }


    private fun onContent1Received() {
        if (viewState.value?.liteFollowersListUI != null && viewState.value?.governs != null && viewState.value?.areas != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }

    private fun onProviderTypesReceived() {
        if (viewState.value?.providerTypes != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }
    private fun onProvidersReceived() {
        if (viewState.value?.providers != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }
}
