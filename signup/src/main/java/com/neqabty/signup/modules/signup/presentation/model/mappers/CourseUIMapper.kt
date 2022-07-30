package com.neqabty.signup.modules.signup.presentation.model.mappers

import com.neqabty.signup.modules.signup.domain.entity.UserEntity
import com.neqabty.signup.modules.signup.presentation.model.UserUIModel

fun UserEntity.toUserUIModel(): UserUIModel {
    return UserUIModel(email, fullname, id, image, mobile, nationalId, entityName, entityCode)
}