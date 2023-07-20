package com.neqabty.healthcare.syndicates.domain.repository

import com.neqabty.healthcare.syndicates.domain.entity.SyndicateEntity
import kotlinx.coroutines.flow.Flow

interface SyndicateRepository {
    fun getSyndicates(): Flow<List<SyndicateEntity>>
}