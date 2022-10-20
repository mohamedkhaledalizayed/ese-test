package com.neqabty.healthcare.auth.signup.data.model.mappers

import com.neqabty.healthcare.auth.signup.data.model.syndicatemember.UserModel
import com.neqabty.healthcare.auth.signup.domain.entity.UserEntity


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