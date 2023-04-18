package com.neqabty.healthcare.chefaa.address.domain.usecases

import com.neqabty.healthcare.chefaa.address.domain.entities.AddressEntity
import com.neqabty.healthcare.chefaa.address.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddUserAddressUseCase @Inject constructor(private val addressRepository: AddressRepository) {
    fun build(
        apartment: String = "",
        buildingNo: String = "",
        floor: String = "",
        landMark: String = "",
        lat: String = "",
        long: String = "",
        phone: String = "",
        streetName: String = "",
        title: String = ""
    ): Flow<AddressEntity> {
        return addressRepository.addUserAddress(apartment, buildingNo, floor, landMark, lat, long, phone, streetName, title)
    }
}