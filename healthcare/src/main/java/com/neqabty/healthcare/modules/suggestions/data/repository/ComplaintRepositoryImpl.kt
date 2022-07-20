package com.neqabty.healthcare.modules.suggestions.data.repository



import com.neqabty.healthcare.modules.suggestions.data.model.ComplaintBody
import com.neqabty.healthcare.modules.suggestions.data.model.complaintscategory.Category
import com.neqabty.healthcare.modules.suggestions.data.source.ComplaintSource
import com.neqabty.healthcare.modules.suggestions.domain.entity.CategoryEntity
import com.neqabty.healthcare.modules.suggestions.domain.repository.ComplaintRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ComplaintRepositoryImpl @Inject constructor(private val complaintSource: ComplaintSource) :
    ComplaintRepository {

    override fun addComplaint(complaintBody: ComplaintBody): Flow<String> {
        return flow {
            emit(
                complaintSource.addComplaint(
                    complaintBody
                )
            )
        }
    }

    override fun getComplaintsCategories(): Flow<List<CategoryEntity>> {
        return flow {
            emit(complaintSource.getComplaintsCategories().map { it.toCategoryEntity() })
        }
    }
}

private fun Category.toCategoryEntity(): CategoryEntity{
    return CategoryEntity(
        id = id,
        nameAr = nameAr,
        nameEn = nameEn
    )
}
