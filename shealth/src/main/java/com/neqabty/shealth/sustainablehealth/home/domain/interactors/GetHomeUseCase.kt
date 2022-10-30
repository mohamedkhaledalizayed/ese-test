package com.neqabty.shealth.sustainablehealth.home.domain.interactors

import com.neqabty.shealth.sustainablehealth.home.domain.entity.about.AboutEntity
import com.neqabty.shealth.sustainablehealth.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    fun build(): Flow<List<AboutEntity>> {
        return homeRepository.getAboutList()
    }

}