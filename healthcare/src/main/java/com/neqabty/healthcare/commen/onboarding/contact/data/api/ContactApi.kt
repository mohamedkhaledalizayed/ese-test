package com.neqabty.healthcare.commen.onboarding.contact.data.api

import com.neqabty.healthcare.commen.onboarding.contact.data.model.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface ContactApi {
    @POST("contact/check_member")
    suspend fun checkMember(@Header("Authorization") token: String, @Body checkMemberRequest: CheckMemberRequest): CheckMemberResponse

    @Headers("Content-Type: multipart/form-data")
    @Multipart
    @POST("contact/create_ocr")
    suspend fun createOCR(@Header("Authorization") token: String, @Part ("nationalId") nationalId: String, @Part ("mobile") mobile: String, @Part front: MultipartBody.Part?, @Part back: MultipartBody.Part?): CreateOCRResponse

    @GET("contact/get_lookups?format=json")
    suspend fun getLookups(): List<GovResponse>

    @POST("contact/submit_client")
    suspend fun submitClient(@Header("Authorization") token: String, @Body submitClientRequest: SubmitClientRequest): SubmitClientResponse

}