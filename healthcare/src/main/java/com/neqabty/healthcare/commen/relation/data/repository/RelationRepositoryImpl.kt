package com.neqabty.healthcare.commen.relation.data.repository


import com.neqabty.healthcare.commen.relation.domain.entity.RelationEntityList
import com.neqabty.healthcare.commen.relation.data.datasource.RelationDS
import com.neqabty.healthcare.commen.relation.data.model.RelationModel
import com.neqabty.healthcare.commen.relation.domain.repository.RelationRepository
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