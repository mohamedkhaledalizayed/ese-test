package com.neqabty.healthcare.chefaa.verifyuser.data.repository

import com.neqabty.healthcare.chefaa.verifyuser.data.datasource.VerificationDS
import com.neqabty.healthcare.chefaa.verifyuser.domain.repository.VerificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerificationRepositoryImpl @Inject constructor(private val verificationDS: VerificationDS): VerificationRepository {

    override fun verifyUser(mobile: String, code: String): Flow<Boolean> {
        return flow {
            emit(verificationDS.verifyUser(mobile = mobile, code = code))
        }
    }

}