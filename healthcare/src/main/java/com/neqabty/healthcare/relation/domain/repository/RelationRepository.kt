package com.neqabty.healthcare.relation.domain.repository

import com.neqabty.healthcare.relation.domain.entity.RelationEntityList
import kotlinx.coroutines.flow.Flow

interface RelationRepository {
    fun getRelations(): Flow<List<RelationEntityList>>
}