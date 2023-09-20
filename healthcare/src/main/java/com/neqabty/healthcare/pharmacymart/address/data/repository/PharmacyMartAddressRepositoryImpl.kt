package com.neqabty.healthcare.pharmacymart.address.data.repository


import com.neqabty.healthcare.pharmacymart.address.data.datasourse.PharmacyMartAddressDS
import com.neqabty.healthcare.pharmacymart.address.data.model.addresses.Addresse
import com.neqabty.healthcare.pharmacymart.address.data.model.addresses.PharmacyMartAddressesModel
import com.neqabty.healthcare.pharmacymart.address.domain.entity.PharmacyMartAddressEntity
import com.neqabty.healthcare.pharmacymart.address.domain.entity.PharmacyMartAddressesListEntity
import com.neqabty.healthcare.pharmacymart.address.domain.repository.PharmacyMartAddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PharmacyMartAddressRepositoryImpl @Inject constructor(private val addressDS: PharmacyMartAddressDS) :
    PharmacyMartAddressRepository {

    override fun getAddresses(): Flow<PharmacyMartAddressesListEntity> {
        return flow {
            emit(addressDS.getAddresses().toPharmacyMartAddressesListEntity())
        }
    }

    private fun PharmacyMartAddressesModel.toPharmacyMartAddressesListEntity(): PharmacyMartAddressesListEntity{
        return PharmacyMartAddressesListEntity(
            data = data.addresses.map { it.toPharmacyMartAddressEntity() },
            message = message,
            status = status,
            status_code = status_code
        )
    }

    private fun Addresse.toPharmacyMartAddressEntity(): PharmacyMartAddressEntity{
        return PharmacyMartAddressEntity(
            address = apartment ?: "",
            buildingNo = building_no ?: "",
            streetName = street_name ?: "",
            id = id,
            title = title ?: "",
            apartmentNo = apartment ?: "",
            areaAr = "",
            cityAr = "",
            floorNo = floor ?: "",
            landmark = land_mark ?: "",
            latitude = lat ?: "",
            longitude = long ?: ""
        )
    }

    override fun addAddress(
        apartment: String,
        buildingNo: String,
        floor: String,
        landMark: String,
        lat: String,
        long: String,
        phone: String,
        streetName: String,
        title: String
    ): Flow<Boolean> {
        return flow {
            emit(
                addressDS.addAddress(
                    apartment = apartment,
                    buildingNo = buildingNo,
                    floor = floor,
                    landMark = landMark,
                    lat = lat,
                    long = long,
                    phone = phone,
                    streetName = streetName,
                    title = title
                )
            )
        }
    }

}