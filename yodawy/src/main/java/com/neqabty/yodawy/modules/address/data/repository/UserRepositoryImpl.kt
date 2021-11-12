package com.neqabty.yodawy.modules.address.data.repository

import com.neqabty.yodawy.modules.address.data.model.AddAddressRequestBody
import com.neqabty.yodawy.modules.address.data.model.GetUserRequestBody
import com.neqabty.yodawy.modules.address.data.model.mappers.toUserEntity
import com.neqabty.yodawy.modules.address.data.source.UserDS
import com.neqabty.yodawy.modules.address.domain.entity.UserEntity
import com.neqabty.yodawy.modules.address.domain.params.AddAddressUseCaseParams
import com.neqabty.yodawy.modules.address.domain.params.GetUserUseCaseParams
import com.neqabty.yodawy.modules.address.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDS: UserDS) : UserRepository {
    override fun getUser(params: GetUserUseCaseParams): Flow<UserEntity> {

        return userDS.getUser(GetUserRequestBody(params.mobileNumber, params.userNumber))
            .map { it.toUserEntity() }
    }

    override fun addAddress(params: AddAddressUseCaseParams): Flow<String> {
        return userDS.addAddress(
            AddAddressRequestBody(
                params.mobile,
                params.addressAlias,
                params.address,
                params.floor,
                params.building,
                params.apartment,
                params.landmark
            )
        ).map { it.id }
    }
}

