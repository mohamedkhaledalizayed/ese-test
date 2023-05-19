package com.neqabty.healthcare.commen.onboarding.contact.view


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.commen.onboarding.contact.data.model.SubmitClientRequest
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.GovEntity
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.SubmitClientEntity
import com.neqabty.healthcare.commen.onboarding.contact.domain.interactors.GetLookupsUseCase
import com.neqabty.healthcare.commen.onboarding.contact.domain.interactors.SubmitClientUseCase
import com.neqabty.healthcare.core.ui.BaseViewModel
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmploymentDetailsViewModel @Inject constructor(
    private val getLookupsUseCase: GetLookupsUseCase,
    private val submitClientUseCase: SubmitClientUseCase
) :
    BaseViewModel() {

    val govList = MutableLiveData<Resource<List<GovEntity>>>()
    fun getLookups() {
        govList.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getLookupsUseCase.build().collect {
                    govList.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                govList.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
                Log.e("", e.toString())
            }
        }
    }

    val submitClientStatus = MutableLiveData<Resource<SubmitClientEntity>>()
    fun submitClient(submitClientRequest: SubmitClientRequest) {
        submitClientStatus.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                submitClientUseCase.build(submitClientRequest).collect {
                    submitClientStatus.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                submitClientStatus.postValue(
                    Resource.error(
                        data = null,
                        message = handleError(e)
                    )
                )
            }
        }
    }
}