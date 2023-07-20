package com.neqabty.healthcare.complains.data.repository

import com.neqabty.healthcare.complains.data.model.ComplainModel
import com.neqabty.healthcare.complains.data.source.ComplainDS
import com.neqabty.healthcare.complains.domain.entity.ComplainEntity
import com.neqabty.healthcare.complains.domain.repository.ComplainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ComplainRepositoryImpl @Inject constructor(private val complainDS: ComplainDS): ComplainRepository {
    override fun getAllComplains(): Flow<List<ComplainEntity>> {
        return flow {
            emit(complainDS.getAllComplains().map { it.toComplainEntity() })
        }
    }
}

private fun ComplainModel.toComplainEntity(): ComplainEntity{
    return ComplainEntity(
        account = account,
        answer = answer,
        createdAt = createdAt,
        email = email,
        entity = entity,
        id = id,
        message = message,
        mobile = mobile,
        updatedAt = updatedAt
    )
}