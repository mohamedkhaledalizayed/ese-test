package com.neqabty.healthcare.modules.offers.presentation.view.offers


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.modules.offers.domain.entity.offers.OffersEntity
import com.neqabty.healthcare.modules.offers.domain.interactors.GetOffersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor(private val getOffersUseCase: GetOffersUseCase) :
    ViewModel() {
    val offers = MutableLiveData<Resource<List<OffersEntity>>>()
    fun getOffers() {
        viewModelScope.launch(Dispatchers.IO) {
            offers.postValue(Resource.loading(data = null))
            try {
                getOffersUseCase.build().collect {
                    offers.postValue(Resource.success(data = it))
                }
            }catch (e:Throwable){
                offers.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}