package com.neqabty.chefaa.modules.address.domain.usecases

import com.neqabty.chefaa.modules.address.domain.entities.AddressEntity
import com.neqabty.chefaa.modules.address.domain.repository.AddressRepository
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