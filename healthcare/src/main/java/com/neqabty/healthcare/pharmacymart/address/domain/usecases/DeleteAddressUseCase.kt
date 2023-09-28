package com.neqabty.healthcare.pharmacymart.address.domain.usecases


import com.neqabty.healthcare.pharmacymart.address.domain.entity.DeleteAddressEntity
import com.neqabty.healthcare.pharmacymart.address.domain.repository.PharmacyMartAddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAddressUseCase @Inject constructor(private val addressRepository: PharmacyMartAddressRepository) {
    fun build(addressId: String): Flow<DeleteAddressEntity> {
        return addressRepository.deleteAddress(addressId)
    }
}