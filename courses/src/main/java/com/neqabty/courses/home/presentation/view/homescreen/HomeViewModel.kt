package com.neqabty.courses.home.presentation.view.homescreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.courses.home.domain.entity.CourseEntity
import com.neqabty.courses.home.domain.interactors.GetCoursesListUseCase
import com.neqabty.courses.offers.domain.interactors.GetOffersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getCoursesListUseCase: GetCoursesListUseCase,private val getOffersUseCase: GetOffersUseCase) :
    ViewModel() {
    val courses = MutableLiveData<List<CourseEntity>>()
    fun getCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getOffersUseCase.build().collect {
                    Log.e("offers",it.toString())
                }
            } catch (e: Throwable) {
                Log.e("offers",e.toString())

            }
        }
    }
}