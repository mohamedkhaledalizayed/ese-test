package com.neqabty.healthcare.modules.home.domain.repository

import com.neqabty.healthcare.modules.home.domain.entity.about.AboutEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAboutList(): Flow<List<AboutEntity>>
}