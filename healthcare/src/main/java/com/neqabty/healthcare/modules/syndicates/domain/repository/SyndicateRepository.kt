package com.neqabty.healthcare.modules.syndicates.domain.repository

import com.neqabty.healthcare.modules.syndicates.domain.entity.SyndicateEntity
import kotlinx.coroutines.flow.Flow

interface SyndicateRepository {
    fun getSyndicates(): Flow<List<SyndicateEntity>>
}