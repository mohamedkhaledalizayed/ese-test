package com.neqabty.healthcare.commen.packages.packageslist.domain.entity



data class PackagesEntityList(
    val data: List<PackagesEntity>,
    val message: String,
    val status: Boolean
)