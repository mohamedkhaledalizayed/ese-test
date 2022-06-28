package com.neqabty.meganeqabty.home.domain.interactors


import com.neqabty.meganeqabty.home.domain.entity.AdEntity
import com.neqabty.meganeqabty.home.domain.repository.AdsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAdsUseCase @Inject constructor(private val adsRepository: AdsRepository) {
    fun build(): Flow<List<AdEntity>> {
        return adsRepository.getAllAds()
    }
}