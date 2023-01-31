package com.neqabty.healthcare.sustainablehealth.home.domain.interactors


import com.neqabty.healthcare.sustainablehealth.home.domain.repository.HomeRepository
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.packages.PackagesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPackagesUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    fun build(code: String): Flow<List<PackagesEntity>> {
        return homeRepository.getPackages(code)
    }

}