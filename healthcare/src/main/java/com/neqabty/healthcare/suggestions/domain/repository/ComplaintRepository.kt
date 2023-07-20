package com.neqabty.healthcare.suggestions.domain.repository


import com.neqabty.healthcare.suggestions.data.model.ComplaintBody
import com.neqabty.healthcare.suggestions.domain.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow


interface ComplaintRepository {

    fun addComplaint(complaintBody: ComplaintBody): Flow<String>
    fun getComplaintsCategories(): Flow<List<CategoryEntity>>

}