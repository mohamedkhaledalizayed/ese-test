package com.neqabty.yodawy.modules.address.data.repository

import com.neqabty.yodawy.modules.address.data.model.AddAddressRequestBody
import com.neqabty.yodawy.modules.address.data.model.GetUserRequestBody
import com.neqabty.yodawy.modules.address.data.model.mappers.toUserEntity
import com.neqabty.yodawy.modules.address.data.source.UserDS
import com.neqabty.yodawy.modules.address.domain.entity.UserEntity
import com.neqabty.yodawy.modules.address.domain.params.AddAddressUseCaseParams
import com.neqabty.yodawy.modules.address.domain.params.GetUserUseCaseParams
import com.neqabty.yodawy.modules.address.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(private val userDS: UserDS) : CoursesRepository {
    override suspend fun getUser(params: GetUserUseCaseParams): Flow<UserEntity> {
        return userDS.getUser(GetUserRequestBody(params.mobileNumber, params.userNumber))
            .map { it.toUserEntity() }
    }

    override suspend fun addAddress(params: AddAddressUseCaseParams): Flow<String> {
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

