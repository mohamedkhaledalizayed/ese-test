package com.neqabty.healthcare.mega.home.domain.repository

import com.neqabty.healthcare.mega.home.domain.entity.AdEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAllAds(): Flow<List<AdEntity>>
    fun addComplain(mobile: String, email: String, message: String): Flow<String>
}