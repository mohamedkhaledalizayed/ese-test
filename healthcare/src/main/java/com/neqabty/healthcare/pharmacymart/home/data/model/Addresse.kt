package com.neqabty.healthcare.pharmacymart.home.data.model


import androidx.annotation.Keep

@Keep
data class Addresse(
    val apartment: String,
    val building_no: String,
    val created_at: String,
    val floor: String,
    val id: Int,
    val land_mark: String,
    val lat: String,
    val long: String,
    val pharmacy_user_id: Int,
    val street_name: String,
    val title: String,
    val updated_at: String
)