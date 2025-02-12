package com.neqabty.yodawy.modules.address.data.repository

import com.neqabty.yodawy.modules.address.data.model.AddAddressRequestBody
import com.neqabty.yodawy.modules.address.data.model.EditAddressRequestBody
import com.neqabty.yodawy.modules.address.data.model.GetUserRequestBody
import com.neqabty.yodawy.modules.address.data.model.mappers.toUserEntity
import com.neqabty.yodawy.modules.address.data.source.UserDS
import com.neqabty.yodawy.modules.address.domain.entity.UserEntity
import com.neqabty.yodawy.modules.address.domain.params.AddAddressUseCaseParams
import com.neqabty.yodawy.modules.address.domain.params.EditAddressUseCaseParams
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
                params.lat,
                params.long,
                params.landmark
            )
        ).map { it.id }
    }

    override fun editAddress(params: EditAddressUseCaseParams): Flow<String> {
        return userDS.editAddress(EditAddressRequestBody(
            params.address_id,
            params.mobile,
            params.AddressAlias,
            params.Address,
            params.Floor,
            params.Building,
            params.Apartment,
            params.lat,
            params.long,
            params.Landmark
        )).map { it.id }
    }
}

