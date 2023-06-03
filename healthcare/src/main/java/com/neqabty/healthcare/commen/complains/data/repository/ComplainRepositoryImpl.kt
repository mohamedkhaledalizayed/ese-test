package com.neqabty.healthcare.commen.complains.data.repository

import com.neqabty.healthcare.commen.complains.data.model.getcomplains.ComplainModel
import com.neqabty.healthcare.commen.complains.data.datasource.ComplainDS
import com.neqabty.healthcare.commen.complains.domain.entity.getcomplain.ComplainEntity
import com.neqabty.healthcare.commen.complains.domain.repository.ComplainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ComplainRepositoryImpl @Inject constructor(private val complainDS: ComplainDS):
    ComplainRepository {

    override fun addComplain(mobile: String, email: String, message: String): Flow<String> {
        return flow {
            emit(complainDS.addComplain(mobile, email, message))
        }
    }

    override fun getAllComplains(): Flow<List<ComplainEntity>> {
        return flow {
            emit(complainDS.getAllComplains().map { it.toComplainEntity() })
        }
    }

}

private fun ComplainModel.toComplainEntity(): ComplainEntity {
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