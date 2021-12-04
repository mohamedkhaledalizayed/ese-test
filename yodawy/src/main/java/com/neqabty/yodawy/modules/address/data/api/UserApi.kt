package com.neqabty.yodawy.modules.address.data.api

import com.neqabty.yodawy.modules.address.data.model.*
import com.neqabty.yodawy.modules.address.data.model.response.addaddress.AddAddressModel
import com.neqabty.yodawy.modules.address.domain.entity.CourseEntity
import com.neqabty.yodawy.modules.address.domain.params.EditAddressUseCaseParams
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("user")
    suspend fun getUser(@Body body:GetUserRequestBody): Response<UserModel>

    @POST("address/addaddress")
    suspend fun addAddress(@Body body:AddAddressRequestBody): Response<AddressResponse>

    @POST("address/updateaddresses")
    suspend fun editAddress(@Body body: EditAddressRequestBody): Response<AddressResponse>
}