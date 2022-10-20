package com.neqabty.healthcare.sustainablehealth.mypackages.data.source

import com.neqabty.healthcare.sustainablehealth.mypackages.data.api.ProfileApi
import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.AddFollowerBody
import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.DeleteFollowerBody
import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.addfollower.AddFollowerModel
import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.profile.ProfileModel
import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.relationstypes.RelationModel
import javax.inject.Inject

class ProfileDS @Inject constructor(private val profileApi: ProfileApi) {

    suspend fun getProfile(phone: String): ProfileModel {
        return profileApi.getProfile(phone)
    }

    suspend fun getRelations(): List<RelationModel>{
        return profileApi.getRelations().data.relations
    }

    suspend fun addFollower(addFollowerBody: AddFollowerBody): AddFollowerModel {
        return profileApi.addFollower(addFollowerBody)
    }

    suspend fun deleteFollower(deleteFollowerBody: DeleteFollowerBody): Boolean {
        return profileApi.deleteFollower(deleteFollowerBody).status
    }

}