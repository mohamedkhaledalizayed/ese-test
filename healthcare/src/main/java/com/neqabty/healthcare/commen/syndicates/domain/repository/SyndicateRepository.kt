package com.neqabty.healthcare.commen.syndicates.domain.repository

import com.neqabty.healthcare.commen.syndicates.domain.entity.SyndicateEntity
import kotlinx.coroutines.flow.Flow

interface SyndicateRepository {
    fun getSyndicates(): Flow<List<SyndicateEntity>>
}