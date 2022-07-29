package com.neqabty.healthcare.modules.verifyphone.domain.usecases

import com.neqabty.healthcare.modules.verifyphone.data.model.CheckOTPBody
import com.neqabty.healthcare.modules.verifyphone.data.model.CheckPhoneBody
import com.neqabty.healthcare.modules.verifyphone.data.model.SendOTPBody
import com.neqabty.healthcare.modules.verifyphone.domain.entity.sendotp.OTPEntity
import com.neqabty.healthcare.modules.verifyphone.domain.repository.VerifyPhoneRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VerifyPhoneUseCase @Inject constructor(private val verifyPhoneRepository: VerifyPhoneRepository) {

    fun build(checkPhoneBody: CheckPhoneBody): Flow<String>{
        return verifyPhoneRepository.checkAccount(checkPhoneBody)
    }

    fun build(sendOTPBody: SendOTPBody): Flow<OTPEntity>{
        return verifyPhoneRepository.sendOTP(sendOTPBody)
    }

    fun build(checkOTPBody: CheckOTPBody): Flow<Boolean>{
        return verifyPhoneRepository.checkOTP(checkOTPBody)
    }
}