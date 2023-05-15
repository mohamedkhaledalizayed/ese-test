package com.neqabty.healthcare.commen.clinido.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.commen.clinido.domain.entity.ClinidoEntity
import com.neqabty.healthcare.commen.clinido.domain.usecases.ClinidoUseCase
import com.neqabty.healthcare.core.ui.BaseViewModel
import com.neqabty.healthcare.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClinidoViewModel @Inject constructor(private val clinidoUseCase: ClinidoUseCase) : BaseViewModel() {

    val clinidoUrl = MutableLiveData<Resource<ClinidoEntity>>()
    fun getUrl(phone: String, type: String, name: String, entityCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            clinidoUrl.postValue(Resource.loading(data = null))
            try {
                clinidoUseCase.build(phone, type, name, entityCode).collect {
                    clinidoUrl.postValue(Resource.success(data = it))
                }
            } catch (e: Throwable) {
                clinidoUrl.postValue(
                    Resource.error(
                        data = null,
                        message = handleError(e)
                    )
                )
            }
        }
    }
}