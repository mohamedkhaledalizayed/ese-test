package com.neqabty.healthcare.modules.profile.domain.entity.profile



data class SubscribedPackage(
    val address: String,
    val backIdImage: String,
    val birthDate: String,
    val downloaded: Int,
    val email: String,
    val frontIdImage: String,
    val fullName: String,
    val id: String,
    val insuranceStatus: Boolean,
    val job: String,
    val mobile: String,
    val nationalId: String,
    val packageName: String,
    val packageX: Package,
    val personalImage: String,
    val status: Int,
    val userNumber: Any
)