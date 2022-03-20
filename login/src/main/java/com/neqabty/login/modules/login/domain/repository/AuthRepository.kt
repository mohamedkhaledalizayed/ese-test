package com.neqabty.login.modules.login.domain.repository

import com.neqabty.login.modules.login.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(mobile: String, password: String): Flow<UserEntity>
}