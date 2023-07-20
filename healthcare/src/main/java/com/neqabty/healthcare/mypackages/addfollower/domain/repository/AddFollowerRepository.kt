package com.neqabty.healthcare.mypackages.addfollower.domain.repository


import com.neqabty.healthcare.mypackages.addfollower.data.model.AddFollowerBody
import com.neqabty.healthcare.mypackages.addfollower.domain.entity.AddFollowerEntity
import kotlinx.coroutines.flow.Flow

interface AddFollowerRepository {
    fun addFollower(addFollowerBody: AddFollowerBody): Flow<AddFollowerEntity>
}