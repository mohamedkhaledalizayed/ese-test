package com.neqabty.recruitment.modules.home.data.repository

import com.neqabty.recruitment.modules.home.data.source.HomeDS
import com.neqabty.recruitment.modules.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeDS: HomeDS): HomeRepository {
    override fun recruitment(): Flow<String> {
        return flow {
            emit(homeDS.recruitment())
        }
    }
}