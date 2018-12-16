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
                id = from.id,
                fName = from.fName,
                lName = from.lName,
                email = from.email,
                number = from.number,
                password = from.password,
                mainSyndicateId = from.mainSyndicateId,
                subSyndicateId = from.subSyndicateId,
                verificationCode = from.verificationCode,
                syndicateUserMobile = from.syndicateUserMobile,
                createdAt = from.createdAt,
                updatedAt = from.updatedAt,
                userToken = from.userToken
        )
    }
}
