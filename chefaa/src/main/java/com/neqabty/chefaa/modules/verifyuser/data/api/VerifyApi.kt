package com.neqabty.chefaa.modules.verifyuser.data.api

import com.neqabty.chefaa.modules.verifyuser.data.model.VerificationModel
import com.neqabty.chefaa.modules.verifyuser.data.model.VerifyUserBody
import retrofit2.http.Body
import retrofit2.http.POST

interface VerifyApi {
    @POST("verify-Client")
    suspend fun verifyUser(@Body body: VerifyUserBody): VerificationModel
}