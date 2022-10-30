package com.neqabty.shealth.sustainablehealth.checkaccountstatus.domain.repository

import com.neqabty.shealth.sustainablehealth.checkaccountstatus.data.model.CheckPhoneBody
import kotlinx.coroutines.flow.Flow

interface CheckAccountRepository {
    fun checkAccount(checkPhoneBody: CheckPhoneBody): Flow<String>
}