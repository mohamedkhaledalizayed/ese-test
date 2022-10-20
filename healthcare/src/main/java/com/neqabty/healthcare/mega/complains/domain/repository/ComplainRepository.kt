package com.neqabty.healthcare.mega.complains.domain.repository

import com.neqabty.healthcare.mega.complains.domain.entity.ComplainEntity
import kotlinx.coroutines.flow.Flow

interface ComplainRepository {
    fun getAllComplains(): Flow<List<ComplainEntity>>
}