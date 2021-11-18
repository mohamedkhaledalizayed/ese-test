package com.neqabty.yodawy.modules.address.data.model.mappers

import com.neqabty.yodawy.modules.address.data.model.AddresseModel
import com.neqabty.yodawy.modules.address.data.model.UserModel
import com.neqabty.yodawy.modules.address.domain.entity.AddressEntity
import com.neqabty.yodawy.modules.address.domain.entity.UserEntity

fun UserModel.toUserEntity(): UserEntity {
    val adresses = this.addresses.map { it.toAddressEntity() }
    return  UserEntity(
        addresses = adresses,
        neqabtyAddress = this.neqabtyAddress,
        plan = this.plan,
        yodawyId = this.yodawyId
    )
}

private fun AddresseModel.toAddressEntity(): AddressEntity {
    return AddressEntity(
        address = this.address,
        addressName = this.addressName,
        apt = this.apt,
        buildingNumber = this.buildingNumber,
        floor = this.floor,
        longitude = this.geoLocationModel.longitude,
        latitude = this.geoLocationModel.latitude,
        landmark = this.landmark,
        signature = this.signature,
        adressId = this.id
    )
}
