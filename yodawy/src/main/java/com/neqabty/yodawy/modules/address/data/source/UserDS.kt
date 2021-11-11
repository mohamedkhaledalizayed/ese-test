package com.neqabty.yodawy.modules.address.data.source

import com.neqabty.yodawy.modules.address.data.api.UserApi
import com.neqabty.yodawy.modules.address.data.model.AddAddressRequestBody
import com.neqabty.yodawy.modules.address.data.model.AddressResponse
import com.neqabty.yodawy.modules.address.data.model.GetUserRequestBody
import com.neqabty.yodawy.modules.address.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDS @Inject constructor(private val userApi: UserApi) {
    suspend fun getUser(body: GetUserRequestBody): Flow<UserModel> {
        return userApi.getUser(body).map { it.dataModel }
    }
    suspend fun addAddress(body: AddAddressRequestBody): Flow<AddressResponse>{
        return userApi.addAddress(body).map { it.dataModel }
    }
}