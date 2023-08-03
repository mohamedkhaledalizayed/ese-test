package com.neqabty.healthcare.retirement.data.api



import com.neqabty.healthcare.retirement.data.model.pension.PensionModel
import retrofit2.http.*
import retrofit2.Response


interface RetirementApi {

    @GET("verify/member/{user_number}/{national_id}/")
    suspend fun checkValidation(@Path("user_number") userNumber: String, @Path("national_id") nationalId: String): Response<Boolean>

    @GET("members/bank/{id}/")
    suspend fun getPensionInfo(@Path("id") id: String): Response<PensionModel>

}