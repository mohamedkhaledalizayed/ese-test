package com.neqabty.healthcare.commen.mypackages.addfollower.domain.repository


import com.neqabty.healthcare.commen.mypackages.addfollower.data.model.AddFollowerBody
import com.neqabty.healthcare.commen.mypackages.addfollower.domain.entity.AddFollowerEntity
import kotlinx.coroutines.flow.Flow

interface AddFollowerRepository {
    fun addFollower(addFollowerBody: AddFollowerBody): Flow<AddFollowerEntity>
}