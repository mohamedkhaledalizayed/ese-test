package com.neqabty.healthcare.auth.forgetpassword.domain.usecases

import com.neqabty.healthcare.auth.forgetpassword.data.model.SendOTPBody
import com.neqabty.healthcare.auth.forgetpassword.domain.entity.SendOTPEntity
import com.neqabty.healthcare.auth.forgetpassword.domain.repository.ForgetPasswordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendOTP @Inject constructor(private val forgetPasswordRepository: ForgetPasswordRepository) {

    fun build(body: SendOTPBody): Flow<SendOTPEntity>{
        return forgetPasswordRepository.sendOTP(body)
    }
}