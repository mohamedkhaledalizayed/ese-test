package com.neqabty.healthcare.auth.forgetpassword.domain.usecases

import com.neqabty.healthcare.auth.forgetpassword.data.model.ChangePasswordBody
import com.neqabty.healthcare.auth.forgetpassword.domain.entity.ChangePasswordEntity
import com.neqabty.healthcare.auth.forgetpassword.domain.repository.ForgetPasswordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangePassword @Inject constructor(private val forgetPasswordRepository: ForgetPasswordRepository) {

    fun build(body: ChangePasswordBody, token: String): Flow<ChangePasswordEntity>{
        return forgetPasswordRepository.changePassword(body, token)
    }

}