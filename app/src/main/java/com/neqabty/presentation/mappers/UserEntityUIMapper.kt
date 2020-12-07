package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.UserEntity
import com.neqabty.presentation.entities.UserUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserEntityUIMapper @Inject constructor() : Mapper<UserEntity, UserUI>() {

    override fun mapFrom(from: UserEntity): UserUI {
        val userUI = UserUI(
                mobile = from.mobile,
                type = from.type,
                jwt = from.jwt,
                details = from.details?.map {userDetails ->
                    return@map UserUI.UserDetails(userDetails.name, userDetails.userNumber)
                }
        )
        return userUI
    }
}