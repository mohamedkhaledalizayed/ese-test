package com.neqabty.healthcare.modules.suggestions.data.source



import com.neqabty.healthcare.modules.suggestions.data.api.ComplaintsApi
import com.neqabty.healthcare.modules.suggestions.data.model.ComplaintBody
import com.neqabty.healthcare.modules.suggestions.data.model.complaintscategory.Category
import javax.inject.Inject


class ComplaintSource @Inject constructor(private val complaintsApi: ComplaintsApi) {

    suspend fun addComplaint(complaintBody: ComplaintBody): Boolean {
        return complaintsApi.addComplaint(complaintBody = complaintBody).status
    }

    suspend fun getComplaintsCategories(): List<Category> {
        return complaintsApi.getComplaintsCategories().data.categories
    }

}