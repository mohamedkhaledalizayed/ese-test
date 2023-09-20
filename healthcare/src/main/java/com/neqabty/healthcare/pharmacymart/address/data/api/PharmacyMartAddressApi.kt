package com.neqabty.healthcare.pharmacymart.address.data.api


import com.neqabty.healthcare.pharmacymart.address.data.model.AddAddressBody
import com.neqabty.healthcare.pharmacymart.address.data.model.GetAddressesBody
import com.neqabty.healthcare.pharmacymart.address.data.model.addaddress.AddAddressModel
import com.neqabty.healthcare.pharmacymart.address.data.model.addresses.PharmacyMartAddressesModel
import retrofit2.http.Body
import retrofit2.http.POST

interface PharmacyMartAddressApi {

    @POST("pharmacy/list-addresses")
    suspend fun getAddresses(@Body getAddressesBody: GetAddressesBody): PharmacyMartAddressesModel

    @POST("pharmacy/add-address")
    suspend fun addAddress(@Body addAddressBody: AddAddressBody): AddAddressModel

}