package com.neqabty.chefaa.modules.address.data.api

import com.neqabty.chefaa.modules.ChefaaResponse
import com.neqabty.chefaa.modules.address.data.models.AddUserAddressBody
import com.neqabty.chefaa.modules.address.data.models.AddressModel
import com.neqabty.chefaa.modules.address.data.models.GetUserAddressBody
import com.neqabty.chefaa.modules.address.data.models.UpdateUserAddressBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AddressApi {
    @POST("list-addresses")
    suspend fun getAllUserAddress(@Body getUserAddressBody: GetUserAddressBody): ChefaaResponse<List<AddressModel>>

    @POST("create-address")
    suspend fun addUserAddress(@Body addUserAddressBody: AddUserAddressBody): ChefaaResponse<AddressModel>

    @POST("update-address")
    suspend fun updateUserAddress(@Body updateUserAddressBody: UpdateUserAddressBody):ChefaaResponse<Int>
}