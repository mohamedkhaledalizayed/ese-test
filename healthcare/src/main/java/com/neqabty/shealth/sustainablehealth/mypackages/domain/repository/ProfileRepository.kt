package com.neqabty.shealth.sustainablehealth.mypackages.domain.repository


import com.neqabty.shealth.sustainablehealth.mypackages.data.model.AddFollowerBody
import com.neqabty.shealth.sustainablehealth.mypackages.domain.entity.addfollower.AddFollowerEntity
import com.neqabty.shealth.sustainablehealth.mypackages.domain.entity.profile.ProfileEntity
import com.neqabty.shealth.sustainablehealth.mypackages.domain.entity.relations.RelationEntityList
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(phone: String): Flow<ProfileEntity>
    fun getRelations(): Flow<List<RelationEntityList>>
    fun addFollower(addFollowerBody: AddFollowerBody): Flow<AddFollowerEntity>
    fun deleteFollower(followerId: Int, subscriberId: String): Flow<Boolean>
}