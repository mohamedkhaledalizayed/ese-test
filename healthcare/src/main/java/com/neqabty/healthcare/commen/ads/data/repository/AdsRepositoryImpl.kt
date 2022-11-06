package com.neqabty.healthcare.commen.ads.data.repository


import com.neqabty.healthcare.commen.ads.data.datasource.AdsDS
import com.neqabty.healthcare.commen.ads.data.model.Ad
import com.neqabty.healthcare.commen.ads.domain.entity.AdEntity
import com.neqabty.healthcare.commen.ads.domain.repository.AdsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdsRepositoryImpl @Inject constructor(private val adsDS: AdsDS): AdsRepository {

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

