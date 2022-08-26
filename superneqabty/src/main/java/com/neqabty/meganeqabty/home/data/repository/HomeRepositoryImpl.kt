package com.neqabty.meganeqabty.home.data.repository


import com.neqabty.meganeqabty.home.data.model.ads.Ad
import com.neqabty.meganeqabty.home.data.source.HomeDS
import com.neqabty.meganeqabty.home.domain.entity.AdEntity
import com.neqabty.meganeqabty.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeDS: HomeDS): HomeRepository {

    override fun getAllAds(): Flow<List<AdEntity>> {
        return flow {
            emit(homeDS.getAllAds().map { it.toAdEntity() })
        }
    }

    override fun addComplain(mobile: String, email: String, message: String): Flow<String> {
        return flow {
            emit(homeDS.addComplain(mobile, email, message))
        }
    }

}

fun Ad.toAdEntity(): AdEntity {
    return AdEntity(
        createdAt, entity, id, newsId, image, title ?: "", type, updatedAt, url?:""
    )
}

