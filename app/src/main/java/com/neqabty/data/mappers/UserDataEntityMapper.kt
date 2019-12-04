package com.neqabty.data.mappers

import com.neqabty.data.entities.UserData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataEntityMapper @Inject constructor() : Mapper<UserData, UserEntity>() {

    override fun mapFrom(from: UserData): UserEntity {
        return UserEntity(
                token = from.token,
                type = from.type,
                name = from.name
        )
    }
}
