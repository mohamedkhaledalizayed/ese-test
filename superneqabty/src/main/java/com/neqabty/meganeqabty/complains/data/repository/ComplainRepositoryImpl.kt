package com.neqabty.meganeqabty.complains.data.repository

import com.neqabty.meganeqabty.complains.data.source.ComplainDS
import com.neqabty.meganeqabty.complains.domain.repository.ComplainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ComplainRepositoryImpl @Inject constructor(private val complainDS: ComplainDS): ComplainRepository {
    override fun getAllComplains(): Flow<String> {
        return flow {
            complainDS.getAllComplains()
        }
    }

}