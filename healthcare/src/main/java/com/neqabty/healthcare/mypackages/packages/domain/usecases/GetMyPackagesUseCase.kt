package com.neqabty.healthcare.mypackages.packages.domain.usecases

import com.neqabty.healthcare.mypackages.packages.domain.entity.ProfileEntity
import com.neqabty.healthcare.mypackages.packages.domain.repository.MyPackagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyPackagesUseCase @Inject constructor(private val myPackagesRepository: MyPackagesRepository) {

    fun build(phone: String): Flow<ProfileEntity>{
        return myPackagesRepository.getMyPackages(phone)
    }

}