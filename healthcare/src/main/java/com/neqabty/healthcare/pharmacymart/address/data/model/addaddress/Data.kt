package com.neqabty.healthcare.pharmacymart.address.data.model.addaddress


import androidx.annotation.Keep

@Keep
data class Data(
    val apartment: Int,
    val building_no: Int,
    val created_at: String,
    val floor: Int,
    val id: Int,
    val land_mark: String,
    val lat: String,
    val long: String,
    val pharmacy_user_id: Int,
    val street_name: String,
    val title: String,
    val updated_at: String
)