package com.neqabty.healthcare.chefaa.address.domain.usecases

import com.neqabty.healthcare.chefaa.address.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserAddressUseCase @Inject constructor(private val addressRepository: AddressRepository) {
    fun build(
        apartment: Int = 0,
        buildingNo: Int = 0,
        floor: Int = 0,
        landMark: String = "",
        lat: String = "",
        long: String = "",
        phone: String = "",
        streetName: String = "",
        title: String = "",
        addressId: Int
    ): Flow<Int> {
        return addressRepository.updateUserAddress(apartment, buildingNo, floor, landMark, lat, long, phone, streetName, title, addressId)
    }
}