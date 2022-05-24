package com.neqabty.healthcare.modules.home.presentation.view.homescreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.modules.home.domain.entity.ProviderEntity
import com.neqabty.healthcare.modules.home.domain.interactors.GetHealthCareProviderstUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getHealthCareProviderstUseCase: GetHealthCareProviderstUseCase) :
    ViewModel() {
    val providers = MutableLiveData<List<ProviderEntity>>()
    fun getProviders() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getHealthCareProviderstUseCase.build().collect {
                    providers.postValue(it)
                }
            }catch (e:Throwable){

            }
        }
    }
}