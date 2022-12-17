package com.neqabty.chefaa.modules.verifyuser.domain.interactors


import com.neqabty.chefaa.modules.verifyuser.domain.repository.VerificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VerificationUseCase @Inject constructor(private val verificationRepository: VerificationRepository) {

    fun build(mobile: String, code: String): Flow<Boolean>{
        return verificationRepository.verifyUser(mobile = mobile, code = code)
    }

}