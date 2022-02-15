package com.neqabty.ads.modules.home.data.repository

import com.neqabty.ads.modules.home.data.model.Ad
import com.neqabty.ads.modules.home.data.source.AdsDS
import com.neqabty.ads.modules.home.domain.entity.AdEntity
import com.neqabty.ads.modules.home.domain.repository.AdsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdsRepositoryImpl @Inject constructor(private val adsDS: AdsDS): AdsRepository {
    override fun getAllAds(): Flow<List<AdEntity>> {
        return flow {
            emit(adsDS.getAllAds().map { it.toAdEntity() })
        }
    }

    override fun getSyndicateAds(syndicateId: Int): Flow<List<AdEntity>> {
        return flow {
            emit(adsDS.getSyndicateAds(syndicateId).map { it.toAdEntity() })
        }
    }
}

fun Ad.toAdEntity(): AdEntity {
    return AdEntity(
        createdAt, entity, id, image, title ?: "", type, updatedAt, url?:""
    )
}

