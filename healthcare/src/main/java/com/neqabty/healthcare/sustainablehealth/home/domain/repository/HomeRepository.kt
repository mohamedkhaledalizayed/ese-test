package com.neqabty.healthcare.sustainablehealth.home.domain.repository

import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.AboutEntity
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.packages.PackagesEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAboutList(): Flow<List<AboutEntity>>
    fun getPackages(code: String): Flow<List<PackagesEntity>>
}