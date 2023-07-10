package com.neqabty.healthcare.commen.relation.domain.repository

import com.neqabty.healthcare.commen.relation.domain.entity.RelationEntityList
import kotlinx.coroutines.flow.Flow

interface RelationRepository {
    fun getRelations(): Flow<List<RelationEntityList>>
}