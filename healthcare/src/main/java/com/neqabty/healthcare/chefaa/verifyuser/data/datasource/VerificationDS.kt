package com.neqabty.healthcare.chefaa.verifyuser.data.datasource

import com.neqabty.healthcare.chefaa.verifyuser.data.api.VerifyApi
import com.neqabty.healthcare.chefaa.verifyuser.data.model.VerifyUserBody
import javax.inject.Inject

class VerificationDS @Inject constructor(private val verifyApi: VerifyApi) {

    suspend fun verifyUser(mobile: String, code: String): Boolean{
        return verifyApi.verifyUser(VerifyUserBody(mobile = mobile, verificationCode = code )).status
    }

}