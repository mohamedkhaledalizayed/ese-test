package com.neqabty.healthcare.relation.domain.usecases

import com.neqabty.healthcare.relation.domain.entity.RelationEntityList
import com.neqabty.healthcare.relation.domain.repository.RelationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RelationUseCase @Inject constructor(private val relationRepository: RelationRepository) {

    fun build(): Flow<List<RelationEntityList>>{
        return relationRepository.getRelations()
    }

}