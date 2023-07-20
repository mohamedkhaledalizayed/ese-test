package com.neqabty.healthcare.packages.packageslist.domain.repository

import com.neqabty.healthcare.packages.packageslist.domain.entity.PackagesEntity
import kotlinx.coroutines.flow.Flow

interface PackagesRepository {
    fun getPackages(code: String): Flow<List<PackagesEntity>>
}