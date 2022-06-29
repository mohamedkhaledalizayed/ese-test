package com.neqabty.meganeqabty.profile.data.api


import com.neqabty.meganeqabty.profile.data.model.cardrequest.CardRequestModel
import com.neqabty.meganeqabty.profile.data.model.ministrylicence.MinistryLicenseModel
import okhttp3.MultipartBody
import retrofit2.http.*

interface ProfileApi {

    @Multipart
    @POST("api/membership_card_requests")
    suspend fun getUserProfile(@Header("Authorization") token: String): String

    @Multipart
    @POST("api/membership_card_requests")
    suspend fun uploadMembershipCard(@Header("Authorization") token: String,
                                     @Part("mobile") mobile: String,
                                     @Part("address") address: String,
                                     @Part("card_year") card_year: Int,
                                     @Part photo: MultipartBody.Part?,
    ): CardRequestModel

    @Multipart
    @POST("api/license_requests")
    suspend fun uploadMinistryLicense(@Header("Authorization") token: String, @Part license: MultipartBody.Part?): MinistryLicenseModel

}