package com.neqabty.healthcare.commen.checkaccountstatus.data.source

import com.neqabty.healthcare.commen.checkaccountstatus.data.api.CheckAccountApi
import com.neqabty.healthcare.commen.checkaccountstatus.data.model.CheckPhoneBody
import javax.inject.Inject

class CheckAccountSource @Inject constructor(private val checkAccountApi: CheckAccountApi) {

    suspend fun checkAccount(checkPhoneBody: CheckPhoneBody): String{
        return checkAccountApi.checkAccount(checkPhoneBody).message
    }

}