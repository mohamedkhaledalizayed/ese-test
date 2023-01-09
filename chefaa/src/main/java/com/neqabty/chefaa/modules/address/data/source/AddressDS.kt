package com.neqabty.chefaa.modules.address.data.source



import com.neqabty.chefaa.modules.address.data.api.AddressApi
import com.neqabty.chefaa.modules.address.data.models.AddUserAddressBody
import com.neqabty.chefaa.modules.address.data.models.AddressModel
import com.neqabty.chefaa.modules.address.data.models.GetUserAddressBody
import com.neqabty.chefaa.modules.address.data.models.UpdateUserAddressBody
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

    suspend fun updateUserAddress(apartment: Int = 0, buildingNo: Int = 0, floor: Int = 0, landMark: String = "", lat: String = "", long: String = "", phone: String = "",
                                  streetName: String = "", title: String = "",addressId:Int): Int {
        return addressApi.updateUserAddress(UpdateUserAddressBody(addressId, apartment, buildingNo, floor, landMark, lat, long, phone, streetName, title)).responseData!!
    }
}