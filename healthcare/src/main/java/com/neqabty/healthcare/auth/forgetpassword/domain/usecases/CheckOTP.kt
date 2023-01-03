package com.neqabty.healthcare.auth.forgetpassword.domain.usecases

import com.neqabty.healthcare.auth.forgetpassword.data.model.CheckOTPBody
import com.neqabty.healthcare.auth.forgetpassword.domain.entity.CheckOTPEntity
import com.neqabty.healthcare.auth.forgetpassword.domain.repository.ForgetPasswordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckOTP @Inject constructor(private val forgetPasswordRepository: ForgetPasswordRepository) {

    fun build(body: CheckOTPBody): Flow<CheckOTPEntity> {
        return forgetPasswordRepository.checkOTP(body)
    }

}