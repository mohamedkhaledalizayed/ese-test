package com.neqabty.healthcare.modules.checkaccountstatus.domain.repository

import com.neqabty.healthcare.modules.checkaccountstatus.data.model.CheckPhoneBody
import kotlinx.coroutines.flow.Flow

interface CheckAccountRepository {
    fun checkAccount(checkPhoneBody: CheckPhoneBody): Flow<String>
}