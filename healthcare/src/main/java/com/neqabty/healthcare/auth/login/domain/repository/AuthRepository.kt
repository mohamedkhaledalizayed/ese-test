package com.neqabty.healthcare.auth.login.domain.repository

import com.neqabty.healthcare.auth.login.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(mobile: String, password: String, firebaseToken: String): Flow<UserEntity>
}