package com.neqabty.healthcare.complains.domain.repository

import com.neqabty.healthcare.complains.domain.entity.ComplainEntity
import kotlinx.coroutines.flow.Flow

interface ComplainRepository {
    fun getAllComplains(): Flow<List<ComplainEntity>>
}