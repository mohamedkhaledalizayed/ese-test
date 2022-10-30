package com.neqabty.shealth.sustainablehealth.home.domain.repository

import com.neqabty.shealth.sustainablehealth.home.domain.entity.about.AboutEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAboutList(): Flow<List<AboutEntity>>
}