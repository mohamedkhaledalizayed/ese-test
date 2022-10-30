package com.neqabty.shealth.auth.otp.data.repository



import com.neqabty.shealth.auth.otp.data.datasource.SendOTPSource
import com.neqabty.shealth.auth.otp.data.model.CheckOTPBody
import com.neqabty.shealth.auth.otp.data.model.SendOTPBody
import com.neqabty.shealth.auth.otp.data.model.sendotp.OTPModel
import com.neqabty.shealth.auth.otp.domain.entity.sendotp.OTPEntity
import com.neqabty.shealth.auth.otp.domain.repository.VerifyPhoneRepository
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

    override fun verifyRecaptcha(token: String): Flow<Boolean> {
        return flow {
            emit(sendOTPSource.verifyRecaptcha(token))
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