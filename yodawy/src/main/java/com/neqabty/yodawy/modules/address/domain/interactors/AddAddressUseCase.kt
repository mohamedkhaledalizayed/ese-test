package com.neqabty.yodawy.modules.address.domain.interactors

import com.neqabty.yodawy.modules.address.domain.params.AddAddressUseCaseParams
import com.neqabty.yodawy.modules.address.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddAddressUseCase @Inject constructor(private val repository: UserRepository) {
    fun build(params:AddAddressUseCaseParams): Flow<String>{
        return repository.addAddress(params)
    }
}