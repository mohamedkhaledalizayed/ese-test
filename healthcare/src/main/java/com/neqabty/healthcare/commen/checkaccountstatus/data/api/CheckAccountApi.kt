package com.neqabty.healthcare.commen.checkaccountstatus.data.api

import com.neqabty.healthcare.commen.checkaccountstatus.data.model.CheckPhoneBody
import com.neqabty.healthcare.commen.checkaccountstatus.data.model.checkphone.CheckPhoneModel
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckAccountApi {

    @POST("accounts/enquiry")
    suspend fun checkAccount(@Body checkPhoneBody: CheckPhoneBody): CheckPhoneModel

}