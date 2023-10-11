package com.neqabty.healthcare.onboarding.contact.data.api

import com.neqabty.healthcare.onboarding.contact.data.model.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface ContactApi {
    @POST("api/contact/check_member")
    suspend fun checkMember(@Header("Authorization") token: String, @Body checkMemberRequest: CheckMemberRequest): CheckMemberResponse

    @Multipart
    @POST("api/contact/create_ocr")
    suspend fun createOCR(@Header("Authorization") token: String, @Part ("nationalId") nationalId: Long, @Part ("mobile") mobile: Long, @Part front: MultipartBody.Part?, @Part back: MultipartBody.Part?): CreateOCRResponse

    @GET("api/contact/get_lookups?format=json")
    suspend fun getLookups(): List<GovResponse>

    @POST("api/contact/submit_client")
    suspend fun submitClient(@Header("Authorization") token: String, @Body submitClientRequest: SubmitClientRequest): SubmitClientResponse

    @POST("api/contact/get_instalment")
    suspend fun getInstallments(@Header("Authorization") token: String, @Body installmentsRequest: InstallmentsRequest): InstallmentsResponse

    @POST("api/contact/submit_invoice")
    suspend fun submitInvoice(@Header("Authorization") token: String, @Body invoiceRequest: InvoiceRequest): InvoiceResponse

}