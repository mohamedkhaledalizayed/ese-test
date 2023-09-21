package com.neqabty.healthcare.pharmacymart.address.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.pharmacymart.address.domain.entity.DeleteAddressEntity
import com.neqabty.healthcare.pharmacymart.address.domain.entity.PharmacyMartAddressesListEntity
import com.neqabty.healthcare.pharmacymart.address.domain.usecases.AddAddressUseCase
import com.neqabty.healthcare.pharmacymart.address.domain.usecases.DeleteAddressUseCase
import com.neqabty.healthcare.pharmacymart.address.domain.usecases.GetAddressesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PharmacyMartAddressViewModel @Inject constructor(private val getAllUserAddressUseCase: GetAddressesUseCase,
                                                       private val addAddressUseCase: AddAddressUseCase,
                                                       private val deleteAddressUseCase: DeleteAddressUseCase) : ViewModel() {

    val addresses = MutableLiveData<Resource<PharmacyMartAddressesListEntity>>()
    fun getAddresses() {
        viewModelScope.launch(Dispatchers.IO) {
            addresses.postValue(Resource.loading(data = null))
            try {
                getAllUserAddressUseCase.build().collect {
                    addresses.postValue(Resource.success(data = it))
                }
            } catch (exception:Throwable){
                addresses.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

    val data = MutableLiveData<Resource<Boolean>>()
    fun addAddress(
        apartment: String = "",
        buildingNo: String = "",
        floor: String = "",
        landMark: String = "",
        lat: String = "",
        long: String = "",
        phone: String = "",
        streetName: String = "",
        title: String = "" ) {
        viewModelScope.launch(Dispatchers.IO) {
            data.postValue(Resource.loading(data = null))
            try {
                addAddressUseCase.build(
                    apartment = apartment,
                    buildingNo = buildingNo,
                    floor = floor,
                    landMark = landMark,
                    lat = lat,
                    long = long,
                    phone = phone,
                    streetName = streetName,
                    title = title
                ).collect {
                    data.postValue(Resource.success(it))
                }
            } catch (e: Throwable) {
                data.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val addressStatus = MutableLiveData<Resource<DeleteAddressEntity>>()
    fun deleteAddresses(addressId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addressStatus.postValue(Resource.loading(data = null))
            try {
                deleteAddressUseCase.build(addressId).collect {
                    addressStatus.postValue(Resource.success(data = it))
                }
            } catch (exception:Throwable){
                addressStatus.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

}