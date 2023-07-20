package com.neqabty.healthcare.checkaccountstatus.data.repository

import com.neqabty.healthcare.checkaccountstatus.data.model.CheckPhoneBody
import com.neqabty.healthcare.checkaccountstatus.data.source.CheckAccountSource
import com.neqabty.healthcare.checkaccountstatus.domain.repository.CheckAccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckAccountRepositoryImpl @Inject constructor(private val checkAccountSource: CheckAccountSource):
    CheckAccountRepository {
    override fun checkAccount(checkPhoneBody: CheckPhoneBody): Flow<String> {
        return flow {
            emit(checkAccountSource.checkAccount(checkPhoneBody))
        }
    }
}