package com.neqabty.healthcare.packages.packageslist.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.ui.BaseViewModel
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.packages.packageslist.domain.entity.PackagesEntity
import com.neqabty.healthcare.packages.packageslist.domain.entity.insurance.InsuranceEntityList
import com.neqabty.healthcare.packages.packageslist.domain.usecase.GetInsuranceDocsUseCase
import com.neqabty.healthcare.packages.packageslist.domain.usecase.GetPackagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PackagesViewModel @Inject constructor(
    private val getPackagesUseCase: GetPackagesUseCase,
    private val getInsuranceDocsUseCase: GetInsuranceDocsUseCase
) : BaseViewModel() {

    val packages = MutableLiveData<Resource<List<PackagesEntity>>>()
    fun getPackages(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            packages.postValue(Resource.loading(data = null))
            try {
                getPackagesUseCase.getPackages(code).collect {
                    packages.postValue(Resource.success(data = it))
                }
            }catch (e:Throwable){
                packages.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val insuranceDocs = MutableLiveData<Resource<InsuranceEntityList>>()
    fun getInsuranceDocs(packageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            insuranceDocs.postValue(Resource.loading(data = null))
            try {
                getInsuranceDocsUseCase.build(packageId).collect {
                    insuranceDocs.postValue(Resource.success(data = it))
                }
            }catch (e:Throwable){
                insuranceDocs.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}