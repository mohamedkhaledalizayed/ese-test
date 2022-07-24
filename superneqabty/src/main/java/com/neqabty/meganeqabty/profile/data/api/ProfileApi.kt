package com.neqabty.meganeqabty.profile.data.api


import com.neqabty.meganeqabty.profile.data.model.cardrequest.CardRequestModel
import com.neqabty.meganeqabty.profile.data.model.ministrylicence.MinistryLicenseModel
import com.neqabty.meganeqabty.profile.data.model.profile.ProfileModel
import okhttp3.MultipartBody
import retrofit2.http.*

interface ProfileApi {

    @GET("api/accounts/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String, @Query("special-format") platform: String = "android"): ProfileModel

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