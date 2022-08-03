package com.neqabty.recruitment.modules.home.domain.usecase

import com.neqabty.recruitment.modules.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend fun recruitment(): Flow<String> {
        return homeRepository.recruitment()
    }

}