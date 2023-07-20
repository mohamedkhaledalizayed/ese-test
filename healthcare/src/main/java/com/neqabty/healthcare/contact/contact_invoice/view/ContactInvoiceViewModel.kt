package com.neqabty.healthcare.contact.contact_invoice.view



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.onboarding.contact.domain.entity.InvoiceEntity
import com.neqabty.healthcare.onboarding.contact.domain.interactors.SubmitInvoiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactInvoiceViewModel @Inject constructor(private val submitInvoiceUseCase: SubmitInvoiceUseCase) :
    ViewModel() {
    val invoice = MutableLiveData<Resource<InvoiceEntity>>()
    fun submitInvoice(nationalId: String, amount: String, tenor: String) {
        viewModelScope.launch(Dispatchers.IO) {
            invoice.postValue(Resource.loading(data = null))
            try {
                submitInvoiceUseCase.build(nationalId, amount, tenor).collect {
                    invoice.postValue(Resource.success(data = it))
                }
            }catch (e:Throwable){
                invoice.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}