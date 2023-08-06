package com.neqabty.healthcare.chefaa.address.data.repository

import com.neqabty.healthcare.chefaa.address.data.models.AddressModel
import com.neqabty.healthcare.chefaa.address.data.source.AddressDS
import com.neqabty.healthcare.chefaa.address.domain.entities.AddressEntity
import com.neqabty.healthcare.chefaa.address.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(private val addressDS: AddressDS) :
    AddressRepository {

    override fun getAllUserAddress(userPhone: String): Flow<List<AddressEntity>> {
        return flow {
            emit(addressDS.getAllUserAddress(userPhone).map { it.toAddressEntity() })
        }
    }

    override fun addUserAddress(
        apartment: String,
        buildingNo: String,
        floor: String,
        landMark: String,
        lat: String,
        long: String,
        phone: String,
        streetName: String,
        title: String
    ): Flow<AddressEntity> {
        return flow {
            emit(
                addressDS.addUserAddress(
                    apartment,
                    buildingNo,
                    floor,
                    landMark,
                    lat,
                    long,
                    phone,
                    streetName,
                    title
                ).toAddressEntity()
            )
        }
    }

}

fun AddressModel.toAddressEntity(): AddressEntity {
    return AddressEntity(
        address ?: "",
        apartmentNo ?: "",
        areaAr ?: "",
        areaEn ?: "",
        buildingNo ?: "",
        cityAr ?: "",
        cityEn ?: "",
        floorNo ?: "",
        id,
        landmark ?: "",
        latitude ?: 0.0,
        longitude ?: 0.0,
        title ?: ""
    )
}