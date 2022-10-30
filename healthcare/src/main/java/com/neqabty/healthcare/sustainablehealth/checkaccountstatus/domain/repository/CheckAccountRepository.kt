package com.neqabty.healthcare.sustainablehealth.checkaccountstatus.domain.repository

import com.neqabty.healthcare.sustainablehealth.checkaccountstatus.data.model.CheckPhoneBody
import kotlinx.coroutines.flow.Flow

interface CheckAccountRepository {
    fun checkAccount(checkPhoneBody: CheckPhoneBody): Flow<String>
}