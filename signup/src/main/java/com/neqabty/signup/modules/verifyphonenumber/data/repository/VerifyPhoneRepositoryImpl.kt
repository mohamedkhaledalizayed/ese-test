package com.neqabty.signup.modules.verifyphonenumber.data.repository


import com.neqabty.signup.modules.verifyphonenumber.data.datasource.SendOTPSource
import com.neqabty.signup.modules.verifyphonenumber.data.model.CheckOTPBody
import com.neqabty.signup.modules.verifyphonenumber.data.model.SendOTPBody
import com.neqabty.signup.modules.verifyphonenumber.data.model.sendotp.OTPModel
import com.neqabty.signup.modules.verifyphonenumber.domain.entity.sendotp.OTPEntity
import com.neqabty.signup.modules.verifyphonenumber.domain.repository.VerifyPhoneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyPhoneRepositoryImpl @Inject constructor(private val sendOTPSource: SendOTPSource):
    VerifyPhoneRepository {

    override fun sendOTP(sendOTPBody: SendOTPBody): Flow<OTPEntity> {
        return flow {
            emit(sendOTPSource.sendOTP(sendOTPBody).toOTPEntity())
        }
    }

    override fun checkOTP(checkOTPBody: CheckOTPBody): Flow<String> {
        return flow {
            emit(sendOTPSource.checkOTP(checkOTPBody))
        }
    }
}

private fun OTPModel.toOTPEntity(): OTPEntity{
    return OTPEntity(
        id = id,
        otp = otp,
        phoneNumber = phoneNumber
    )
}