package com.neqabty.yodawy.modules

data class Medication(
    val name: String,
    val status: Int,
    val image: String,
    var quantity: Int
)