package com.neqabty.yodawy.modules.address.data.source

import android.util.Log
import com.neqabty.yodawy.modules.address.data.api.UserApi
import com.neqabty.yodawy.modules.address.data.model.*
import com.neqabty.yodawy.modules.address.data.model.mappers.toUserEntity
import com.neqabty.yodawy.modules.address.data.model.response.addaddress.AddAddressModel
import com.neqabty.yodawy.modules.address.domain.params.EditAddressUseCaseParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDS @Inject constructor(private val userApi: UserApi) {
    fun getUser(body: GetUserRequestBody): Flow<UserModel> {
        return flow { emit(userApi.getUser(body).dataModel) }
    }

    fun addAddress(body: AddAddressRequestBody): Flow<AddressResponse>{
        return flow { emit(userApi.addAddress(body).dataModel) }
    }

    fun editAddress(body: EditAddressRequestBody): Flow<AddressResponse>{
        return flow { emit(userApi.editAddress(body).dataModel) }
    }
}