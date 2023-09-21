package com.neqabty.healthcare.pharmacymart.address.domain.repository


import com.neqabty.healthcare.pharmacymart.address.domain.entity.DeleteAddressEntity
import com.neqabty.healthcare.pharmacymart.address.domain.entity.PharmacyMartAddressesListEntity
import kotlinx.coroutines.flow.Flow

interface PharmacyMartAddressRepository {
    fun getAddresses(): Flow<PharmacyMartAddressesListEntity>
    fun addAddress(
        apartment: String = "",
        buildingNo: String = "",
        floor: String = "",
        landMark: String = "",
        lat: String = "",
        long: String = "",
        phone: String = "",
        streetName: String = "",
        title: String = ""
    ): Flow<Boolean>

    fun deleteAddress(addressId: String): Flow<DeleteAddressEntity>
}
