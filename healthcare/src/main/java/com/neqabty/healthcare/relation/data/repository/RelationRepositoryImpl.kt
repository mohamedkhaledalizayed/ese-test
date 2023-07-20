package com.neqabty.healthcare.relation.data.repository


import com.neqabty.healthcare.relation.data.datasource.RelationDS
import com.neqabty.healthcare.relation.data.model.RelationModel
import com.neqabty.healthcare.relation.domain.entity.RelationEntityList
import com.neqabty.healthcare.relation.domain.repository.RelationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RelationRepositoryImpl @Inject constructor(private val relationDS: RelationDS) :
    RelationRepository {

    override fun getRelations(): Flow<List<RelationEntityList>> {
        return flow {
            emit(relationDS.getRelations().map { it.toRelationEntityList() })
        }
    }

}

private fun RelationModel.toRelationEntityList(): RelationEntityList {
    return RelationEntityList(
        id = id,
        relation = relation
    )
}