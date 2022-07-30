package com.neqabty.signup.modules.signup.data.model.mappers

import com.neqabty.signup.modules.signup.domain.entity.UserEntity
import com.neqabty.signup.modules.signup.data.model.syndicatemember.UserModel

fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(email ?: "", fullname, id, image ?: "", mobile, nationalId, entity.name, entity.code)
}