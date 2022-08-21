package com.neqabty.signup.modules.signup.presentation.model.mappers

import com.neqabty.signup.modules.signup.domain.entity.UserEntity
import com.neqabty.signup.modules.signup.presentation.model.UserUIModel

fun UserEntity.toUserUIModel(): UserUIModel {
    return UserUIModel(
        email = email ?: "",
        token = token,
        fullname = fullname,
        id = id,
        image = image ?: "",
        mobile = mobile,
        nationalId = nationalId,
        entityName = entityName,
        entityImage = entityImage,
        entityCode = entityCode
    )
}