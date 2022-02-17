package com.neqabty.signup.modules.home.presentation.model.mappers

import com.neqabty.signup.modules.home.domain.entity.UserEntity
import com.neqabty.signup.modules.home.presentation.model.UserUIModel

fun UserEntity.toUserUIModel(): UserUIModel {
    return UserUIModel(email, fullname, id, image, mobile, nationalId)
}