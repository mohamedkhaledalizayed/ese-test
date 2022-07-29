package com.neqabty.healthcare.modules.verifyphone.data.repository

import com.neqabty.healthcare.modules.verifyphone.data.model.CheckOTPBody
import com.neqabty.healthcare.modules.verifyphone.data.model.CheckPhoneBody
import com.neqabty.healthcare.modules.verifyphone.data.model.SendOTPBody
import com.neqabty.healthcare.modules.verifyphone.data.model.sendotp.OTPModel
import com.neqabty.healthcare.modules.verifyphone.data.source.SendOTPSource
import com.neqabty.healthcare.modules.verifyphone.domain.entity.sendotp.OTPEntity
import com.neqabty.healthcare.modules.verifyphone.domain.repository.VerifyPhoneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyPhoneRepositoryImpl @Inject constructor(private val sendOTPSource: SendOTPSource):
    VerifyPhoneRepository {
    override fun checkAccount(checkPhoneBody: CheckPhoneBody): Flow<String> {
        return flow {
            emit(sendOTPSource.checkPhone(checkPhoneBody))
        }
    }

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