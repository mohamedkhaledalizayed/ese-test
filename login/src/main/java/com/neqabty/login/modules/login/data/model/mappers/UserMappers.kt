package com.neqabty.login.modules.login.data.model.mappers

import com.neqabty.login.modules.login.data.model.UserModel
import com.neqabty.login.modules.login.domain.entity.UserEntity

fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(email, fullname, id, image, mobile, nationalId, password)
}