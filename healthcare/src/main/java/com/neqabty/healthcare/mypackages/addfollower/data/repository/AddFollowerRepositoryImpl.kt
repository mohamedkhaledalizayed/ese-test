package com.neqabty.healthcare.mypackages.addfollower.data.repository

import com.neqabty.healthcare.mypackages.addfollower.data.datasource.AddFollowerDS
import com.neqabty.healthcare.mypackages.addfollower.data.model.AddFollowerBody
import com.neqabty.healthcare.mypackages.addfollower.data.model.addfollower.AddFollowerModel
import com.neqabty.healthcare.mypackages.addfollower.domain.entity.AddFollowerEntity
import com.neqabty.healthcare.mypackages.addfollower.domain.repository.AddFollowerRepository
import com.neqabty.healthcare.relation.data.model.RelationModel
import com.neqabty.healthcare.relation.domain.entity.RelationEntityList
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