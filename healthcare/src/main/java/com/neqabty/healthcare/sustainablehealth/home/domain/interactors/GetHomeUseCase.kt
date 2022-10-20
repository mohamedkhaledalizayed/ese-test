package com.neqabty.healthcare.sustainablehealth.home.domain.interactors

import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.AboutEntity
import com.neqabty.healthcare.sustainablehealth.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    fun build(): Flow<List<AboutEntity>> {
        return homeRepository.getAboutList()
    }
}