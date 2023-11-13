package com.neqabty.healthcare.suggestions.data.api



import com.neqabty.healthcare.suggestions.data.model.ComplaintBody
import com.neqabty.healthcare.suggestions.data.model.complaints.ComplaintModel
import com.neqabty.healthcare.suggestions.data.model.complaintscategory.CategoriesModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ComplaintsApi {

    @POST("healthcare/api/v1/complaints/addComplaint")
    suspend fun addComplaint(@Header("Authorization") token: String, @Body complaintBody: ComplaintBody): ComplaintModel

    @GET("healthcare/api/v1/complaints/packageLockups")
    suspend fun getComplaintsCategories(@Header("Authorization") token: String): CategoriesModel

}
