package com.neqabty.healthcare.sustainablehealth.home.domain.repository

import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.AboutEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAboutList(): Flow<List<AboutEntity>>
}