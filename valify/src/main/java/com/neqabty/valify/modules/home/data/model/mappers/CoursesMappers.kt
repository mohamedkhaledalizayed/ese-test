package com.neqabty.valify.modules.home.data.model.mappers

import com.neqabty.valify.modules.home.domain.entity.TokenEntity
import com.neqabty.valify.modules.home.data.model.TokenModel

fun TokenModel.toTokenEntity(): TokenEntity {
    return TokenEntity(
        this.accessToken,
        this.expiresIn,
        this.refreshToken,
        this.scope,
        this.tokenType
    )
}