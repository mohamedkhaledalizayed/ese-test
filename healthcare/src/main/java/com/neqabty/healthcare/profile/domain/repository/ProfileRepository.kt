package com.neqabty.healthcare.profile.domain.repository


import com.neqabty.healthcare.profile.data.model.UpdatePasswordBody
import com.neqabty.healthcare.profile.data.model.updatepaswword.UpdatePasswordModel
import com.neqabty.healthcare.profile.domain.entity.MinistryLicenseEntity
import com.neqabty.healthcare.profile.domain.entity.licencestatus.LicenceStatusEntity
import com.neqabty.healthcare.profile.domain.entity.membershipcardstatus.CardStatusEntity
import com.neqabty.healthcare.profile.domain.entity.profile.ProfileEntity
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response

interface ProfileRepository {
    fun getUserProfile(token: String): Flow<ProfileEntity>
    fun membershipCardStatus(): Flow<CardStatusEntity>
    fun licenseStatus(): Flow<LicenceStatusEntity>
    fun uploadMembershipCard(token: String, mobile: String, address: String, year: Int, photo: MultipartBody.Part?): Flow<String>
    fun uploadMinistryLicense(token: String, license: MultipartBody.Part?): Flow<MinistryLicenseEntity>
    fun updatePassword(body: UpdatePasswordBody): Flow<Response<UpdatePasswordModel>>
}