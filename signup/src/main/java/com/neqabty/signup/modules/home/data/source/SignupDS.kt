package com.neqabty.signup.modules.home.data.source

import com.neqabty.signup.modules.home.data.api.SignupApi
import com.neqabty.signup.modules.home.data.model.SignupBody
import com.neqabty.signup.modules.home.data.model.UserModel
import com.neqabty.signup.modules.home.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignupDS @Inject constructor(private val signupApi: SignupApi) {
    suspend fun signup(signupBody: SignupBody): UserModel {
        return signupApi.signup(signupBody)

    }
}