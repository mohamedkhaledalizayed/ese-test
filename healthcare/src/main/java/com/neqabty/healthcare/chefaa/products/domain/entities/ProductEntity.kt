package com.neqabty.healthcare.chefaa.products.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductEntity(
    val id: Int = 0,
    val image: String = "",
    val price: Double = 0.0,
    val titleAr: String = "",
    val titleEn: String = "",
    val type: String = "",
    var quantity: Int = 0
) : Parcelable
