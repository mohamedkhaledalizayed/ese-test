package com.neqabty.healthcare.chefaa.home.data.model


import androidx.annotation.Keep

@Keep
data class Addresses(
    val apartment: String,
    val building_no: String,
    val floor: String,
    val id: Int,
    val land_mark: String,
    val lat: String,
    val long: String,
    val phone: String,
    val street_name: String,
    val title: String
)