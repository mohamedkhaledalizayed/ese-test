package com.neqabty.meganeqabty.home.domain.repository

import com.neqabty.meganeqabty.home.domain.entity.AdEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAllAds(): Flow<List<AdEntity>>
    fun addComplain(mobile: String, email: String, message: String): Flow<String>
}