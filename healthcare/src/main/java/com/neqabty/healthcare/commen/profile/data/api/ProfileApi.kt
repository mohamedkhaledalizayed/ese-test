package com.neqabty.healthcare.commen.profile.data.api


import com.neqabty.healthcare.commen.profile.data.model.UpdatePasswordBody
import com.neqabty.healthcare.commen.profile.data.model.cardrequest.CardRequestModel
import com.neqabty.healthcare.commen.profile.data.model.licencestatus.LicenceStatusModel
import com.neqabty.healthcare.commen.profile.data.model.membershipcardstatus.MemberShipCardStatus
import com.neqabty.healthcare.commen.profile.data.model.ministrylicence.MinistryLicenseModel
import com.neqabty.healthcare.commen.profile.data.model.profile.ProfileModel
import com.neqabty.healthcare.commen.profile.data.model.updatepaswword.UpdatePasswordModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ProfileApi {

    @GET("accounts/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String, @Query("special-format") platform: String = "android"): ProfileModel

    @GET("membership_card_requests/check_request_status")
    suspend fun membershipCardStatus(@Header("Authorization") token: String) : MemberShipCardStatus

    @GET("license_requests/check_request_status")
    suspend fun licenseStatus(@Header("Authorization") token: String) : LicenceStatusModel

    @Multipart
    @POST("membership_card_requests")
    suspend fun uploadMembershipCard(@Header("Authorization") token: String,
                                     @Part("mobile") mobile: String,
                                     @Part("address") address: String,
                                     @Part("card_year") card_year: Int,
                                     @Part photo: MultipartBody.Part?,
    ): CardRequestModel

    @Multipart
    @POST("license_requests")
    suspend fun uploadMinistryLicense(@Header("Authorization") token: String, @Part license: MultipartBody.Part?): MinistryLicenseModel

    @POST("accounts/change_password")
    suspend fun updatePassword(@Header("Authorization") token: String, @Body body: UpdatePasswordBody): Response<UpdatePasswordModel>

}