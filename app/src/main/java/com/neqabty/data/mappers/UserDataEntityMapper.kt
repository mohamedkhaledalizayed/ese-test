package com.neqabty.data.mappers

import com.neqabty.data.entities.UserData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataEntityMapper @Inject constructor() : Mapper<UserData, UserEntity>() {

    override fun mapFrom(from: UserData): UserEntity {
        val userEntity = UserEntity(
                mobile = from.mobile,
                type = from.type,
                syndicate = from.syndicate,
                section = from.section,
                jwt = from.jwt,
                details = from.details?.map { userDetails ->
                    return@map UserEntity.UserDetails(userDetails.name, userDetails.userNumber)
                }
        )
        return userEntity
    }
}
