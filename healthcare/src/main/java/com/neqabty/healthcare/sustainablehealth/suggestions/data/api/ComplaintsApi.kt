package com.neqabty.healthcare.sustainablehealth.suggestions.data.api



import com.neqabty.healthcare.sustainablehealth.suggestions.data.model.ComplaintBody
import com.neqabty.healthcare.sustainablehealth.suggestions.data.model.complaints.ComplaintModel
import com.neqabty.healthcare.sustainablehealth.suggestions.data.model.complaintscategory.CategoriesModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ComplaintsApi {

    @POST("complaints/addComplaint")
    suspend fun addComplaint(@Body complaintBody: ComplaintBody): ComplaintModel

    @GET("complaints/packageLockups")
    suspend fun getComplaintsCategories(): CategoriesModel

}
