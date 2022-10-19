package com.neqabty.chefaa.modules.address.data.repository

import com.neqabty.chefaa.modules.address.data.models.AddressModel
import com.neqabty.chefaa.modules.address.data.source.AddressDS
import com.neqabty.chefaa.modules.address.domain.entities.AddressEntity
import com.neqabty.chefaa.modules.address.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(private val addressDS: AddressDS): AddressRepository {
    override fun getAllUserAddress(userPhone: String): Flow<List<AddressEntity>> {
        return flow {
            emit(addressDS.getAllUserAddress(userPhone).map { it.toAddressEntity() })
        }
    }

    override fun addUserAddress(
        apartment: Int,
        buildingNo: Int,
        floor: Int,
        landMark: String,
        lat: String,
        long: String,
        phone: String,
        streetName: String,
        title: String
    ): Flow<AddressEntity> {
        return flow {
            emit(addressDS.addUserAddress(apartment, buildingNo, floor, landMark, lat, long, phone, streetName, title).toAddressEntity())
        }
    }

    override fun updateUserAddress(
        apartment: Int,
        buildingNo: Int,
        floor: Int,
        landMark: String,
        lat: String,
        long: String,
        phone: String,
        streetName: String,
        title: String,
        addressId: Int
    ): Flow<Int> {
        return flow {
            emit(addressDS.updateUserAddress(apartment, buildingNo, floor, landMark, lat, long, phone, streetName, title, addressId))
        }
    }
}

fun AddressModel.toAddressEntity(): AddressEntity {
    return AddressEntity(address, apartmentNo, areaAr, areaEn, buildingNo, cityAr, cityEn, floorNo, id, landmark, latitude, longitude, title)
}
