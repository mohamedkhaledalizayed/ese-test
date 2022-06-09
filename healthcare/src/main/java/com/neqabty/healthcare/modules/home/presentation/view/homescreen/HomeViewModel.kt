package com.neqabty.healthcare.modules.home.presentation.view.homescreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.modules.home.domain.entity.MedicalProviderEntity
import com.neqabty.healthcare.modules.home.domain.interactors.GetMedicalProviderstUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getMedicalProviderstUseCase: GetMedicalProviderstUseCase) :
    ViewModel() {
    val providers = MutableLiveData<List<MedicalProviderEntity>>()
    fun getProviders() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getMedicalProviderstUseCase.build().collect {
                    providers.postValue(it)
                }
            }catch (e:Throwable){
                Log.e("error",e.toString())
            }
        }
    }
}