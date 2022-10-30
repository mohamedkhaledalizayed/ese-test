package com.neqabty.shealth.auth.login.domain.repository

import com.neqabty.shealth.auth.login.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(mobile: String, password: String): Flow<UserEntity>
}