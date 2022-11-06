package com.neqabty.healthcare.commen.ads.domain.interactors


import com.neqabty.healthcare.commen.ads.domain.entity.AdEntity
import com.neqabty.healthcare.commen.ads.domain.repository.AdsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AdsUseCase @Inject constructor(private val adsRepository: AdsRepository) {
    fun build(): Flow<List<AdEntity>> {
        return adsRepository.getAllAds()
    }
}