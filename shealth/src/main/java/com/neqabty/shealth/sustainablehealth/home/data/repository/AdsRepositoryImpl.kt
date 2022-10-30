package com.neqabty.shealth.sustainablehealth.home.data.repository

import com.neqabty.shealth.sustainablehealth.home.data.model.ads.Ad
import com.neqabty.shealth.sustainablehealth.home.data.source.AdsDS
import com.neqabty.shealth.sustainablehealth.home.domain.entity.AdEntity
import com.neqabty.shealth.sustainablehealth.home.domain.repository.AdsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdsRepositoryImpl @Inject constructor(private val adsDS: AdsDS) : AdsRepository {
    override fun getAllAds(): Flow<List<AdEntity>> {
        return flow {
            emit(adsDS.getAllAds().map { it.toAdEntity() })
        }
    }
}

fun Ad.toAdEntity(): AdEntity {
    return AdEntity(
        createdAt, entity, id, newsId, image, title ?: "", type, updatedAt, url?:""
    )
}