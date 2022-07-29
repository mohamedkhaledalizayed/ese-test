package com.neqabty.healthcare.modules.verifyphone.domain.repository

import com.neqabty.healthcare.modules.verifyphone.data.model.CheckOTPBody
import com.neqabty.healthcare.modules.verifyphone.data.model.CheckPhoneBody
import com.neqabty.healthcare.modules.verifyphone.data.model.SendOTPBody
import com.neqabty.healthcare.modules.verifyphone.domain.entity.sendotp.OTPEntity
import kotlinx.coroutines.flow.Flow

interface VerifyPhoneRepository {
    fun checkAccount(checkPhoneBody: CheckPhoneBody): Flow<String>
    fun sendOTP(sendOTPBody: SendOTPBody): Flow<OTPEntity>
    fun checkOTP(checkOTPBody: CheckOTPBody): Flow<Boolean>
}