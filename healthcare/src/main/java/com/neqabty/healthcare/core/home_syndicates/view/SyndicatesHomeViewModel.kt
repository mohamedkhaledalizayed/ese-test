package com.neqabty.healthcare.core.home_syndicates.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.ads.domain.entity.AdEntity
import com.neqabty.healthcare.ads.domain.interactors.AdsUseCase
import com.neqabty.healthcare.clinido.domain.entity.ClinidoEntity
import com.neqabty.healthcare.clinido.domain.usecases.ClinidoUseCase
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseViewModel
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.onboarding.contact.domain.entity.CheckMemberEntity
import com.neqabty.healthcare.onboarding.contact.domain.interactors.CheckMemberUseCase
import com.neqabty.healthcare.syndicateservices.domain.entity.SyndicateServiceEntity
import com.neqabty.healthcare.syndicateservices.domain.interactors.SyndicatesServicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SyndicatesHomeViewModel @Inject constructor(
    private val adsUseCase: AdsUseCase,
    private val syndicateServicesUseCase: SyndicatesServicesUseCase,
    private val checkMemberUseCase: CheckMemberUseCase,
    private val clinidoUseCase: ClinidoUseCase
) : BaseViewModel() {
    val ads = MutableLiveData<List<AdEntity>>()
    fun getAds() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                adsUseCase.build().collect {
                    ads.postValue(it)
                }
            } catch (e: Throwable) {
                Log.e("", e.toString())
            }
        }
    }

    val syndicateServices = MutableLiveData<Resource<List<SyndicateServiceEntity>>>()
    fun getSyndicateServices(entityCode: String, serviceCategory: String) {
        viewModelScope.launch(Dispatchers.IO) {
//            syndicateServices.postValue(Resource.loading(data = null))
            try {
                syndicateServicesUseCase.build(entityCode, serviceCategory).collect {
                    syndicateServices.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                syndicateServices.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }

    val checkMemberStatus = MutableLiveData<Resource<CheckMemberEntity>>()
    fun checkMemberStatus(nationalId: String){
        viewModelScope.launch(Dispatchers.IO){
            checkMemberStatus.postValue(Resource.loading(data = null))

            try {
                checkMemberUseCase.build(nationalId).collect(){
                    checkMemberStatus.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                checkMemberStatus.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }

    val clinidoUrl = MutableLiveData<Resource<ClinidoEntity>>()
    fun getUrl(phone: String, type: String, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            clinidoUrl.postValue(Resource.loading(data = null))
            try {
                clinidoUseCase.build(phone, type, name, Constants.NEQABTY_CODE).collect {
                    clinidoUrl.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                clinidoUrl.postValue(
                    Resource.error(
                        data = null,
                        message = handleError(e)
                    )
                )
            }
        }
    }
}