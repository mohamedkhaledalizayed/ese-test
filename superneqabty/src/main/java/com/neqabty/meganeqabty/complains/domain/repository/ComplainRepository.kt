package com.neqabty.meganeqabty.complains.domain.repository

import kotlinx.coroutines.flow.Flow

interface ComplainRepository {
    fun getAllComplains(): Flow<String>
}