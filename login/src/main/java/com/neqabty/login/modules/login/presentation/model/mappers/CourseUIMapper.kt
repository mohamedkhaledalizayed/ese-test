package com.neqabty.login.modules.login.presentation.model.mappers

import com.neqabty.login.modules.login.domain.entity.UserEntity
import com.neqabty.login.modules.login.presentation.model.UserUIModel

fun UserEntity.toUserUIModel(): UserUIModel {
    return UserUIModel(name, token, email, fullname, image, mobile, nationalId)
}