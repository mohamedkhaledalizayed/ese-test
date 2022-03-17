package com.neqabty.valify.modules.home.domain.repository

import com.neqabty.valify.modules.home.data.model.VerifyUserBody
import com.neqabty.valify.modules.home.data.model.VerifyUserResponse
import com.neqabty.valify.modules.home.domain.entity.TokenEntity
import kotlinx.coroutines.flow.Flow

interface ValifyRepository {
    fun getToken(): Flow<TokenEntity>
    fun verifyUser(verifyUserBody: VerifyUserBody): Flow<VerifyUserResponse>
}