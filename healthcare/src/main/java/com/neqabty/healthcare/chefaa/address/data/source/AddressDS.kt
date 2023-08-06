package com.neqabty.healthcare.chefaa.address.data.source


import com.neqabty.healthcare.chefaa.address.data.api.AddressApi
import com.neqabty.healthcare.chefaa.address.data.models.AddUserAddressBody
import com.neqabty.healthcare.chefaa.address.data.models.AddressModel
import com.neqabty.healthcare.chefaa.address.data.models.GetUserAddressBody
import javax.inject.Inject


class AddressDS @Inject constructor(private val addressApi: AddressApi) {

    suspend fun getAllUserAddress(userPhone: String): List<AddressModel> {
        return addressApi.getAllUserAddress(GetUserAddressBody(userPhone)).responseData!!
    }

    suspend fun addUserAddress(
        apartment: String = "", buildingNo: String = "", floor: String = "", landMark: String = "", lat: String = "", long: String = "", phone: String = "",
        streetName: String = "", title: String = ""
    ): AddressModel {
        return addressApi.addUserAddress(
            AddUserAddressBody(
                apartment, buildingNo, floor, landMark, lat, long, phone, streetName, title
            )
        ).responseData!!
    }

}