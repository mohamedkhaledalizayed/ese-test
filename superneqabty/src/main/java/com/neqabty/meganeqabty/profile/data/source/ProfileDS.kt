package com.neqabty.meganeqabty.profile.data.source


import com.neqabty.core.data.PreferencesHelper
import com.neqabty.meganeqabty.profile.data.api.ProfileApi
import com.neqabty.meganeqabty.profile.data.model.licencestatus.LicenceStatusModel
import com.neqabty.meganeqabty.profile.data.model.membershipcardstatus.MemberShipCardStatus
import com.neqabty.meganeqabty.profile.data.model.ministrylicence.MinistryLicenseModel
import com.neqabty.meganeqabty.profile.data.model.profile.ProfileModel
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileDS @Inject constructor(private val profileApi: ProfileApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun getUserProfile(token: String): ProfileModel {
        return profileApi.getUserProfile(token)
    }

    suspend fun membershipCardStatus(): MemberShipCardStatus {
        return profileApi.membershipCardStatus("Token ${preferencesHelper.token}")
    }

    suspend fun licenseStatus(): LicenceStatusModel {
        return profileApi.licenseStatus("Token ${preferencesHelper.token}")
    }

    suspend fun uploadMembershipCard(token: String, mobile: String, address: String, year: Int, photo: MultipartBody.Part?): String{
        return profileApi.uploadMembershipCard(token, mobile, address, year, photo).statusMessage
    }

    suspend fun uploadMinistryLicense(token: String, license: MultipartBody.Part?): MinistryLicenseModel{
        return profileApi.uploadMinistryLicense(token, license)
    }

}