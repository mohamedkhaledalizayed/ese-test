package com.neqabty.chefaa.modules.verifyuser.domain.repository

import kotlinx.coroutines.flow.Flow

interface VerificationRepository {
    fun verifyUser(mobile: String, code: String): Flow<Boolean>
}