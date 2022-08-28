package com.neqabty.meganeqabty.complains.domain.repository

import com.neqabty.meganeqabty.complains.domain.entity.ComplainEntity
import kotlinx.coroutines.flow.Flow

interface ComplainRepository {
    fun getAllComplains(): Flow<List<ComplainEntity>>
}