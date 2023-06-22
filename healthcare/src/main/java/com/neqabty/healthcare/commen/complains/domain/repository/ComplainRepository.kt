package com.neqabty.healthcare.commen.complains.domain.repository

import com.neqabty.healthcare.commen.complains.domain.entity.getcomplain.ComplainEntity
import kotlinx.coroutines.flow.Flow

interface ComplainRepository {
    fun addComplain(mobile: String, email: String, message: String): Flow<String>
    fun getAllComplains(): Flow<List<ComplainEntity>>
}