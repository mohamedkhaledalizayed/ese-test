package com.neqabty.healthcare.modules.profile.domain.repository


import com.neqabty.healthcare.modules.profile.data.model.AddFollowerBody
import com.neqabty.healthcare.modules.profile.domain.entity.profile.ProfileEntity
import com.neqabty.healthcare.modules.profile.domain.entity.relations.RelationEntityList
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(phone: String): Flow<ProfileEntity>
    fun getRelations(): Flow<List<RelationEntityList>>
    fun addFollower(addFollowerBody: AddFollowerBody): Flow<String>
    fun deleteFollower(followerId: Int, subscriberId: String): Flow<Boolean>
}