package com.neqabty.news.modules.home.presentation.view.homescreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.news.modules.home.domain.entity.CourseEntity
import com.neqabty.news.modules.home.domain.interactors.GetCoursesListUseCase
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
            try {
                getCoursesListUseCase.build().collect {
                    courses.postValue(it)
                }
            }catch (e:Throwable){

            }
        }
    }
}