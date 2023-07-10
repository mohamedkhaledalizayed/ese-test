package com.neqabty.healthcare.commen.mypackages.addfollower.domain.usecases

import com.neqabty.healthcare.commen.mypackages.addfollower.data.model.AddFollowerBody
import com.neqabty.healthcare.commen.mypackages.addfollower.domain.entity.AddFollowerEntity
import com.neqabty.healthcare.commen.relation.domain.entity.RelationEntityList
import com.neqabty.healthcare.commen.mypackages.addfollower.domain.repository.AddFollowerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFollowerUseCase @Inject constructor(private val addFollowerRepository: AddFollowerRepository) {

    fun build(addFollowerBody: AddFollowerBody): Flow<AddFollowerEntity>{
        return addFollowerRepository.addFollower(addFollowerBody)
    }

}