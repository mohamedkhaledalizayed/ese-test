package com.neqabty.valify.modules.home.data.repository

import com.neqabty.valify.modules.home.data.model.VerifyUserBody
import com.neqabty.valify.modules.home.data.model.VerifyUserResponse
import com.neqabty.valify.modules.home.data.model.mappers.toTokenEntity
import com.neqabty.valify.modules.home.data.source.ValifyDS
import com.neqabty.valify.modules.home.domain.entity.TokenEntity
import com.neqabty.valify.modules.home.domain.repository.ValifyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValifyRepositoryImpl @Inject constructor(private val valifyDS: ValifyDS) : ValifyRepository {
    override fun getToken(): Flow<TokenEntity> {
        return flow { emit(valifyDS.getToken().tokenModel.toTokenEntity()) }
    }

    override fun verifyUser(verifyUserBody: VerifyUserBody): Flow<VerifyUserResponse> {
        return flow {
            emit(valifyDS.verifyUser(verifyUserBody))
        }
    }
}