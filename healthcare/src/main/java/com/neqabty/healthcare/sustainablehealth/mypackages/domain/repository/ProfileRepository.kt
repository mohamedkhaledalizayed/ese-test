package com.neqabty.healthcare.sustainablehealth.mypackages.domain.repository


import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.AddFollowerBody
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.addfollower.AddFollowerEntity
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.profile.ProfileEntity
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.relations.RelationEntityList
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(phone: String): Flow<ProfileEntity>
    fun getRelations(): Flow<List<RelationEntityList>>
    fun addFollower(addFollowerBody: AddFollowerBody): Flow<AddFollowerEntity>
    fun deleteFollower(followerId: Int, mobile: String, subscriberId: String): Flow<Boolean>
}