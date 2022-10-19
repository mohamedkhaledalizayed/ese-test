package com.neqabty.chefaa.modules.address.domain.usecases

import com.neqabty.chefaa.modules.address.domain.entities.AddressEntity
import com.neqabty.chefaa.modules.address.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUserAddressUseCase @Inject constructor(private val addressRepository: AddressRepository) {
    fun build(userPhone:String): Flow<List<AddressEntity>> {
        return addressRepository.getAllUserAddress(userPhone)
    }
}