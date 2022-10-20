package com.neqabty.healthcare.sustainablehealth.suggestions.domain.repository


import com.neqabty.healthcare.sustainablehealth.suggestions.data.model.ComplaintBody
import com.neqabty.healthcare.sustainablehealth.suggestions.domain.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow


interface ComplaintRepository {

    fun addComplaint(complaintBody: ComplaintBody): Flow<String>
    fun getComplaintsCategories(): Flow<List<CategoryEntity>>

}