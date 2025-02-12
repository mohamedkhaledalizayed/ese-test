package com.neqabty.yodawy.modules.address.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddresseModel(
    @SerializedName("Address")
    val address: String,
    @SerializedName("AddressName")
    val addressName: String,
    @SerializedName("Apt")
    val apt: String,
    @SerializedName("BuildingNumber")
    val buildingNumber: String,
    @SerializedName("Floor")
    val floor: String,
    @SerializedName("GeoLocation")
    val geoLocationModel: GeoLocationModel,
    @SerializedName("Id")
    val id: String,
    @SerializedName("signature")
    val signature: String,
    @SerializedName("Landmark")
    val landmark: String
): Parcelable