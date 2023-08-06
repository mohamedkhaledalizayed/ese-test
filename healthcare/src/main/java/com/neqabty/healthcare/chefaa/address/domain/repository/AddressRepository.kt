package com.neqabty.healthcare.chefaa.address.domain.repository

import com.neqabty.healthcare.chefaa.address.domain.entities.AddressEntity
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

}