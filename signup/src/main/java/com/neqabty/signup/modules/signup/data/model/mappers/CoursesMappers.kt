package com.neqabty.signup.modules.signup.data.model.mappers

import com.neqabty.signup.modules.signup.data.model.syndicatemember.UserModel
import com.neqabty.signup.modules.signup.domain.entity.UserEntity

fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(
        email = email ?: "",
        token = token.key,
        fullname = fullname,
        id = id,
        image = image ?: "",
        mobile = mobile,
        nationalId = nationalId,
        entityName = entity.name,
        entityImage = entity.imageUrl,
        entityCode = entity.code)
}