package com.neqabty.signup.modules.signup.data.source

import com.neqabty.signup.modules.signup.data.api.SignupApi
import com.neqabty.signup.modules.signup.data.model.NeqabtySignupBody
import com.neqabty.signup.modules.signup.data.model.SignupBody
import com.neqabty.signup.modules.signup.data.model.neqabtymember.NeqabtyMemberModel
import com.neqabty.signup.modules.signup.data.model.syndicatemember.UserModel
import com.neqabty.signup.modules.signup.data.model.syndicates.EntityModel
import javax.inject.Inject

class SignupDS @Inject constructor(private val signupApi: SignupApi) {

    suspend fun signup(signupBody: SignupBody): UserModel {
        return signupApi.syndicateMember(signupBody)
    }

    suspend fun signUpNeqabtyMember(body: NeqabtySignupBody): NeqabtyMemberModel {
        return signupApi.signUpNeqabtyMember(body)
    }

    suspend fun getSyndicates(): List<EntityModel> {
        return signupApi.getSyndicates().data.entities
    }

}