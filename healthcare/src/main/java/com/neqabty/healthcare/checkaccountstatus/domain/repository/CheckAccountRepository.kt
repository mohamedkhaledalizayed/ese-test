package com.neqabty.healthcare.checkaccountstatus.domain.repository

import com.neqabty.healthcare.checkaccountstatus.data.model.CheckPhoneBody
import kotlinx.coroutines.flow.Flow

interface CheckAccountRepository {
    fun checkAccount(checkPhoneBody: CheckPhoneBody): Flow<String>
}