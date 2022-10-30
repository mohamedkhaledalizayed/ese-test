package com.neqabty.shealth.sustainablehealth.suggestions.domain.repository


import com.neqabty.shealth.sustainablehealth.suggestions.data.model.ComplaintBody
import com.neqabty.shealth.sustainablehealth.suggestions.domain.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow


interface ComplaintRepository {

    fun addComplaint(complaintBody: ComplaintBody): Flow<String>
    fun getComplaintsCategories(): Flow<List<CategoryEntity>>

}