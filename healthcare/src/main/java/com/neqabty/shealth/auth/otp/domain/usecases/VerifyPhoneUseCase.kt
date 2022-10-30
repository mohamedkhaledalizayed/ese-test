package com.neqabty.shealth.auth.otp.domain.usecases




import com.neqabty.shealth.auth.otp.data.model.CheckOTPBody
import com.neqabty.shealth.auth.otp.data.model.SendOTPBody
import com.neqabty.shealth.auth.otp.domain.entity.sendotp.OTPEntity
import com.neqabty.shealth.auth.otp.domain.repository.VerifyPhoneRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VerifyPhoneUseCase @Inject constructor(private val verifyPhoneRepository: VerifyPhoneRepository) {

    fun build(sendOTPBody: SendOTPBody): Flow<OTPEntity>{
        return verifyPhoneRepository.sendOTP(sendOTPBody)
    }

    fun build(checkOTPBody: CheckOTPBody): Flow<Boolean>{
        return verifyPhoneRepository.checkOTP(checkOTPBody)
    }

    fun build(token: String): Flow<Boolean>{
        return verifyPhoneRepository.verifyRecaptcha(token)
    }
}
