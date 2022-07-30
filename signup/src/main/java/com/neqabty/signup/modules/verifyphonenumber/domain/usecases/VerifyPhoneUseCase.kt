package com.neqabty.signup.modules.verifyphonenumber.domain.usecases



import com.neqabty.signup.modules.verifyphonenumber.data.model.CheckOTPBody
import com.neqabty.signup.modules.verifyphonenumber.data.model.SendOTPBody
import com.neqabty.signup.modules.verifyphonenumber.domain.entity.sendotp.OTPEntity
import com.neqabty.signup.modules.verifyphonenumber.domain.repository.VerifyPhoneRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VerifyPhoneUseCase @Inject constructor(private val verifyPhoneRepository: VerifyPhoneRepository) {

    fun build(sendOTPBody: SendOTPBody): Flow<OTPEntity>{
        return verifyPhoneRepository.sendOTP(sendOTPBody)
    }

    fun build(checkOTPBody: CheckOTPBody): Flow<Boolean>{
        return verifyPhoneRepository.checkOTP(checkOTPBody)
    }
}