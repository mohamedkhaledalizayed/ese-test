package com.neqabty.superneqabty.home.data.repository

import com.neqabty.superneqabty.home.data.model.VerifyUserBody
import com.neqabty.superneqabty.home.data.model.valify.GetToken
import com.neqabty.superneqabty.home.data.model.verifyuser.VerifyUserResponse
import com.neqabty.superneqabty.home.data.source.ValifyDS
import com.neqabty.superneqabty.home.domain.repository.ValifyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValifyRepositoryImpl @Inject constructor(private val valifyDS: ValifyDS) : ValifyRepository {

    override fun getToken(): Flow<GetToken> {
        return flow {
            emit(valifyDS.getToken())
        }
    }

    override fun verifyUser(verifyUserBody: VerifyUserBody): Flow<VerifyUserResponse> {
        return flow {
            emit(valifyDS.verifyUser(verifyUserBody))
        }
    }

}

