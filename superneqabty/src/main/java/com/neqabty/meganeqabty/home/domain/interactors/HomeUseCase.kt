package com.neqabty.meganeqabty.home.domain.interactors


import com.neqabty.meganeqabty.home.domain.entity.AdEntity
import com.neqabty.meganeqabty.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    fun build(): Flow<List<AdEntity>> {
        return homeRepository.getAllAds()
    }

    fun addComplain(mobile: String, email: String, message: String): Flow<String> {
        return homeRepository.addComplain(mobile, email, message)
    }
}