package com.neqabty.healthcare.commen.checkaccountstatus.domain.repository

import com.neqabty.healthcare.commen.checkaccountstatus.data.model.CheckPhoneBody
import kotlinx.coroutines.flow.Flow

interface CheckAccountRepository {
    fun checkAccount(checkPhoneBody: CheckPhoneBody): Flow<String>
}