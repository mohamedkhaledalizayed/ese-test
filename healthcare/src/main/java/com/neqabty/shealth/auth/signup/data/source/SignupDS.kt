package com.neqabty.shealth.auth.signup.data.source



import com.neqabty.shealth.auth.signup.data.api.SignupApi
import com.neqabty.shealth.auth.signup.data.model.NeqabtySignupBody
import com.neqabty.shealth.auth.signup.data.model.neqabtymember.NeqabtyMemberModel
import javax.inject.Inject

class SignupDS @Inject constructor(private val signupApi: SignupApi) {

    suspend fun signUpNeqabtyMember(body: NeqabtySignupBody): NeqabtyMemberModel {
        return signupApi.signUpNeqabtyMember(body)
    }

}