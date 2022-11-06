package com.neqabty.chefaa.modules.address.domain.repository

import com.neqabty.chefaa.modules.address.domain.entities.AddressEntity
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    fun getAllUserAddress(userPhone: String): Flow<List<AddressEntity>>

    fun addUserAddress(
        apartment: String = "",
        buildingNo: String = "",
        floor: String = "",
        landMark: String = "",
        lat: String = "",
        long: String = "",
        phone: String = "",
        streetName: String = "",
        title: String = ""
    ): Flow<AddressEntity>

    fun updateUserAddress(
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
    ): Flow<Int>
}