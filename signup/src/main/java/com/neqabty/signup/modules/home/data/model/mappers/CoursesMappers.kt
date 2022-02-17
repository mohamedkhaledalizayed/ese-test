package com.neqabty.signup.modules.home.data.model.mappers

import com.neqabty.signup.modules.home.domain.entity.UserEntity
import com.neqabty.signup.modules.home.data.model.UserModel

fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(email ?: "", fullname, id, image ?: "", mobile, nationalId)
}