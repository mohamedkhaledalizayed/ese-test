package com.neqabty.healthcare.commen.profile.domain.interactors



import com.neqabty.healthcare.commen.profile.data.model.UpdatePasswordBody
import com.neqabty.healthcare.commen.profile.data.model.updatepaswword.UpdatePasswordModel
import com.neqabty.healthcare.commen.profile.domain.entity.MinistryLicenseEntity
import com.neqabty.healthcare.commen.profile.domain.entity.licencestatus.LicenceStatusEntity
import com.neqabty.healthcare.commen.profile.domain.entity.membershipcardstatus.CardStatusEntity
import com.neqabty.healthcare.commen.profile.domain.entity.profile.ProfileEntity
import com.neqabty.healthcare.commen.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    fun build(token: String): Flow<ProfileEntity>{
        return profileRepository.getUserProfile(token)
    }

    fun build(): Flow<CardStatusEntity>{
        return profileRepository.membershipCardStatus()
    }

    fun licenseStatus(): Flow<LicenceStatusEntity>{
        return profileRepository.licenseStatus()
    }

    fun build(token: String, mobile: String, address: String, year: Int, photo: MultipartBody.Part?): Flow<String>{
        return profileRepository.uploadMembershipCard(token, mobile, address, year, photo)
    }

    fun build(token: String, license: MultipartBody.Part?): Flow<MinistryLicenseEntity>{
        return profileRepository.uploadMinistryLicense(token, license)
    }

    fun build(body: UpdatePasswordBody): Flow<Response<UpdatePasswordModel>>{
        return profileRepository.updatePassword(body)
    }

}