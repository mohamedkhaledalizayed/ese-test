package com.neqabty.healthcare.chefaa.address.data.api

import com.neqabty.healthcare.chefaa.ChefaaResponse
import com.neqabty.healthcare.chefaa.address.data.models.AddUserAddressBody
import com.neqabty.healthcare.chefaa.address.data.models.AddressModel
import com.neqabty.healthcare.chefaa.address.data.models.GetUserAddressBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AddressApi {
    @POST("healthcare/api/v1/chefaa/list-addresses")
    suspend fun getAllUserAddress(@Body getUserAddressBody: GetUserAddressBody): ChefaaResponse<List<AddressModel>>

    @POST("healthcare/api/v1/chefaa/create-address")
    suspend fun addUserAddress(@Body addUserAddressBody: AddUserAddressBody): ChefaaResponse<AddressModel>

}