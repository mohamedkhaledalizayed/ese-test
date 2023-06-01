package com.neqabty.healthcare.sustainablehealth.mypackages.data.source

import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.sustainablehealth.mypackages.data.api.ProfileApi
import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.AddFollowerBody
import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.DeleteFollowerBody
import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.InsuranceBody
import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.PackagesBody
import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.addfollower.AddFollowerModel
import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.insurance.InsuranceModel
import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.profile.ProfileModel
import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.relationstypes.RelationModel
import javax.inject.Inject

class ProfileDS @Inject constructor(private val profileApi: ProfileApi, private val sharedPreferences: PreferencesHelper) {

    suspend fun getProfile(phone: String): ProfileModel {
        return profileApi.getProfile(token = "Token ${sharedPreferences.token}", PackagesBody(mobile = phone))
    }

    suspend fun getRelations(): List<RelationModel>{
        return profileApi.getRelations().data.relations
    }

    suspend fun addFollower(addFollowerBody: AddFollowerBody): AddFollowerModel {
        return profileApi.addFollower(token = "Token ${sharedPreferences.token}", addFollowerBody)
    }

    suspend fun deleteFollower(deleteFollowerBody: DeleteFollowerBody): Boolean {
        return profileApi.deleteFollower(token = "Token ${sharedPreferences.token}", deleteFollowerBody).status
    }

    suspend fun getInsurance(insuranceBody: InsuranceBody): InsuranceModel {
        return profileApi.getInsurance(insuranceBody)
    }

}