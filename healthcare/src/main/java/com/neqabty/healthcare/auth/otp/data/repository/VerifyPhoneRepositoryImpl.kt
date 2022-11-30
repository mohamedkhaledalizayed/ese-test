package com.neqabty.healthcare.auth.otp.data.repository



import com.neqabty.healthcare.auth.otp.data.datasource.SendOTPSource
import com.neqabty.healthcare.auth.otp.data.model.CheckOTPBody
import com.neqabty.healthcare.auth.otp.data.model.SendOTPBody
import com.neqabty.healthcare.auth.otp.data.model.sendotp.OTPModel
import com.neqabty.healthcare.auth.otp.domain.entity.sendotp.OTPEntity
import com.neqabty.healthcare.auth.otp.domain.repository.VerifyPhoneRepository
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

    override fun checkOTP(checkOTPBody: CheckOTPBody): Flow<Boolean> {
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