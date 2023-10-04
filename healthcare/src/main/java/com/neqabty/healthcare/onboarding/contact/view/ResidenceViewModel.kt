package com.neqabty.healthcare.onboarding.contact.view


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.ui.BaseViewModel
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.onboarding.contact.domain.entity.GovEntity
import com.neqabty.healthcare.onboarding.contact.domain.interactors.GetLookupsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResidenceViewModel @Inject constructor(
    private val getLookupsUseCase: GetLookupsUseCase
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
//                Log.e("", e.toString())
            }
        }
    }
}