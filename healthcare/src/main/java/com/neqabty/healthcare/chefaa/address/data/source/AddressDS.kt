package com.neqabty.healthcare.chefaa.address.data.source


import com.neqabty.healthcare.chefaa.address.data.api.AddressApi
import com.neqabty.healthcare.chefaa.address.data.models.AddUserAddressBody
import com.neqabty.healthcare.chefaa.address.data.models.AddressModel
import com.neqabty.healthcare.chefaa.address.data.models.GetUserAddressBody
import com.neqabty.healthcare.core.data.PreferencesHelper
import javax.inject.Inject


class AddressDS @Inject constructor(private val addressApi: AddressApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun getAllUserAddress(userPhone: String): List<AddressModel> {
        return addressApi.getAllUserAddress(token = preferencesHelper.token, GetUserAddressBody(userPhone)).responseData!!
    }

    suspend fun addUserAddress(
        apartment: String = "", buildingNo: String = "", floor: String = "", landMark: String = "", lat: String = "", long: String = "", phone: String = "",
        streetName: String = "", title: String = ""
    ): AddressModel {
        return addressApi.addUserAddress(token = preferencesHelper.token,
            AddUserAddressBody(
                apartment, buildingNo, floor, landMark, lat, long, phone, streetName, title
            )
        ).responseData!!
    }

}