package com.neqabty.healthcare.sustainablehealth.suggestions.data.source



import com.neqabty.healthcare.sustainablehealth.suggestions.data.api.ComplaintsApi
import com.neqabty.healthcare.sustainablehealth.suggestions.data.model.ComplaintBody
import com.neqabty.healthcare.sustainablehealth.suggestions.data.model.complaintscategory.Category
import javax.inject.Inject


class ComplaintSource @Inject constructor(private val complaintsApi: ComplaintsApi) {

    suspend fun addComplaint(complaintBody: ComplaintBody): String {
        return complaintsApi.addComplaint(complaintBody = complaintBody).data.complaintNumber.toString()
    }

    suspend fun getComplaintsCategories(): List<Category> {
        return complaintsApi.getComplaintsCategories().data.categories
    }

}