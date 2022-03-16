package com.neqabty.superneqabty.home.domain.interactors

import com.neqabty.superneqabty.home.data.model.VerifyUserBody
import com.neqabty.superneqabty.home.data.model.valify.GetToken
import com.neqabty.superneqabty.home.data.model.verifyuser.VerifyUserResponse
import com.neqabty.superneqabty.home.domain.repository.ValifyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VerifyUserUseCase @Inject constructor(private val repository: ValifyRepository) {
    fun build(verifyUserBody: VerifyUserBody): Flow<VerifyUserResponse> {
        return repository.verifyUser(verifyUserBody)
    }
}