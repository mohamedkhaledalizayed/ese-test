package com.neqabty.healthcare.commen.mypackages.addfollower.data.repository

import com.neqabty.healthcare.commen.mypackages.addfollower.data.datasource.AddFollowerDS
import com.neqabty.healthcare.commen.mypackages.addfollower.data.model.AddFollowerBody
import com.neqabty.healthcare.commen.mypackages.addfollower.data.model.addfollower.AddFollowerModel
import com.neqabty.healthcare.commen.relation.data.model.RelationModel
import com.neqabty.healthcare.commen.mypackages.addfollower.domain.entity.AddFollowerEntity
import com.neqabty.healthcare.commen.relation.domain.entity.RelationEntityList
import com.neqabty.healthcare.commen.mypackages.addfollower.domain.repository.AddFollowerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddFollowerRepositoryImpl @Inject constructor(private val addFollowerDS: AddFollowerDS) :
    AddFollowerRepository {

    override fun addFollower(addFollowerBody: AddFollowerBody): Flow<AddFollowerEntity> {
        return flow {
            emit(addFollowerDS.addFollower(addFollowerBody).toAddFollowerEntity())
        }
    }

}

private fun AddFollowerModel.toAddFollowerEntity(): AddFollowerEntity {
    return AddFollowerEntity(
        data = data,
        message = message,
        status = status
    )
}

private fun RelationModel.toRelationEntityList(): RelationEntityList {
    return RelationEntityList(
        id = id,
        relation = relation
    )
}