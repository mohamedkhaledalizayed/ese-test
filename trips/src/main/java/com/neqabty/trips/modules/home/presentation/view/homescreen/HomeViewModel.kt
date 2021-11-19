package com.neqabty.trips.modules.home.presentation.view.homescreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.trips.modules.home.domain.entity.TripsEntity
import com.neqabty.trips.modules.home.domain.interactors.GetTripsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getTripsUseCase: GetTripsUseCase) :
    ViewModel() {
    val courses = MutableLiveData<List<TripsEntity>>()
    fun getCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            getTripsUseCase.build().collect {
                courses.postValue(it)
            }
        }
    }
}