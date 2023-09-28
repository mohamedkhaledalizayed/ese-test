package com.neqabty.healthcare.pharmacymart.address.domain.usecases



import com.neqabty.healthcare.pharmacymart.address.domain.repository.PharmacyMartAddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddAddressUseCase @Inject constructor(private val addressRepository: PharmacyMartAddressRepository) {

    fun build(apartment: String, buildingNo: String, floor: String, landMark: String, lat: String,
              long: String, phone: String, streetName: String, title: String): Flow<Boolean> {
        return addressRepository.addAddress(
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
    }

}