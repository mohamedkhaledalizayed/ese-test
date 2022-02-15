package com.neqabty.login.modules.login.presentation.model.mappers

import com.neqabty.login.modules.login.domain.entity.UserEntity
import com.neqabty.login.modules.login.presentation.model.UserUIModel

fun UserEntity.toUserUIModel(): UserUIModel {
    return UserUIModel(email, fullname, id, image, mobile, nationalId, password)
}