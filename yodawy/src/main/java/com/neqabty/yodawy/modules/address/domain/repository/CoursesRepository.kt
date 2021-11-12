package com.neqabty.yodawy.modules.address.domain.repository

import com.neqabty.yodawy.modules.address.data.model.AddressResponse
import com.neqabty.yodawy.modules.address.data.model.response.addaddress.AddAddressModel
import com.neqabty.yodawy.modules.address.domain.entity.UserEntity
import com.neqabty.yodawy.modules.address.domain.params.AddAddressUseCaseParams
import com.neqabty.yodawy.modules.address.domain.params.GetUserUseCaseParams
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    suspend fun getUser(params: GetUserUseCaseParams): Flow<UserEntity>
    suspend fun addAddress(params: AddAddressUseCaseParams): Flow<String>
}