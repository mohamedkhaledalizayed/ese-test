package com.neqabty.healthcare.auth.signup.data.source



import com.neqabty.healthcare.auth.signup.data.api.SignupApi
import com.neqabty.healthcare.auth.signup.data.model.NeqabtySignupBody
import com.neqabty.healthcare.auth.signup.data.model.UpgradeMemberBody
import com.neqabty.healthcare.auth.signup.data.model.neqabtymember.NeqabtyMemberModel
import com.neqabty.healthcare.auth.signup.data.model.syndicatemember.UserModel
import com.neqabty.healthcare.auth.signup.data.model.syndicates.EntityModel
import com.neqabty.healthcare.core.data.PreferencesHelper
import retrofit2.Response
import javax.inject.Inject

class SignupDS @Inject constructor(private val signupApi: SignupApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun upgradeMember(body: UpgradeMemberBody): NeqabtyMemberModel {
        return signupApi.upgradeMember("Token " + preferencesHelper.token, body).data!!
    }

    suspend fun signupMember(body: NeqabtySignupBody): NeqabtyMemberModel {
        return signupApi.signupMember(body).data!!
    }

    suspend fun getSyndicates(): List<EntityModel> {
        return signupApi.getSyndicates().data.entities
    }

}