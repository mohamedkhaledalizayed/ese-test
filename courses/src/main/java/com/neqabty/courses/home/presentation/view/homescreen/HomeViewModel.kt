package com.neqabty.courses.home.presentation.view.homescreen


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.courses.core.utils.AppUtils
import com.neqabty.courses.core.utils.Resource
import com.neqabty.courses.home.domain.entity.CourseEntity
import com.neqabty.courses.home.domain.interactors.GetCoursesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getCoursesListUseCase: GetCoursesListUseCase) :
    ViewModel() {

    val courses = MutableLiveData<Resource<List<CourseEntity>>>()
    fun getCourses() {
        courses.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getCoursesListUseCase.build().collect {
                    courses.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                courses.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val courseDetails = MutableLiveData<Resource<List<CourseEntity>>>()
    fun getCourseDetails(id: String) {
        courseDetails.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getCoursesListUseCase.build(id).collect {
                    courseDetails.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                courseDetails.postValue(
                    Resource.error(
                        data = null,
                        message = AppUtils().handleError(e)
                    )
                )
            }
        }
    }

}