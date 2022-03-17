package com.neqabty.valify.modules.home.domain.interactors

import com.neqabty.valify.modules.home.domain.entity.TokenEntity
import com.neqabty.valify.modules.home.domain.repository.ValifyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val valifyRepository: ValifyRepository) {
    fun build(): Flow<TokenEntity> {
        return valifyRepository.getToken()
    }
}