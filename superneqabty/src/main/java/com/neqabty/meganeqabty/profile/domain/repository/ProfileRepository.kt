package com.neqabty.meganeqabty.profile.domain.repository


import com.neqabty.meganeqabty.profile.domain.entity.MinistryLicenseEntity
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ProfileRepository {
    fun uploadMembershipCard(token: String, mobile: String, address: String, year: Int, photo: MultipartBody.Part?): Flow<String>
    fun uploadMinistryLicense(token: String, license: MultipartBody.Part?): Flow<MinistryLicenseEntity>
}