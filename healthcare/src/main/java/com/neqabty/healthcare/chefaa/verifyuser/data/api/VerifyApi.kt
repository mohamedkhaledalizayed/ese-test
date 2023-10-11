package com.neqabty.healthcare.chefaa.verifyuser.data.api

import com.neqabty.healthcare.chefaa.verifyuser.data.model.VerificationModel
import com.neqabty.healthcare.chefaa.verifyuser.data.model.VerifyUserBody
import retrofit2.http.Body
import retrofit2.http.POST

interface VerifyApi {
    @POST("healthcare/api/v1/chefaa/verify-Client")
    suspend fun verifyUser(@Body body: VerifyUserBody): VerificationModel
}