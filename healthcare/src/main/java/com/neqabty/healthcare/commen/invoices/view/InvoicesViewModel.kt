package com.neqabty.healthcare.commen.invoices.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.commen.invoices.domain.entity.InvoicesEntity
import com.neqabty.healthcare.commen.invoices.domain.usecase.GetAllInvoicesUseCase
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoicesViewModel @Inject constructor(private val getAllInvoicesUseCase: GetAllInvoicesUseCase): ViewModel() {

    val invoices = MutableLiveData<Resource<List<InvoicesEntity>>>()
    fun getAllInvoices(){
        invoices.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO){
            try {
                getAllInvoicesUseCase.build().collect(){
                    invoices.postValue(Resource.success(data = it))
                }
            }catch (e: Exception){
                invoices.postValue(Resource.error(data = null, message = e.message.toString()))
            }
        }
    }
}