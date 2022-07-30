package com.neqabty.healthcare.modules.checkaccountstatus.domain.usecases

import com.neqabty.healthcare.modules.checkaccountstatus.data.model.CheckPhoneBody
import com.neqabty.healthcare.modules.checkaccountstatus.domain.repository.CheckAccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckAccountUseCase @Inject constructor(private val checkAccountRepository: CheckAccountRepository) {

    fun build(checkPhoneBody: CheckPhoneBody): Flow<String>{
        return checkAccountRepository.checkAccount(checkPhoneBody)
    }

}