package com.neqabty.healthcare.chefaa.address.domain.usecases

import com.neqabty.healthcare.chefaa.address.domain.entities.AddressEntity
import com.neqabty.healthcare.chefaa.address.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUserAddressUseCase @Inject constructor(private val addressRepository: AddressRepository) {
    fun build(userPhone:String): Flow<List<AddressEntity>> {
        return addressRepository.getAllUserAddress(userPhone)
    }
}