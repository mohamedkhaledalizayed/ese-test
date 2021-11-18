package com.neqabty.yodawy.modules.address.domain.interactors

import com.neqabty.yodawy.modules.address.domain.entity.UserEntity
import com.neqabty.yodawy.modules.address.domain.params.GetUserUseCaseParams
import com.neqabty.yodawy.modules.address.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun build(param: GetUserUseCaseParams): Flow<UserEntity> {
        return userRepository.getUser(param)
    }
}