package com.neqabty.yodawy.modules.home.presentation.view.homescreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.yodawy.modules.home.domain.entity.CourseEntity
import com.neqabty.yodawy.modules.home.domain.interactors.GetCoursesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getCoursesListUseCase: GetCoursesListUseCase) :
    ViewModel() {
    val courses = MutableLiveData<List<CourseEntity>>()
    fun getCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            getCoursesListUseCase.build().collect {
                courses.postValue(it)
            }
        }
    }
}