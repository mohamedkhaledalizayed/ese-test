package com.neqabty.login.modules.login.data.repository

import com.neqabty.login.modules.login.data.source.AuthDS
import com.neqabty.login.modules.login.domain.entity.UserEntity
import com.neqabty.login.modules.login.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDS: AuthDS) : AuthRepository {
    override fun login(mobile: String, password: String): Flow<UserEntity> {
        return flow { emit(authDS.login(mobile, password)) }
    }
}