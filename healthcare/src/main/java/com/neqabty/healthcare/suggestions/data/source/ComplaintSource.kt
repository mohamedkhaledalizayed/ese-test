package com.neqabty.healthcare.suggestions.data.source



import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.suggestions.data.api.ComplaintsApi
import com.neqabty.healthcare.suggestions.data.model.ComplaintBody
import com.neqabty.healthcare.suggestions.data.model.complaintscategory.Category
import javax.inject.Inject


class ComplaintSource @Inject constructor(private val complaintsApi: ComplaintsApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun addComplaint(complaintBody: ComplaintBody): String {
        return complaintsApi.addComplaint(token = preferencesHelper.token, complaintBody = complaintBody).data.complaintNumber.toString()
    }

    suspend fun getComplaintsCategories(): List<Category> {
        return complaintsApi.getComplaintsCategories(token = preferencesHelper.token).data.categories
    }

}