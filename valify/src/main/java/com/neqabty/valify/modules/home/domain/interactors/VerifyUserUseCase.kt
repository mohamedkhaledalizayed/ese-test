package com.neqabty.valify.modules.home.domain.interactors


import com.neqabty.valify.modules.home.data.model.VerifyUserBody
import com.neqabty.valify.modules.home.data.model.VerifyUserResponse
import com.neqabty.valify.modules.home.domain.repository.ValifyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VerifyUserUseCase @Inject constructor(private val repository: ValifyRepository) {
    fun build(verifyUserBody: VerifyUserBody): Flow<VerifyUserResponse> {
        return repository.verifyUser(verifyUserBody)
    }
}