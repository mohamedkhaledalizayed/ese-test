package com.neqabty.healthcare.pharmacymart.address.domain.usecases


import com.neqabty.healthcare.pharmacymart.address.domain.entity.PharmacyMartAddressesListEntity
import com.neqabty.healthcare.pharmacymart.address.domain.repository.PharmacyMartAddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAddressesUseCase @Inject constructor(private val addressRepository: PharmacyMartAddressRepository) {
    fun build(): Flow<PharmacyMartAddressesListEntity> {
        return addressRepository.getAddresses()
    }
}