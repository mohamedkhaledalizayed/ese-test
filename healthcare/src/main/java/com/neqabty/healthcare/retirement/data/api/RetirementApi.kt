package com.neqabty.healthcare.retirement.data.api



import com.neqabty.healthcare.retirement.data.model.inheritor.InheritorModel
import com.neqabty.healthcare.retirement.data.model.pension.PensionModel
import com.neqabty.healthcare.retirement.data.model.validation.ValidationModel
import retrofit2.http.*
import retrofit2.Response


interface RetirementApi {

    @GET("verify/member/{user_number}/{national_id}")
    suspend fun checkValidation(@Header("Authorization") token: String, @Path("user_number") userNumber: String, @Path("national_id") nationalId: String): Response<ValidationModel>

    @GET("members/bank/{id}")
    suspend fun getPensionInfo(@Header("Authorization") token: String, @Path("id") id: String): Response<PensionModel>

    @GET("members/visa/{id}")
    suspend fun getInheritor(@Header("Authorization") token: String, @Path("id") id: String): Response<InheritorModel>

}