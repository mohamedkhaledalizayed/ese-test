package com.neqabty.healthcare.pharmacymart.address.data.datasourse



import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.pharmacymart.address.data.api.PharmacyMartAddressApi
import com.neqabty.healthcare.pharmacymart.address.data.model.AddAddressBody
import com.neqabty.healthcare.pharmacymart.address.data.model.DeleteAddressBody
import com.neqabty.healthcare.pharmacymart.address.data.model.GetAddressesBody
import com.neqabty.healthcare.pharmacymart.address.data.model.addresses.PharmacyMartAddressesModel
import com.neqabty.healthcare.pharmacymart.address.data.model.deleteaddress.DeleteAddressModel
import javax.inject.Inject


class PharmacyMartAddressDS @Inject constructor(private val addressApi: PharmacyMartAddressApi,
                                                private val sharedPreferencesHelper: PreferencesHelper) {

    suspend fun getAddresses(): PharmacyMartAddressesModel {
        return addressApi.getAddresses(token = sharedPreferencesHelper.token, GetAddressesBody(sharedPreferencesHelper.mobile))
    }

    suspend fun addAddress(
        apartment: String, buildingNo: String, floor: String, landMark: String, lat: String,
        long: String, phone: String, streetName: String, title: String
    ): Boolean {
        return addressApi.addAddress(
            token = sharedPreferencesHelper.token,
            AddAddressBody(
                apartment = apartment,
                buildingNo = buildingNo,
                floor = floor,
                landMark = landMark,
                lat = lat,
                long = long,
                mobile = phone,
                streetName = streetName,
                title = title
            )
        ).status
    }

    suspend fun deleteAddress(addressId: String): DeleteAddressModel {
        return addressApi.deleteAddress(token = sharedPreferencesHelper.token, DeleteAddressBody(addressId = addressId))
    }
}