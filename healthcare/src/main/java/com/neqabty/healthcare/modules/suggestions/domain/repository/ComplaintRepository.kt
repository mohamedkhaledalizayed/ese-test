package com.neqabty.healthcare.modules.suggestions.domain.repository


import com.neqabty.healthcare.modules.suggestions.data.model.ComplaintBody
import com.neqabty.healthcare.modules.suggestions.domain.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow


interface ComplaintRepository {

    fun addComplaint(complaintBody: ComplaintBody): Flow<String>
    fun getComplaintsCategories(): Flow<List<CategoryEntity>>

}