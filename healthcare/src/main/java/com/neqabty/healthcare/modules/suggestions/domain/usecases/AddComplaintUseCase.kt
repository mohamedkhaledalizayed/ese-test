package com.neqabty.healthcare.modules.suggestions.domain.usecases

import com.neqabty.healthcare.modules.suggestions.data.model.ComplaintBody
import com.neqabty.healthcare.modules.suggestions.domain.entity.CategoryEntity
import com.neqabty.healthcare.modules.suggestions.domain.repository.ComplaintRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddComplaintUseCase @Inject constructor(private val complaintRepository: ComplaintRepository) {

    fun build(complaintBody: ComplaintBody): Flow<String> {
        return complaintRepository.addComplaint(complaintBody)
    }

    fun build(): Flow<List<CategoryEntity>> {
        return complaintRepository.getComplaintsCategories()
    }
}