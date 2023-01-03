package com.neqabty.healthcare.auth.forgetpassword.data.repository

import com.neqabty.healthcare.auth.forgetpassword.data.datasource.ForgetPasswordDS
import com.neqabty.healthcare.auth.forgetpassword.data.model.*
import com.neqabty.healthcare.auth.forgetpassword.domain.entity.ChangePasswordEntity
import com.neqabty.healthcare.auth.forgetpassword.domain.entity.CheckOTPEntity
import com.neqabty.healthcare.auth.forgetpassword.domain.entity.SendOTPEntity
import com.neqabty.healthcare.auth.forgetpassword.domain.repository.ForgetPasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ForgetPasswordRepositoryImpl @Inject constructor(private val forgetPasswordDS: ForgetPasswordDS): ForgetPasswordRepository {

    override fun sendOTP(body: SendOTPBody): Flow<SendOTPEntity> {
        return flow {
            emit(forgetPasswordDS.sendOTP(body).toSendOTPEntity())
        }
    }

    override fun checkOTP(body: CheckOTPBody): Flow<CheckOTPEntity> {
        return flow {
            emit(forgetPasswordDS.checkOTP(body).toCheckOTPEntity())
        }
    }

    override fun changePassword(body: ChangePasswordBody, token: String): Flow<ChangePasswordEntity> {
        return flow {
            emit(forgetPasswordDS.changePassword(body, token).toChangePasswordEntity())
        }
    }

}

private fun SendOTPModel.toSendOTPEntity(): SendOTPEntity{
    return SendOTPEntity(
        status = status,
        message = message
    )
}

private fun CheckOTPModel.toCheckOTPEntity(): CheckOTPEntity{
    return CheckOTPEntity(
        status = status,
        message = message,
        token = token
    )
}

private fun ChangePasswordModel.toChangePasswordEntity(): ChangePasswordEntity{
    return ChangePasswordEntity(
        status = status,
        message = message
    )
}
