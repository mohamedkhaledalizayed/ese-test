package com.neqabty.healthcare.pharmacymart.address.data.api


import com.neqabty.healthcare.pharmacymart.address.data.model.AddAddressBody
import com.neqabty.healthcare.pharmacymart.address.data.model.DeleteAddressBody
import com.neqabty.healthcare.pharmacymart.address.data.model.GetAddressesBody
import com.neqabty.healthcare.pharmacymart.address.data.model.addaddress.AddAddressModel
import com.neqabty.healthcare.pharmacymart.address.data.model.addresses.PharmacyMartAddressesModel
import com.neqabty.healthcare.pharmacymart.address.data.model.deleteaddress.DeleteAddressModel
import retrofit2.http.Body
import retrofit2.http.POST

interface PharmacyMartAddressApi {

    @POST("healthcare/api/v1/pharmacy/list-addresses")
    suspend fun getAddresses(@Body getAddressesBody: GetAddressesBody): PharmacyMartAddressesModel

    @POST("healthcare/api/v1/pharmacy/add-address")
    suspend fun addAddress(@Body addAddressBody: AddAddressBody): AddAddressModel

    @POST("healthcare/api/v1/pharmacy/delete-address")
    suspend fun deleteAddress(@Body deleteAddressBody: DeleteAddressBody): DeleteAddressModel

}