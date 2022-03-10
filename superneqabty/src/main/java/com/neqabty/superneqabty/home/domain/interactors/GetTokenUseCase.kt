package com.neqabty.superneqabty.home.domain.interactors

import com.neqabty.superneqabty.home.data.model.valify.GetToken
import com.neqabty.superneqabty.home.domain.repository.ValifyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val repository: ValifyRepository) {
    fun build(): Flow<GetToken> {
        return repository.getToken()
    }
}