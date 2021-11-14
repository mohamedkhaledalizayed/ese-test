package com.neqabty.yodawy.modules.address.data.source

import android.util.Log
import com.neqabty.yodawy.modules.address.data.api.UserApi
import com.neqabty.yodawy.modules.address.data.model.AddAddressRequestBody
import com.neqabty.yodawy.modules.address.data.model.AddressResponse
import com.neqabty.yodawy.modules.address.data.model.GetUserRequestBody
import com.neqabty.yodawy.modules.address.data.model.UserModel
import com.neqabty.yodawy.modules.address.data.model.mappers.toUserEntity
import com.neqabty.yodawy.modules.address.data.model.response.addaddress.AddAddressModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDS @Inject constructor(private val userApi: UserApi) {
    suspend fun getUser(body: GetUserRequestBody): Flow<UserModel> {
        return flow { emit(userApi.getUser(body).dataModel) }
    }

    suspend fun addAddress(body: AddAddressRequestBody): Flow<AddAddressModel>{
        return flow { emit(userApi.addAddress(body).dataModel) }
    }
}