package com.neqabty.healthcare.commen.packages.packageslist.domain.usecase

import com.neqabty.healthcare.commen.packages.packageslist.domain.repository.PackagesRepository
import com.neqabty.healthcare.commen.packages.packageslist.domain.entity.PackagesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPackagesUseCase @Inject constructor(private val packagesRepository: PackagesRepository) {
    fun getPackages(code: String): Flow<List<PackagesEntity>> {
        return packagesRepository.getPackages(code)
    }
}