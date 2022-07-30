package com.neqabty.healthcare.modules.checkaccountstatus.data.api

import com.neqabty.healthcare.modules.checkaccountstatus.data.model.CheckPhoneBody
import com.neqabty.healthcare.modules.checkaccountstatus.data.model.checkphone.CheckPhoneModel
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckAccountApi {

    @POST("api/accounts/enquiry")
    suspend fun checkAccount(@Body checkPhoneBody: CheckPhoneBody): CheckPhoneModel

}