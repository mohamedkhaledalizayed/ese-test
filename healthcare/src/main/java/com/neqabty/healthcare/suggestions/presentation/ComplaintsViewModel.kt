package com.neqabty.healthcare.suggestions.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.suggestions.data.model.ComplaintBody
import com.neqabty.healthcare.suggestions.domain.entity.CategoryEntity
import com.neqabty.healthcare.suggestions.domain.usecases.AddComplaintUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComplaintsViewModel @Inject constructor(private val addComplaintUseCase: AddComplaintUseCase) :
    ViewModel() {

    val complaintStatus = MutableLiveData<Resource<String>>()
    fun addComplaint(complaintBody: ComplaintBody) {
        viewModelScope.launch(Dispatchers.IO) {
            complaintStatus.postValue(Resource.loading(data = null))
            try {
                addComplaintUseCase.build(complaintBody).collect {
                    complaintStatus.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                complaintStatus.postValue(
                    Resource.error(
                        data = null,
                        message = AppUtils().handleError(e)
                    )
                )
            }

        }
    }

    val categories = MutableLiveData<Resource<List<CategoryEntity>>>()
    fun getComplaintsCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            categories.postValue(Resource.loading(data = null))
            try {
                addComplaintUseCase.build().collect {
                    categories.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                categories.postValue(
                    Resource.error(
                        data = null,
                        message = AppUtils().handleError(e)
                    )
                )
            }

        }
    }
}