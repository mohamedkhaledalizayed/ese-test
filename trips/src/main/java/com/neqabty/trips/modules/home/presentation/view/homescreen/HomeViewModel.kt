package com.neqabty.trips.modules.home.presentation.view.homescreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.trips.modules.home.domain.entity.CityEntity
import com.neqabty.trips.modules.home.domain.interactors.GetCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getCitiesUseCase: GetCitiesUseCase) :
    ViewModel() {
    val cities = MutableLiveData<List<CityEntity>>()
    fun getCities() {
        viewModelScope.launch(Dispatchers.IO) {
            getCitiesUseCase.build().collect {
                cities.postValue(it)
            }
        }
    }
}