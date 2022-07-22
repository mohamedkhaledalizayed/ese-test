package com.neqabty.courses.offers.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.courses.offers.domain.entity.OfferEntity
import com.neqabty.courses.offers.domain.interactors.GetCourseOffersUseCase
import com.neqabty.courses.offers.domain.interactors.GetOffersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor(
    private val getOffersUseCase: GetOffersUseCase,
    private val getCourseOffersUseCase: GetCourseOffersUseCase
) : ViewModel() {
    val offers = MutableLiveData<List<OfferEntity>>()
    fun getCoursesOffers(courseId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if(courseId != -1)
                    getCourseOffersUseCase.build(courseId).collect {
                        offers.postValue(it)
                    }
                else
                    getOffersUseCase.build().collect {
                        offers.postValue(it)
                    }
            }catch (e:Throwable){
                Log.e("getOffers",e.toString())
            }
        }
    }
}